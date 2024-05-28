package handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.Database;
import intermediary.BadRequestException;
import intermediary.LoginRequest;
import intermediary.LoginResponse;
import service.LoginService;
import spark.Request;
import spark.Response;

public class LoginHandler extends BaseHandler{
    public LoginHandler(Database database) {
        super(database);
    }

    //2.
    private LoginResponse service(LoginRequest loginRequest) throws Exception {
        LoginService loginService = new LoginService(database);
        return loginService.login(loginRequest);
    }

    //4.
    public Object handleRequest(Request request, Response response) throws Exception {
        LoginRequest loginRequest = jsonToClass(request, LoginRequest.class);
        Object responseObj;
        try {
            response.status(200);
            responseObj = service(loginRequest);
        }
        catch(BadRequestException e){
            response.status(401);
            responseObj = e;
        }
        return classToJson(response, responseObj);
    }

}
