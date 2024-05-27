package service;

import com.google.gson.Gson;
import dataaccess.Database;
import intermediary.InvalidAuthException;
import intermediary.RegisterRequest;
import spark.Request;

public class LogoutService extends BaseService{
    public LogoutService(Database database) {
        super(database);
    }
    public void logout(String authToken) throws InvalidAuthException {
        validateAuthToken(authToken);
        authDataBase.deleteAuth(authToken);
    }
}
