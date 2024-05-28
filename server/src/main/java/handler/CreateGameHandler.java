package handler;

import dataaccess.Database;
import intermediary.CreateGameRequest;
import service.CreateGameService;

public class CreateGameHandler extends BaseHandler{
    public CreateGameHandler(Database database) {
        super(database);
    }
    //2.
    @Override
    protected Object service(String authToken, Object gameName) throws Exception {
        CreateGameService createGameService = new CreateGameService(database);
        return createGameService.createGame(authToken, (CreateGameRequest) gameName);
    }
}
