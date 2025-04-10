package client.websocket;
import chess.ChessMove;
import client.Client;
import com.google.gson.Gson;
import exception.ResponseException;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;


public class WebsocketFacade extends Endpoint implements MessageHandler.Whole<String>{
    public Session session;
    private GameHandler gameHandler;
    public WebsocketFacade() throws ResponseException {
        try {
            URI uri = new URI("ws://localhost:8080/ws");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);

        }
        catch(Exception e) {
            throw new ResponseException(400, "");
        }
    }

    @Override
    public void onOpen(Session sesssion, EndpointConfig endpointConfig){
    }

    @Override
    public void onMessage(String messageReceived){
        ServerMessage message = new Gson().fromJson(messageReceived,ServerMessage.class);
        if(message.getServerMessageType()== ServerMessage.ServerMessageType.LOAD_GAME){
            LoadGameMessage loadGameMessage = new Gson().fromJson(messageReceived,LoadGameMessage.class);
            gameHandler.updateGame(loadGameMessage.getChessGame(),null);
        }
        else if(message.getServerMessageType() == ServerMessage.ServerMessageType.NOTIFICATION){
            NotificationMessage notificationMessage = new Gson().fromJson(messageReceived, NotificationMessage.class);
            gameHandler.printMessage(notificationMessage.)
        }
        else{
            ErrorMessage errorMessage = new Gson().fromJson(messageReceived, ErrorMessage.class);

        }
    }

    public void connect(String authToken, Integer gameID) throws IOException {
        UserGameCommand command = new UserGameCommand(UserGameCommand.CommandType.CONNECT,authToken,gameID);
        sendMessage(new Gson().toJson(command));
    }

    public void makeMove(String authToken, Integer gameID, ChessMove chessMove) throws IOException {
        MakeMoveCommand command = new MakeMoveCommand(chessMove,authToken,gameID);
        sendMessage(new Gson().toJson(command));
    }

    public void leaveGame(String authToken, Integer gameID) throws IOException {
        UserGameCommand command = new UserGameCommand(UserGameCommand.CommandType.LEAVE,authToken,gameID);
        sendMessage(new Gson().toJson(command));
    }

    public void resignGame(String authToken, Integer gameID) throws IOException {
        UserGameCommand command = new UserGameCommand(UserGameCommand.CommandType.RESIGN,authToken,gameID);
        sendMessage(new Gson().toJson(command));
    }

    private void sendMessage(String jsonCommand) throws IOException {
        this.session.getBasicRemote().sendText(jsonCommand);
    }



}
