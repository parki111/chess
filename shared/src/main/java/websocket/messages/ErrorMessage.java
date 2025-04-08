package websocket.messages;

public class ErrorMessage extends ServerMessage{
    String message;
    public ErrorMessage(String errorMessage){
        super(ServerMessageType.ERROR);
        message=errorMessage;
    }
}
