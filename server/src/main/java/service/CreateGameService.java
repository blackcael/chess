package service;

import dataaccess.DataAccessException;
import dataaccess.Database;
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
            throw new BadRequestException("Error: bad request");
        }
        GameData gameData = new GameData(database.getGameGenID(), null, null, gameName, new ChessGame());
        gameDataBase.createGame(gameData);
        return new CreateGameResponse(gameName);
    }

    private boolean isDuplicateName(String gameName){
        //TODO implement;
        return false;
    }

}
