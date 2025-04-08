package server.websocket;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import dataaccess.sqldataaccess.SqlAuthData;
import dataaccess.sqldataaccess.SqlGamesData;
import dataaccess.sqldataaccess.SqlUserData;
import exception.ResponseException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.UserGameCommand;
import com.google.gson.Gson;

@WebSocket
public class WebSocketHandler {
    WebSocketSessions sessions;
    AuthDAO authDAO;
    GameDAO gameDAO;
    UserDAO userDAO;

    void WebsocketHandler() throws ResponseException {
        sessions = new WebSocketSessions();
        authDAO = new SqlAuthData();
        gameDAO = new SqlGamesData();
        userDAO = new SqlUserData();

    }
    @OnWebSocketError
    public void onError(Throwable throwable){

    }
    @OnWebSocketMessage
    public void onMessage(Session session, String userJsonString) throws ResponseException {
        UserGameCommand gameCommand = new Gson().fromJson(userJsonString, UserGameCommand.class);
        switch (gameCommand.getCommandType()){
            case CONNECT:
                connect(gameCommand,session);
                break;
            case MAKE_MOVE:
                makeMove()
                break;
            case LEAVE:
                break;
            case RESIGN:

        }
    }

    public void connect(UserGameCommand command, Session session) throws ResponseException{

    }

    public void makeMove(UserGameCommand command, Session session){

    }

    public void leave(UserGameCommand command, Session session){

    }

    public void resign(UserGameCommand command, Session session){

    }

    public void sendMessage(String message, Session session){

    }


}
