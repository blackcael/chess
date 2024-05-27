package handler;

import com.google.gson.Gson;
import dataaccess.Database;
import intermediary.ListGamesResponse;
import service.ListGamesService;
import service.LogoutService;
import spark.Request;
import spark.Response;

public class ListGamesHandler extends BaseHandler{
    public ListGamesHandler(Database database) {
        super(database);
    }
    //2.
    private ListGamesResponse service(String authToken) throws Exception {
        ListGamesService listGamesService = new ListGamesService(database);
        return listGamesService.listGames(authToken);
    }
    //4.
    public Object handleRequest(Request request, Response response) throws Exception {
        String authToken = parseOutAuthToken(request);
        return classToJson(response, service(authToken));
    }
}
