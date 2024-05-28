package handler;

import dataaccess.Database;
import service.ClearService;
import spark.*;


public class ClearHandler extends BaseHandler{
    public ClearHandler(Database database) {
        super(database);
    }
    public Object handleRequest(Request request, Response response){
        ClearService clearService = new ClearService(database);
        clearService.clear();
        return classToJson(response, null); //TODO, RETURN SUCCESS
    }
}