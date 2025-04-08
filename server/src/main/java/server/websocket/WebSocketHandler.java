package server.websocket;

import chess.ChessGame;
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
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import com.google.gson.Gson;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {
    WebSocketSessions sessions;
    AuthDAO authDAO;
    GameDAO gameDAO;
    UserDAO userDAO;

    public void WebsocketHandler() throws ResponseException {
        sessions = new WebSocketSessions();
        authDAO = new SqlAuthData();
        gameDAO = new SqlGamesData();
        userDAO = new SqlUserData();

    }
    @OnWebSocketError
    public void onError(Throwable throwable,Session session) throws ResponseException {
        ErrorMessage error = new ErrorMessage("websocket error: " + throwable.getMessage());
        try{
            session.getRemote().sendString(new Gson().toJson(error));
        }
        catch(Exception exception){
            throw new ResponseException(400,"IO error could not send error message");
        }
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String userJsonString) throws ResponseException {
        UserGameCommand gameCommand = new Gson().fromJson(userJsonString, UserGameCommand.class);
        errorPresent(gameCommand,session);
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

    public void errorPresent(UserGameCommand gameCommand,Session session) throws ResponseException {

        AuthData authdata = authDAO.getAuthData(gameCommand.getAuthToken());
        if (authdata==null){
            onError(new ResponseException(400,"error: not authorized"),session);
        }
        try{
            GameData gamedata = gameDAO.findGame(gameCommand.getGameID());
        }
        catch(Exception e){
            onError(new ResponseException(400,"error: game does not exist"),session);
        }
    }


    public void connect(UserGameCommand command, Session session) throws ResponseException {
        GameData gameData = gameDAO.findGame(command.getGameID());
        ChessGame game = gameData.game();
        sendMessage(new LoadGameMessage(game),session);

        AuthData authData = authDAO.getAuthData(command.getAuthToken());
        String username = authData.username();
        String role;
        if (gameData.blackUsername()==username) {
            role="black";
        }
        else if (gameData.whiteUsername()==username){
            role="white";
        }
        else{
            role="observer";
        }
        broadcastMessage(new NotificationMessage(username+" joined the game as "+role),session,command.getGameID());

    }

    public void makeMove(UserGameCommand command, Session session) throws ResponseException {
        GameData gameData = gameDAO.findGame(command.getGameID());
        ChessGame game = gameData.game();


//        sendMessage(new LoadGameMessage(game),session);
    }

    public void leave(UserGameCommand command, Session session){

    }

    public void resign(UserGameCommand command, Session session){

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
