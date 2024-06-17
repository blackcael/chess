package service;

import handler.WebSocketConnection;
import websocket.WebSocketSerializer;
import websocket.messages.ServerMessage;


import java.io.IOException;
import java.util.Set;

public class WebSocketNotificationService {
    private Set<WebSocketConnection> connectionList;
    private final WebSocketConnection senderConnection;
    WebSocketNotificationService(Set<WebSocketConnection> playerList, WebSocketConnection senderConnection){
        this.connectionList = playerList;
        this.senderConnection = senderConnection;
    }
    public void alertEveryone(ServerMessage servermessage) throws IOException {
        for(WebSocketConnection connection: connectionList){
            connection.send(WebSocketSerializer.serverMessageToJson(servermessage));
        }
    }

    public void alertSender(ServerMessage servermessage) throws IOException {
        senderConnection.send(WebSocketSerializer.serverMessageToJson(servermessage));
    }



}
