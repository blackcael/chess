package service;

import handler.WebSocketConnection;
import websocket.messages.ServerMessage;


import java.io.IOException;
import java.util.Set;

public class WebSocketNotificationService {
    private Set<WebSocketConnection> connectionList;
    private final WebSocketConnection connection;
    WebSocketNotificationService(Set<WebSocketConnection> playerList, WebSocketConnection connection){
        this.connectionList = playerList;
        this.connection = connection;
    }
    public void alertEveryone(ServerMessage servermessage) throws IOException {
        for(WebSocketConnection iterPlayer: connectionList){
            iterPlayer.send(serverMessageToJson(servermessage));
        }
    }

    public void alertSender(ServerMessage servermessage) throws IOException {
        connection.send(serverMessageToJson(servermessage));
    }


    private String serverMessageToJson(ServerMessage serverMessage){
        return "peepee";
    }


}
