package handler;

import dataaccess.Database;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.WebSocketServices;
import websocket.commands.UserGameCommand;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {

    private Database database;
    public WebSocketHandler(Database database){
        this.database = database;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception{
        System.out.printf("received: %s", message);
        UserGameCommand userGameCommand = messageToCommand(message);
        WebSocketServices websocketService = new WebSocketServices(database, session);
        websocketService.service(userGameCommand);
    }


    private static UserGameCommand messageToCommand(String message){
        return new UserGameCommand("peepee");
    }
}
