package service;

import chess.ChessGame;
import chess.InvalidMoveException;
import dataaccess.DataAccessException;
import dataaccess.Database;
import handler.WebSocketConnection;
import intermediary.GameOverException;
import intermediary.InvalidAuthException;
import model.GameData;
import websocket.commands.*;
import websocket.messages.*;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;


public class WebSocketServices extends BaseService{
    private WebSocketNotificationService notificationService;
    private final Session session;
    private WebSocketConnection connection;
    private String username;
    public WebSocketServices(Database database, Session session) {
        super(database);
        this.session = session;
    }

    public void service(UserGameCommand command) throws IOException {
        try {
            username = authDataBase.getAuth(command.getAuthString()).username();
            connection = new WebSocketConnection(username, session);
            notificationService = new WebSocketNotificationService(database.getGameParticipants(command.getGameID()), connection);
            validateAuthToken(command.getAuthString());
            switch (command.getCommandType()) {
                case CONNECT -> connect((ConnectCommand) command);
                case LEAVE -> leave((LeaveCommand) command);
                case MAKE_MOVE -> makeMove((MakeMoveCommand) command);
                case RESIGN -> resign((ResignCommand) command);
            }
        }catch (InvalidAuthException e) {
            notificationService.alertSender(new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Error: Invalid AuthToken"));
        } catch (DataAccessException e) {
            notificationService.alertSender( new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Error: Data Access Exception"));
        } catch (InvalidMoveException e) {
            notificationService.alertSender( new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Error: Invalid Move!"));
        } catch (GameOverException e) {
            notificationService.alertSender( new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Error: Game is already over!"));
        }
    }

    private void connect(ConnectCommand command) throws DataAccessException, IOException {
        database.connectPlayerToGameSession(connection, command.getGameID());
        notificationService.alertSender( new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, gameDataBase.getGame(command.getGameID()).game()));
        notificationService.alertEveryone(new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, username + " has connected to the game. Welcome!"));
    }

    private void leave(LeaveCommand leaveCommand) throws DataAccessException, IOException {
        //SQL interactions
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
        //gameSessionInteraction
        database.disconnectPlayerFromGameSession(connection, leaveCommand.getGameID());
        notificationService.alertEveryone( new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, username + "has left the game. Adios!"));
    }

    private void makeMove(MakeMoveCommand makeMoveCommand) throws DataAccessException, InvalidMoveException, GameOverException, IOException {
        //color issues?
        validateGameIsNotOver(makeMoveCommand.getGameID());
        GameData gameData = gameDataBase.getGame(makeMoveCommand.getGameID());
        ChessGame updatedGame = gameData.game();
        updatedGame.makeMove(makeMoveCommand.getMove());
        GameData updatedGameData = new GameData(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), updatedGame);
        gameDataBase.updateGame(updatedGameData);
        String gameStatusMessage = "";
        if(updatedGame.isInCheckmate(ChessGame.TeamColor.WHITE)){
            updateGameAsFinished(makeMoveCommand.getGameID());
            gameStatusMessage = "\nWhite is in Checkmate! Game Over!";
        }else if (updatedGame.isInCheckmate(ChessGame.TeamColor.BLACK)){
            updateGameAsFinished(makeMoveCommand.getGameID());
            gameStatusMessage = "\nWhite is in Checkmate! Game Over!";
        }else if (updatedGame.isInStalemate(ChessGame.TeamColor.WHITE) || updatedGame.isInStalemate(ChessGame.TeamColor.BLACK)){
            updateGameAsFinished(makeMoveCommand.getGameID());
            gameStatusMessage = "\nStalemate! Game Over!";
        }else if (updatedGame.isInCheck(ChessGame.TeamColor.WHITE) || updatedGame.isInCheck(ChessGame.TeamColor.BLACK)){
            gameStatusMessage = "\nCheck!";
        }
        notificationService.alertEveryone( new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, gameDataBase.getGame(makeMoveCommand.getGameID()).game()));
        notificationService.alertEveryone( new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, username + "has moved." + gameStatusMessage));
    }

    private void resign(ResignCommand command) throws GameOverException, DataAccessException, IOException {
        validateGameIsNotOver(command.getGameID());
        notificationService.alertEveryone( new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, username + "has resigned. Game over man!"));
    }

    //supporting Functions

    private void updateGameAsFinished(int gameID) throws DataAccessException {
        GameData gameData = gameDataBase.getGame(gameID);
        ChessGame updatedGame = gameData.game();
        updatedGame.finishGame();
        GameData updatedGameData =new GameData(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), updatedGame);
        gameDataBase.updateGame(updatedGameData);
    }

    private void validateGameIsNotOver(int gameID) throws GameOverException, DataAccessException {
        GameData gameData = gameDataBase.getGame(gameID);
        if (gameData.game().isFinished()) {
            throw new GameOverException();
        }
    }
}
