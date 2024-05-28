package handler;

import dataaccess.Database;
import service.LogoutService;

public class LogoutHandler extends BaseHandler{
    public LogoutHandler(Database database) {
        super(database);
    }

    @Override
    protected Object service(String authToken, Object nullObject) throws Exception {
        LogoutService logoutService = new LogoutService(database);
        logoutService.logout(authToken);
        return null;
    }
}
