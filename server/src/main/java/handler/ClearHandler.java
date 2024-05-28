package handler;

import com.google.gson.Gson;
import dataaccess.Database;
import intermediary.ResponseCodeAndObject;
import service.ClearService;
import spark.*;


public class ClearHandler extends BaseHandler{
    public ClearHandler(Database database) {
        super(database);
    }
    public Object handleRequest(Request request, Response response){
        ClearService clearService = new ClearService(database);
        clearService.clear();
        return classToJson(response, null);
    }

    @Override
    protected Object classToJson(Response response, ResponseCodeAndObject rcao) {
        response.type("application/json");
        response.status(200);
        return new Gson().toJson(null);
    }
}