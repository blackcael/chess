package handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.Database;

import service.RegisterService;
import intermediary.RegisterRequest;
import intermediary.RegisterResponse;
import spark.*;

public class RegisterHandler extends BaseHandler{
    public RegisterHandler(Database database) {
        super(database);
    }

    //1.
    private RegisterRequest jsonToClass(Request request){
        return new Gson().fromJson(String.valueOf(request), RegisterRequest.class);
    }
    //2.
    private RegisterResponse service(RegisterRequest registerRequest) throws Exception {
        RegisterService registerService = new RegisterService(database);
        return registerService.register(registerRequest);
    }

    //4.
    public Object handleRequest(Request request, Response response) throws Exception {
        RegisterRequest registerRequest = jsonToClass(request);
        return classToJson(response, service(registerRequest));
    }





}
