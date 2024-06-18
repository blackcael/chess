package service;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.InvalidMoveException;
import dataaccess.DataAccessException;
import dataaccess.Database;
import handler.WebSocketConnection;
import intermediary.GameOverException;
import intermediary.InvalidAuthException;
import intermediary.ObserverException;
import intermediary.WrongTurnException;
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
        this.connection = new WebSocketConnection("null", session);
        this.notificationService =  new WebSocketNotificationService(database, connection);
    }

    public void service(UserGameCommand command) throws IOException {
        try {
            username = authDataBase.getAuth(command.getAuthString()).username();
            connection = new WebSocketConnection(username, session);
            notificationService = new WebSocketNotificationService(database, connection);
            validateAuthToken(command.getAuthString());
            switch (command.getCommandType()) {
                case CONNECT -> connect((ConnectCommand) command);
                case LEAVE -> leave((LeaveCommand) command);
                case MAKE_MOVE -> makeMove((MakeMoveCommand) command);
                case RESIGN -> resign((ResignCommand) command);
            }
        }catch (InvalidAuthException e) {
            notificationService.alertRoot(new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Error: Invalid AuthToken"));
        } catch (DataAccessException e) {
            notificationService.alertRoot( new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Error: Data Access Exception"));
        } catch (InvalidMoveException e) {
            notificationService.alertRoot( new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Error: Invalid Move!"));
        } catch (GameOverException e) {
            notificationService.alertRoot( new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Error: Game is already over!"));
        } catch (ObserverException e){
            notificationService.alertRoot( new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Error: This action cannot be executed as an Observer!"));
        }
        catch (WrongTurnException e){
            notificationService.alertRoot( new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Error: It is not your turn!"));
        }
        catch(NullPointerException e){
            notificationService.alertRoot(new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Error: Invalid Game ID!"));
        }
    }

    private void connect(ConnectCommand command) throws DataAccessException, IOException {
        database.connectPlayerToGameSession(connection, command.getGameID());
        int gameID = command.getGameID();
        notificationService.alertRoot( new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, gameDataBase.getGame(command.getGameID()).game()));
        notificationService.alertOthers(command.getGameID(), new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, username + " has connected to the game as "+ getJoinType(command.getGameID())+". Welcome!"));
    }

    private void leave(LeaveCommand leaveCommand) throws DataAccessException, IOException {
        //SQL interactions
        GameData gameData = gameDataBase.getGame(leaveCommand.getGameID());
        String updatedWhiteUsername = gameData.whiteUsername();
        String updatedBlackUsername = gameData.blackUsername();
        if(updatedWhiteUsername != null){
            if(updatedWhiteUsername.equals(username)){
                updatedWhiteUsername = null;
            }
        }
        if(updatedBlackUsername != null ){
            if(updatedBlackUsername.equals(username)){
                updatedBlackUsername = null;
            }
        }
        GameData updatedGameData =new GameData(gameData.gameID(), updatedWhiteUsername, updatedBlackUsername, gameData.gameName(), gameData.game());
        gameDataBase.updateGame(updatedGameData);

        //gameSessionInteraction

        notificationService.alertOthers(leaveCommand.getGameID(), new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, username + " has left the game. Adios!"));
        database.disconnectPlayerFromGameSession(connection, leaveCommand.getGameID());
    }

    private void makeMove(MakeMoveCommand makeMoveCommand) throws DataAccessException, InvalidMoveException, GameOverException, IOException, ObserverException, WrongTurnException {
        //color issues?
        validateGameIsNotOver(makeMoveCommand.getGameID());
        validateCallerIsPlayer(makeMoveCommand.getGameID());
        validateCorrectTurn(makeMoveCommand.getGameID());

        GameData gameData = gameDataBase.getGame(makeMoveCommand.getGameID());
        ChessGame updatedGame = gameData.game();
        updatedGame.makeMove(makeMoveCommand.getMove());
        GameData updatedGameData = new GameData(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), updatedGame);
        gameDataBase.updateGame(updatedGameData);

        String gameStatusMessage = null;
        if(updatedGame.isInCheckmate(ChessGame.TeamColor.WHITE)){
            updateGameAsFinished(makeMoveCommand.getGameID());
            gameStatusMessage = "White ("+ gameData.whiteUsername() +") is in Checkmate! Game Over!";
        }else if (updatedGame.isInCheckmate(ChessGame.TeamColor.BLACK)){
            updateGameAsFinished(makeMoveCommand.getGameID());
            gameStatusMessage = "Black ("+ gameData.blackUsername() +") is in Checkmate! Game Over!";
        }else if (updatedGame.isInStalemate(ChessGame.TeamColor.WHITE) || updatedGame.isInStalemate(ChessGame.TeamColor.BLACK)){
            updateGameAsFinished(makeMoveCommand.getGameID());
            gameStatusMessage = "Wicked Stalemate! Game Over!";
        }else if (updatedGame.isInCheck(ChessGame.TeamColor.WHITE)){
            gameStatusMessage = "White ("+ updatedGameData.whiteUsername()+") is in Check!";
        }else if (updatedGame.isInCheck(ChessGame.TeamColor.BLACK)){
            gameStatusMessage = "Black ("+ updatedGameData.blackUsername()+") is in Check!";
        }

        notificationService.alertEveryone(makeMoveCommand.getGameID(), new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, gameDataBase.getGame(makeMoveCommand.getGameID()).game()));
        notificationService.alertOthers(makeMoveCommand.getGameID(), new NotificationMessage(
                ServerMessage.ServerMessageType.NOTIFICATION,
                username + " has moved a " +
                        getPieceFromMove(gameDataBase.getGame(makeMoveCommand.getGameID()).game(), makeMoveCommand.getMove()).toString() +
                        " from " + makeMoveCommand.getMove().getStartPosition().toString() +
                        " to " + makeMoveCommand.getMove().getEndPosition().toString()
                )
        );
        if(gameStatusMessage != null){
            notificationService.alertEveryone(makeMoveCommand.getGameID(), new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, gameStatusMessage));
        }
    }

    private void resign(ResignCommand command) throws GameOverException, DataAccessException, IOException, ObserverException {
        validateGameIsNotOver(command.getGameID());
        validateCallerIsPlayer(command.getGameID());
        updateGameAsFinished(command.getGameID());
        notificationService.alertEveryone(command.getGameID(), new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, username + " has resigned. Game over man!"));
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

    private void validateCallerIsPlayer(int gameID) throws DataAccessException, ObserverException {
        boolean isPlayer = false;
        GameData gameData = gameDataBase.getGame(gameID);
        if(gameData.whiteUsername() != null) {
            if (gameData.whiteUsername().equals(username)) {
                isPlayer = true;
            }
        }
        if(gameData.blackUsername() != null){
            if(gameData.blackUsername().equals(username)){
                isPlayer = true;
            }
        }
        if(!isPlayer){
            throw new ObserverException();
        }
    }

    private void validateCorrectTurn(int gameID) throws DataAccessException, WrongTurnException {
        boolean isMyTurn = false;
        GameData gameData = gameDataBase.getGame(gameID);
        if(gameData.whiteUsername() != null) {
            if (gameData.whiteUsername().equals(username)) {
                if(gameData.game().getTeamTurn() == ChessGame.TeamColor.WHITE){
                    isMyTurn = true;
                }
            }
        }
        if(gameData.blackUsername() != null){
            if(gameData.blackUsername().equals(username)){
                if(gameData.game().getTeamTurn() == ChessGame.TeamColor.BLACK){
                    isMyTurn = true;
                }
            }
        }
        if(!isMyTurn){
            throw new WrongTurnException();
        }
    }

    private String getJoinType(int gameID) throws DataAccessException {
        GameData gameData = gameDataBase.getGame(gameID);
        if(gameData.whiteUsername() != null) {
            if (gameData.whiteUsername().equals(username)) {
                return "WHITE";
            }
        }
        if(gameData.blackUsername() != null){
            if(gameData.blackUsername().equals(username)){
                return "BLACK";
            }
        }
        return "an OBSERVER";
    }

    private ChessPiece.PieceType getPieceFromMove(ChessGame game, ChessMove move){
        return game.getBoard().getPiece(move.getEndPosition()).getPieceType();
    }
}
