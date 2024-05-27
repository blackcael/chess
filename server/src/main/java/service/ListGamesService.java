package service;

import dataaccess.Database;
import intermediary.InvalidAuthException;
import intermediary.ListGamesResponse;

public class ListGamesService extends BaseService{
    public ListGamesService(Database database) {
        super(database);
    }
    public ListGamesResponse listGames(String authToken) throws InvalidAuthException{
        validateAuthToken(authToken);
        return new ListGamesResponse(gameDataBase.getGameList());
    }
}
