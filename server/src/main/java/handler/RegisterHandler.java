package handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.Database;

import service.RegisterService;
import Intermediary.RegisterRequest;
import Intermediary.RegisterResponse;
import spark.Request;

public class RegisterHandler extends BaseHandler{
    public RegisterHandler(Database database) {
        super(database);
    }

    @Override
    protected RegisterRequest jsonToClass(Request request){
        return new Gson().fromJson(String.valueOf(request), RegisterRequest.class);
    }

    @Override
    protected RegisterResponse service(RegisterRequest registerRequest) throws DataAccessException {
        RegisterService registerService = new RegisterService(database);
        return registerService.register(registerRequest);
    }





}
