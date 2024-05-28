package handler;

import dataaccess.Database;
import service.ListGamesService;

public class ListGamesHandler extends BaseHandler{
    public ListGamesHandler(Database database) {
        super(database);
    }

    @Override
    protected Object service(String authToken, Object inputObject) throws Exception {
        ListGamesService listGamesService = new ListGamesService(database);
        return listGamesService.listGames(authToken);
    }
}
