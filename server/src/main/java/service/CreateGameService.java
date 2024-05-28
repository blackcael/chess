package service;

import dataaccess.DataAccessException;
import dataaccess.Database;
import dataaccess.GameDAO;
import intermediary.BadRequestException;
import intermediary.CreateGameResponse;
import intermediary.InvalidAuthException;
import model.GameData;
import chess.ChessGame;

public class CreateGameService extends BaseService{
    public CreateGameService(Database database) {
        super(database);
    }

    public CreateGameResponse createGame(String authToken, String gameName) throws InvalidAuthException, DataAccessException, BadRequestException {
        validateAuthToken(authToken);
        if(isDuplicateName(gameName)){
            throw new BadRequestException();
        }
        GameData gameData = new GameData(database.getGameGenID(), null, null, gameName, new ChessGame());
        gameDataBase.createGame(gameData);
        return new CreateGameResponse(gameName);
    }

    private boolean isDuplicateName(String gameName){
        for(GameDAO.ListGamesSubData game: gameDataBase.getGameList()){
            String testName = game.gameName();
            if(testName.equals(gameName)){
                return true;
            }
        }
        return false;
    }

}
