package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.Database;
import intermediary.InvalidAuthException;
import model.GameData;
import server.Server;
import websocket.commands.LeaveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.NotificationMessage;
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
            return new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Error: Invalid AuthToken");
        } catch (DataAccessException e) {
            return new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Error: Data Access Exception");
        }
    }

    private ServerMessage connect(UserGameCommand command){
        //honestly not super sure what to do here...

    }

    private ServerMessage leave(LeaveCommand leaveCommand) throws DataAccessException {
        String username = authDataBase.getAuth(leaveCommand.getAuthString()).username();
        GameData gameData = gameDataBase.getGame(leaveCommand.getGameID());
        String updatedWhiteUsername = gameData.whiteUsername();
        String updatedBlackUsername = gameData.blackUsername();
        if(updatedWhiteUsername.equals(username)){
            updatedWhiteUsername = null; //null instead of "null" could cause issue
        }
        if(updatedBlackUsername.equals(username)){
            updatedBlackUsername = null;
        }
        GameData updatedGameData =new GameData(gameData.gameID(), updatedWhiteUsername, updatedBlackUsername, gameData.gameName(), gameData.game());
        gameDataBase.updateGame(updatedGameData);
        //TODO : remove self from messaging list
        return new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, username + "has left the game. Adios!");
    }

    private ServerMessage makeMove(UserGameCommand command){

    }

    private ServerMessage resign(UserGameCommand command){

    }


}
