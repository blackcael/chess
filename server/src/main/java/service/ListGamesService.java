package service;

import dataaccess.DataAccessException;
import dataaccess.Database;
import exceptions.InvalidAuthException;
import intermediary.ListGamesResponse;

public class ListGamesService extends BaseService{
    public ListGamesService(Database database) {
        super(database);
    }
    public ListGamesResponse listGames(String authToken) throws InvalidAuthException, DataAccessException {
        validateAuthToken(authToken);
        return new ListGamesResponse(gameDataBase.getGameList());
    }
}
