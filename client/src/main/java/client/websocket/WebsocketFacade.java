package client.websocket;
import chess.ChessMove;
import exception.ResponseException;

import javax.websocket.*;
import javax.websocket.Session;
import java.net.URI;


public class WebsocketFacade extends Endpoint implements MessageHandler.Whole<String>{
    public Session session;
    private GameHandler gameHandler;
    public WebsocketFacade(Session session) throws ResponseException {
        try {
            this.session = session;
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
    public void onMessage(String message){

    }

    public void connect(String authToken, Integer gameID){

    }

    public void makeMove(String authToken, Integer gameID, ChessMove chessMove){

    }

    public void leaveGame(String authToken, Integer gameID){

    }

    public void resignGame(String authToken, Integer gameID){

    }
}
