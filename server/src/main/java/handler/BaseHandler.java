package handler;

import Intermediary.RegisterRequest;
import Intermediary.RegisterResponse;
import com.google.gson.Gson;
import dataaccess.*;
import spark.*;
import model.AuthData;
import model.UserData;
import service.RegisterService;

import java.lang.reflect.Type;

public abstract class BaseHandler {
    Database database;
    protected BaseHandler(Database database) {
        this.database = database;
    }
    //PLAN FOR STRUCTURE: 4 functions
    //1. jsonToClass : converts the received Json to class
    //2. service : constructs and calls a service function (does the meat!)
    //3. classToJson : converts the class type that the service function returns back into meat
    //4. serviceHandler : executes the three above statements, taking in Json, and returning Json.

    //1.
    protected <GenObjectIn> GenObjectIn jsonToClass(Request request, Type type){
        return new Gson().fromJson(String.valueOf(request), type);
    }
    //2.
    protected  <GenObjectIn, GenObjectOut> GenObjectOut service(GenObjectIn genObjectIn) throws Exception {
        throw new Exception("Service Must be Overwritten!");
    }

    //3.
    private <GenObjectOut> Object classToJson(Response response, GenObjectOut genObjectOut){
        response.type("application/json");
        return new Gson().toJson(genObjectOut);
    }

    //4.
    public <GenObjectIn, GenObjectOut>  Object handleRequest(Request request, Response response) throws Exception {
        GenObjectIn genObjectIn = jsonToClass(request, GenObjectIn);
        GenObjectOut genObjectOut = service (genObjectIn);
        return classToJson(response, genObjectOut);
    }


    protected abstract RegisterRequest jsonToClass(Request request);

    protected abstract RegisterResponse service(RegisterRequest registerRequest) throws DataAccessException;
}