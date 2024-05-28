package handler;

import com.google.gson.Gson;
import dataaccess.*;
import intermediary.*;
import spark.Request;
import spark.Response;

import java.lang.reflect.Type;

public abstract class BaseHandler {
    Database database;
    protected BaseHandler(Database database) {
        this.database = database;
    }

    //1.
    protected String parseOutAuthToken(Request request) {
        return request.headers("Authorization");
    }

    //2.
    protected <C> C jsonToClass(Request request, Type clazz) {
        return new Gson().fromJson(request.body(), clazz);
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
    public <C> Object handleRequest(Request request, Response response, Type clazz) throws Exception {
        String authToken = parseOutAuthToken(request);
        C joinGameRequest = jsonToClass(request, clazz);
        return classToJson(response, sensitiveService(authToken, joinGameRequest));
    }

}