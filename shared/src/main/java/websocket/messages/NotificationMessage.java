package websocket.messages;

public class NotificationMessage extends ServerMessage{
    String message;
    public NotificationMessage(String notificationmessage){
        super(ServerMessageType.NOTIFICATION);
        message=notificationmessage;

    }
}
