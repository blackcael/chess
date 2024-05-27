package handler;

import dataaccess.Database;
import intermediary.CreateGameResponse;
import service.CreateGameService;
import spark.Request;
import spark.Response;

public class CreateGameHandler extends BaseHandler{
    public CreateGameHandler(Database database) {
        super(database);
    }
    //2.
    private CreateGameResponse service(String authToken, String gameName) throws Exception {
        CreateGameService createGameService = new CreateGameService(database);
        return createGameService.createGame(authToken, gameName);
    }

    //4.
    public Object handleRequest(Request request, Response response) throws Exception {
        String authToken = parseOutAuthToken(request);
        String gameName = jsonToClass(request, String.class);
        return classToJson(response, service(authToken, gameName));
    }
}
