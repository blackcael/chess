package handler;

import dataaccess.Database;
import service.WebsocketServices;
import websocket.commands.UserGameCommand;

public class WebSocketMessageHandler {
    public static String handleMessage(Database database, String message){
        //1. parse down the message, figure out which type of command
        UserGameCommand userGameCommand; //parseThatHomie
        WebsocketServices websocketService = new WebsocketServices(database);
        websocketService.service(userGameCommand);


        return "peepee";
    }
}
