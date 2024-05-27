package handler;

import com.google.gson.Gson;
import dataaccess.Database;
import intermediary.RegisterRequest;
import intermediary.RegisterResponse;
import service.LogoutService;
import service.RegisterService;
import spark.Request;
import spark.Response;

public class LogoutHandler extends BaseHandler{
    public LogoutHandler(Database database) {
        super(database);
    }
    //1.
    private String jsonToClass(Request request) {
        return new Gson().fromJson(String.valueOf(request), String.class);
    }
    //2.
    private void service(String authToken) throws Exception {
        LogoutService logoutService = new LogoutService(database);
        logoutService.logout(authToken);
    }
    //4.
    public Object handleRequest(Request request, Response response) throws Exception {
        String authToken = jsonToClass(request);
        service(authToken);
        return classToJson(response, null);
    }
}
