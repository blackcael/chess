package handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.Database;
import intermediary.LoginRequest;
import intermediary.LoginResponse;
import service.LoginService;
import spark.Request;
import spark.Response;

public class LoginHandler extends BaseHandler{
    public LoginHandler(Database database) {
        super(database);
    }

    //1.
    private LoginRequest jsonToClass(Request request){
        return new Gson().fromJson(String.valueOf(request), LoginRequest.class);
    }
    //2.
    private LoginResponse service(LoginRequest loginRequest) throws Exception {
        LoginService loginService = new LoginService(database);
        return loginService.login(loginRequest);
    }

    //4.
    public Object handleRequest(Request request, Response response) throws Exception {
        LoginRequest loginRequest = jsonToClass(request);
        LoginResponse loginResponse = service(loginRequest);
        return classToJson(response, loginResponse);
    }

}
