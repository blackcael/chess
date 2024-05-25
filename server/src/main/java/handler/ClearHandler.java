package handler;

import com.google.gson.Gson;
import dataaccess.Database;
import spark.*;


public class ClearHandler extends BaseHandler{
    public ClearHandler(Database database) {
        super(database);
    }
    public Object handleRequest(Request request, Response response){
        response.type("application/json");
        return new Gson().toJson(null);
    }
}
