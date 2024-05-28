package handler;

import dataaccess.Database;
import intermediary.LoginRequest;
import service.LoginService;

public class LoginHandler extends BaseHandler{
    public LoginHandler(Database database) {
        super(database);
    }

    @Override
    protected Object service(String authToken, Object loginRequest) throws Exception {
        LoginService loginService = new LoginService(database);
        return loginService.login((LoginRequest) loginRequest);
    }

}
