package server.websocket;

import chess.*;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import dataaccess.sqldataaccess.SqlAuthData;
import dataaccess.sqldataaccess.SqlGamesData;
import dataaccess.sqldataaccess.SqlUserData;
import exception.ResponseException;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import com.google.gson.Gson;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

@WebSocket
public class WebSocketHandler {
    WebSocketSessions sessions;
    AuthDAO authDAO;
    GameDAO gameDAO;
    UserDAO userDAO;

    public WebSocketHandler(){
        sessions = new WebSocketSessions();

        try{
        authDAO = new SqlAuthData();
        gameDAO = new SqlGamesData();
        userDAO = new SqlUserData();
        }
        catch (ResponseException r){
        }
    }

    @OnWebSocketConnect
    public void onConnect(Session session){
        System.out.println("inside onConnect");
    }

    @OnWebSocketError
    public void onError(Session session,Throwable throwable) throws ResponseException {
        ErrorMessage error = new ErrorMessage("websocket error: " + throwable.getMessage());
        try{
            session.getRemote().sendString(new Gson().toJson(error));
        }
        catch(Exception exception){
            throw new ResponseException(400,"IO error could not send error message");
        }
        //throw new ResponseException(500,"Failed");
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String userJsonString) throws ResponseException, InvalidMoveException {
        System.out.println("INSIDE ONMESSAGE");
        UserGameCommand gameCommand = new Gson().fromJson(userJsonString, UserGameCommand.class);
        System.out.println(userJsonString);
            //errorPresent(gameCommand,session);
        AuthData authData = authDAO.getAuthData(gameCommand.getAuthToken());
        if (authData==null){
            throw new ResponseException(400, "Websocket connect Not authorized");
        }
        GameData gameData = gameDAO.findGame(gameCommand.getGameID());
        if (gameData==null){
            throw new ResponseException(400,"Websocket connect No game with this gameID");
        }

            switch (gameCommand.getCommandType()) {
                case CONNECT:
                    connect(gameCommand, session);
                    break;
                case MAKE_MOVE:

                    makeMove(new Gson().fromJson(userJsonString, MakeMoveCommand.class), session);
                    break;
                case LEAVE:
                    leave(gameCommand, session);
                    break;
                case RESIGN:
                    resign(gameCommand, session);
            }

    }

    @OnWebSocketClose
    public void closeSocket(Session session, int a, String b){
        System.out.println(b);
        sessions.removeSession(sessions.getGameID(session),session);
    }



    public void connect(UserGameCommand command, Session session) throws ResponseException {
        System.out.println("Inside connect");
        sessions.addSession(command.getGameID(),session);
        AuthData authData = authDAO.getAuthData(command.getAuthToken());
        if (authData==null){
            System.out.println("authdata is null");
            throw new ResponseException(200, "Websocket connect Not authorized");
        }
        GameData gameData = gameDAO.findGame(command.getGameID());
        if (gameData==null){
            System.out.println("gamedata=null");
            throw new ResponseException(200,"Websocket connect No game with this gameID");
        }

        ChessGame game = gameData.game();
        sendMessage(new LoadGameMessage(game),session);


        String username = authData.username();
        String role;
        if (Objects.equals(gameData.blackUsername(), username)) {
            role="black";
        }
        else if (Objects.equals(gameData.whiteUsername(), username)){
            role="white";
        }
        else{
            role="observer";
        }
        broadcastMessage(new NotificationMessage(username+" joined the game as "+role),session,command.getGameID());

    }

    public void makeMove(MakeMoveCommand command, Session session) throws ResponseException, InvalidMoveException {
        System.out.println("calling makeMove");
        GameData gameData = gameDAO.findGame(command.getGameID());
        if (gameData.game().isEndGame()){sendMessage(new ErrorMessage("Game is over. Cannot move pieces."),session);return;}
        AuthData authData = authDAO.getAuthData(command.getAuthToken());
        if (authData==null){
//            System.out.println("didn't work");
            throw new ResponseException(400, "Websocket connect Not authorized");
        }


        String username = authData.username();

        ChessGame game = gameData.game();

        ChessMove requestedMove = command.getMove();
        //Collection<ChessMove> validMoves = game.validMoves(command.getMove().getStartPosition());
        ChessPosition endPosition = requestedMove.getEndPosition();
        ChessPiece piece;

        piece = game.getBoard().getPiece(command.getMove().getStartPosition());

        System.out.println("calling makeMove");
        ChessGame.TeamColor userColor;
        ChessGame.TeamColor pieceColor;
        ChessGame.TeamColor enemyColor;
        boolean observer = false;

        if (Objects.equals(gameData.blackUsername(), username)){
            userColor= ChessGame.TeamColor.BLACK;
            enemyColor = ChessGame.TeamColor.WHITE;
        }
        else if (Objects.equals(gameData.whiteUsername(), username)){
            userColor=ChessGame.TeamColor.WHITE;
            enemyColor = ChessGame.TeamColor.BLACK;
        }
        else{
            sendMessage(new ErrorMessage("Invalid request. You are observing this game."),session);
            return;
        }
        if(game.getTeamTurn()!=userColor){
            sendMessage(new ErrorMessage("Not your turn"),session);
            return;
        }
            game.makeMove(requestedMove);   //might need to throw an exception if just calling it doesn't throw an invalid move



        if (piece.getTeamColor()== ChessGame.TeamColor.BLACK){
            pieceColor = ChessGame.TeamColor.BLACK;
        }
        else{
            pieceColor = ChessGame.TeamColor.WHITE;
        }
        if (pieceColor!=userColor){
            sendMessage(new ErrorMessage("Invalid request. You cannot move this piece."),session);return;
        }
        System.out.println("calling makeMove");

        gameDAO.updateGameWebsocket(gameData); //is game actually updating in SQL?

        LoadGameMessage message = new LoadGameMessage(game);
        broadcastMessage(message,session,command.getGameID()); //send message to you and everyone else
        sendMessage(message,session);
        broadcastMessage(new NotificationMessage(username+" moved "+piece+" from "+requestedMove.getStartPosition()+
                " to "+endPosition),session,command.getGameID()); //send message to everyone else with move made

        NotificationMessage possibleEndGame;
        if (game.isInCheckmate(enemyColor)){
            possibleEndGame = new NotificationMessage("Game ends in checkmate! " + pieceColor + " wins!");
            sendMessage(possibleEndGame,session);
            broadcastMessage(possibleEndGame,session,command.getGameID());
            gameData.game().setEndGame(true);

        }
        else if (game.isInStalemate(enemyColor)){
            possibleEndGame = new NotificationMessage("Game ends in stalemate!");
            sendMessage(possibleEndGame,session);
            broadcastMessage(possibleEndGame,session,command.getGameID());
            gameData.game().setEndGame(true);
        }
        else if(game.isInCheck(enemyColor)){
            possibleEndGame = new NotificationMessage(enemyColor.toString()+" is in check!");
            sendMessage(possibleEndGame,session);
            broadcastMessage(possibleEndGame,session,command.getGameID());
        }
        System.out.println("calling makeMove");
    }


    public void leave(UserGameCommand command, Session session) throws ResponseException {
        AuthData authData = authDAO.getAuthData(command.getAuthToken());
        String username = authData.username();
        GameData gameData = gameDAO.findGame(command.getGameID());


        if (Objects.equals(username, gameData.blackUsername())){
            gameDAO.updateGameWebsocket(
                    new GameData(gameData.gameID(),gameData.whiteUsername(),null, gameData.gameName(), gameData.game()));
        }
        else if (Objects.equals(username, gameData.whiteUsername())){
            gameDAO.updateGameWebsocket(
                    new GameData(gameData.gameID(),null,gameData.blackUsername(), gameData.gameName(), gameData.game()));
        }

        broadcastMessage(new NotificationMessage(username+" left the game."),session,command.getGameID());
        sessions.removeSession(command.getGameID(), session);
    }

    public void resign(UserGameCommand command, Session session) throws ResponseException {

        AuthData authData = authDAO.getAuthData(command.getAuthToken());
        String username = authData.username();
        GameData gameData = gameDAO.findGame(command.getGameID());
        if (gameData.game().isEndGame()){
            sendMessage(new ErrorMessage("Game is over. Cannot resign."),session);
            return;
        }
        if (!Objects.equals(username, gameData.blackUsername()) && !Objects.equals(username, gameData.whiteUsername())){
            sendMessage(new ErrorMessage("Observers cannot resign."),session);
            return;
        }
        gameData.game().setEndGame(true);
        NotificationMessage resigned = new NotificationMessage(username+" has resigned. Game ended.");
        sendMessage(resigned,session);
        broadcastMessage(resigned,session,command.getGameID());
        gameDAO.updateGameWebsocket(gameData);

    }

    public void sendMessage(ServerMessage message, Session session) throws ResponseException {
        try{
            session.getRemote().sendString(new Gson().toJson(message));
        }
        catch (Exception exception) {
            throw new ResponseException(400,"Could not send message");
        }
    }

    public void broadcastMessage(ServerMessage message, Session sessionException,Integer gameID) throws ResponseException {
        for (Session session : sessions.getSessions(gameID)){
            if (session!=sessionException){
                sendMessage(message,session);
            }
        }
    }
}
