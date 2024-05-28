package service;

import dataaccess.AuthDAO;
import dataaccess.Database;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import intermediary.InvalidAuthException;
import model.AuthData;

public class BaseService {
    Database database;
    AuthDAO authDataBase;
    GameDAO gameDataBase;
    UserDAO userDataBase;
    protected BaseService(Database database){
        this.database = database;
        this.authDataBase = database.authDataBase;
        this.gameDataBase = database.gameDataBase;
        this.userDataBase = database.userDataBase;
    }

    protected void validateAuthToken(String authToken) throws InvalidAuthException {
        if(authDataBase.getAuth(authToken) == null) {
            throw new InvalidAuthException();
        }
    }
}
