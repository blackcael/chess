package handler;

import dataaccess.Database;
import service.RegisterService;
import intermediary.RegisterRequest;

public class RegisterHandler extends BaseHandler{
    public RegisterHandler(Database database) {
        super(database);
    }

    @Override
    protected Object service(String authToken, Object registerRequest) throws Exception {
        RegisterService registerService = new RegisterService(database);
        return registerService.register((RegisterRequest) registerRequest);
    }







}
