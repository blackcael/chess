package handler;

import dataaccess.Database;
import intermediary.CreateGameResponse;
import intermediary.JoinGameRequest;
import service.CreateGameService;
import service.JoinGameService;
import spark.Request;
import spark.Response;

public class JoinGameHandler extends BaseHandler{
    public JoinGameHandler(Database database) {
        super(database);
    }
    //2.
    private void service(String authToken, JoinGameRequest joinGameRequest) throws Exception {
        JoinGameService joinGameService = new JoinGameService(database);
        joinGameService.joinGame(authToken, joinGameRequest);
    }

    //4.
    public Object handleRequest(Request request, Response response) throws Exception {
        String authToken = parseOutAuthToken(request);
        JoinGameRequest joinGameRequest = jsonToClass(request, JoinGameService.class);
        service(authToken, joinGameRequest);
        return classToJson(response, null);
    }
}
