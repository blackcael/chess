package handler;

import dataaccess.Database;
import service.WebsocketServices;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

public class WebSocketMessageHandler {
    public static String handleMessage(Database database, String message){
        //1. parse down the message, figure out which type of command
        UserGameCommand userGameCommand = messageToCommand(message);
        WebsocketServices websocketService = new WebsocketServices(database);
        ServerMessage serverMessage = websocketService.service(userGameCommand);
        //turn servermessage back into a string and send it back

        return "peepee";
    }

    private static UserGameCommand messageToCommand(String message){
        return new UserGameCommand("peepee");
    }
}
