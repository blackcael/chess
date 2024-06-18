package service;

import dataaccess.DataAccessException;
import dataaccess.Database;
import exceptions.InvalidAuthException;

public class LogoutService extends BaseService{
    public LogoutService(Database database) {
        super(database);
    }
    public void logout(String authToken) throws InvalidAuthException, DataAccessException {
        validateAuthToken(authToken);
        authDataBase.deleteAuth(authToken);
    }
}
