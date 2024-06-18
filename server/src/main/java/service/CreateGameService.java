package service;

import dataaccess.DataAccessException;
import dataaccess.Database;
import exceptions.BadRequestException;
import exceptions.InvalidAuthException;
import intermediary.*;
import model.GameData;
import chess.ChessGame;

public class CreateGameService extends BaseService{
    public CreateGameService(Database database) {
        super(database);
    }

    public CreateGameResponse createGame(String authToken, CreateGameRequest createGameRequest) throws InvalidAuthException, DataAccessException, BadRequestException {
        String gameName = createGameRequest.gameName();
        validateAuthToken(authToken);
        if(isDuplicateName(gameName)){
            throw new BadRequestException();
        }
        GameData gameData = new GameData(database.getGameGenID(), null, null, gameName, new ChessGame());
        GameData resultGameData= new GameData (gameDataBase.createGame(gameData), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), gameData.game());
        return new CreateGameResponse(resultGameData.gameID());
    }

    private boolean isDuplicateName(String gameName) throws DataAccessException {
        for(ListGamesSubData game: gameDataBase.getGameList()){
            String testName = game.gameName();
            if(testName.equals(gameName)){
                return true;
            }
        }
        return false;
    }

}
