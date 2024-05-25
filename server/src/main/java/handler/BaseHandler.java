package handler;

import dataaccess.*;

import java.lang.reflect.Type;

public abstract class BaseHandler {
    Database database;
    Type Clazz;
    protected BaseHandler(Database database) {
        this.database = database;
    }
    //HANDLERS WILL ALSO HANDLE ERROR HANDLING THROWN BY SERVICE


    //PLAN FOR STRUCTURE: 4 functions
    //1. jsonToClass : converts the received Json to class
    //2. service : constructs and calls a service function (does the meat!)
    //3. classToJson : converts the class type that the service function returns back into meat
    //4. serviceHandler : executes the three above statements, taking in Json, and returning Json.


    //0.
//    //1.
//    protected <GenObjectIn> GenObjectIn jsonToClass(Request request, Type clazz){
//        return new Gson().fromJson(String.valueOf(request), clazz);
//    }
//    //2.
//    protected <GenObjectIn, GenObjectOut> GenObjectOut service(GenObjectIn genObjectIn) throws Exception {
//        throw new Exception("Service Must be Overwritten!");
//    }
//
//    //3.
//    private <GenObjectOut> Object classToJson(Response response, GenObjectOut genObjectOut){
//        response.type("application/json");
//        return new Gson().toJson(genObjectOut);
//    }
//
//    //4.
//    public <GenObjectIn, GenObjectOut>  Object handleRequest(Request request, Response response) throws Exception {
//        GenObjectIn genObjectIn = jsonToClass(request, GenObjectIn.class);
//        GenObjectOut genObjectOut = service (genObjectIn);
//        return classToJson(response, genObjectOut);
//    }
    //LOTS OF ISSUES HERE
}