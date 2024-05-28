package handler;

import com.google.gson.Gson;
import dataaccess.*;
import intermediary.BadRequestException;
import intermediary.CaelsHandyCompilationError;
import intermediary.InvalidAuthException;
import intermediary.ResponseCodeAndObject;
import spark.Request;
import spark.Response;

import java.lang.reflect.Type;

public abstract class BaseHandler {
    Database database;
    Type Clazz;
    protected BaseHandler(Database database) {
        this.database = database;
    }

    //1.
    protected String parseOutAuthToken(Request request) {
        return new Gson().fromJson(request.headers("authentication"), String.class);
    }

    //2.
    protected <clazz> clazz jsonToClass(Request request, Type clazz) {
        return new Gson().fromJson(request.body(), clazz);
    }

    //3.
    protected Object service(String authToken, Object inputObject) throws Exception {
        throw new CaelsHandyCompilationError("ERROR: MUST OVERRIDE SERVICE");
    }

    //4.
    public ResponseCodeAndObject sensitiveService(String authToken, Object inputObject) throws Exception {
        int responseCode = 0;
        Object responseObj = null;
        try {
            responseCode = 200;
            responseObj = service(authToken, inputObject);
        } catch (BadRequestException e) {
            responseCode = 400;
            responseObj = e;
        } catch (InvalidAuthException e) {
            responseCode = 401;
            responseObj = e;
        }
        return new ResponseCodeAndObject(responseCode, responseObj);
    }

    //5.
    protected Object classToJson(Response response, ResponseCodeAndObject rcao) {
        response.type("application/json");
        response.status(rcao.responseCode());
        return new Gson().toJson(rcao.responseObject());
    }

    //6.
    public <clazz> Object handleRequest(Request request, Response response, Type clazz) throws Exception {
        String authToken = parseOutAuthToken(request);
        clazz joinGameRequest = jsonToClass(request, clazz);
        return classToJson(response, sensitiveService(authToken, joinGameRequest));
    }

}    