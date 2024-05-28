package handler;

import com.google.gson.Gson;
import dataaccess.*;
import intermediary.*;
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
        return request.headers("Authorization");
    }

    //2.
    protected <Clazz> Clazz jsonToClass(Request request, Type Clazz) {
        return new Gson().fromJson(request.body(), Clazz);
    }

    //3.
    protected Object service(String authToken, Object inputObject) throws Exception {
        throw new CaelsHandyCompilationError("ERROR: MUST OVERRIDE SERVICE");
    }

    //4.
    public ResponseCodeAndObject sensitiveService(String authToken, Object inputObject) throws Exception {
        int responseCode = 200;
        Object responseObj = null;
        try {
            responseObj = service(authToken, inputObject);
        } catch (BadRequestException e) {
            responseCode = 400;
            responseObj = new ErrorResponse(e.toString());
        } catch (InvalidAuthException e) {
            responseCode = 401;
            responseObj = new ErrorResponse(e.toString());
        } catch (AlreadyTakenException e) {
            responseCode = 403;
            responseObj = new ErrorResponse(e.toString());
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
    public <Clazz> Object handleRequest(Request request, Response response, Type Clazz) throws Exception {
        String authToken = parseOutAuthToken(request);
        Clazz joinGameRequest = jsonToClass(request, Clazz);
        return classToJson(response, sensitiveService(authToken, joinGameRequest));
    }

}