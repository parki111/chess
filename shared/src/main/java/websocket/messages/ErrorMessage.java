package websocket.messages;

public class ErrorMessage extends ServerMessage{
    String errorMessage;
    public ErrorMessage(String Message){
        super(ServerMessageType.ERROR);
        errorMessage=Message;
    }
}
