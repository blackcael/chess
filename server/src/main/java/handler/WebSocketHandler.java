package handler;

import dataaccess.Database;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.WebSocketServices;
import websocket.WebSocketSerializer;
import websocket.commands.UserGameCommand;

@WebSocket
public class WebSocketHandler {

    private Database database;
    public WebSocketHandler(Database database){
        this.database = database;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception{
        System.out.printf("received: %s", message);
        UserGameCommand userGameCommand = WebSocketSerializer.jsonToUserCommand(message);
        WebSocketServices websocketService = new WebSocketServices(database, session);
        websocketService.service(userGameCommand);
    }
}
