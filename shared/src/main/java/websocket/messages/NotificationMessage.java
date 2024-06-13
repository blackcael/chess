package websocket.messages;

import server.Server;

public class NotificationMessage extends ServerMessage {
    private final String message;
    public NotificationMessage(ServerMessageType type, String message) {
        super(type);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
