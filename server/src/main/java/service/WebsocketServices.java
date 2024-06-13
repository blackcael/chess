package service;

import dataaccess.DataAccessException;
import dataaccess.Database;
import intermediary.InvalidAuthException;
import server.Server;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

public class WebsocketServices extends BaseService{

    public WebsocketServices(Database database) {
        super(database);
    }

    public ServerMessage service(UserGameCommand command){
        try {
            validateAuthToken(command.getAuthString());
            return switch (command.getCommandType()) {
                case CONNECT -> connect(command);
                case LEAVE -> leave(command);
                case MAKE_MOVE -> makeMove(command);
                case RESIGN -> resign(command);
            };
        }catch (InvalidAuthException e) {
            return ("Error: Invalid Message Dawg");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private ServerMessage connect(UserGameCommand command){

    }

    private ServerMessage leave(UserGameCommand command){

        //
    }

    private ServerMessage makeMove(UserGameCommand command){

    }

    private ServerMessage resign(UserGameCommand command){

    }

}
