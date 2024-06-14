package service;

import chess.ChessGame;
import chess.InvalidMoveException;
import dataaccess.DataAccessException;
import dataaccess.Database;
import intermediary.InvalidAuthException;
import model.GameData;
import server.Server;
import websocket.commands.*;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

public class WebsocketServices extends BaseService{
    private String username;
    private ChessGame.TeamColor playerColor;
    public WebsocketServices(Database database) {
        super(database);
    }

    public ServerMessage service(UserGameCommand command){

        try {
            validateAuthToken(command.getAuthString());
            username = authDataBase.getAuth(command.getAuthString()).username();
            return switch (command.getCommandType()) {
                case CONNECT -> connect((ConnectCommand) command);
                case LEAVE -> leave((LeaveCommand) command);
                case MAKE_MOVE -> makeMove((MakeMoveCommand) command);
                case RESIGN -> resign((ResignCommand) command);
            };
        }catch (InvalidAuthException e) {
            return new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Error: Invalid AuthToken");
        } catch (DataAccessException e) {
            return new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Error: Data Access Exception");
        } catch (InvalidMoveException e) {
            return new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Error: Invalid Move!");
    }
    }

    private ServerMessage connect(ConnectCommand command){
        //honestly not super sure what to do here...
        //mostly just map work?
    }

    private ServerMessage leave(LeaveCommand leaveCommand) throws DataAccessException {
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

    private ServerMessage makeMove(MakeMoveCommand makeMoveCommand) throws DataAccessException, InvalidMoveException {
        //color issues?
        GameData gameData = gameDataBase.getGame(makeMoveCommand.getGameID());
        ChessGame updatedGame = gameData.game();
        updatedGame.makeMove(makeMoveCommand.getMove());
        GameData updatedGameData =new GameData(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), updatedGame);
        gameDataBase.updateGame(updatedGameData);
        return new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, updatedGame);
    }

    private ServerMessage resign(ResignCommand command){

    }


}
