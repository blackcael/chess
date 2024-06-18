package service;

import dataaccess.Database;
import handler.WebSocketConnection;
import websocket.WebSocketSerializer;
import websocket.messages.ServerMessage;


import java.io.IOException;
import java.util.Set;

public class WebSocketNotificationService {
    private Database database;
    private final WebSocketConnection senderConnection;
    WebSocketNotificationService(Database database, WebSocketConnection senderConnection){
        this.database = database;
        this.senderConnection = senderConnection;
    }
    public void alertEveryone(int gameID, ServerMessage servermessage) throws IOException {
        Set<WebSocketConnection> connectionList = database.getParticipantSet(gameID);
        for(WebSocketConnection connection: connectionList){
            connection.send(WebSocketSerializer.serverMessageToJson(servermessage));
        }
    }

    public void alertSender(ServerMessage servermessage) throws IOException {
        senderConnection.send(WebSocketSerializer.serverMessageToJson(servermessage));
    }

    public void alertOthers(int gameID, ServerMessage servermessage) throws IOException {
        Set<WebSocketConnection> connectionList = database.getParticipantSet(gameID);
        for(WebSocketConnection connection: connectionList){
            if(!connection.getUsername().equals(senderConnection.getUsername())){
                connection.send(WebSocketSerializer.serverMessageToJson(servermessage));
            }
        }
    }



}
