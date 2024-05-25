package service;

import dataaccess.AuthDAO;
import dataaccess.Database;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.AuthData;

public class BaseService {
    AuthDAO authDataBase;
    GameDAO gameDataBase;
    UserDAO userDataBase;
    protected BaseService(Database database){
        this.authDataBase = database.authDataBase;
        this.gameDataBase = database.gameDataBase;
        this.userDataBase = database.userDataBase;
    }

    protected boolean validateAuthToken(String authToken){
        return(authDataBase.getAuth(authToken) != null); //TODO: is this the right kind of equals?
    }
}
