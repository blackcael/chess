package service;

import dataaccess.DataAccessException;
import dataaccess.Database;
import exceptions.InvalidAuthException;
import intermediary.LoginResponse;
import intermediary.LoginRequest;
import model.AuthData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

public class LoginService extends BaseService{
    public LoginService(Database database) {
        super(database);
    }

    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        if(userDataBase.getUser(loginRequest.username()) == null){
            throw new InvalidAuthException();
        }

        if(!hasValidPassword(loginRequest)){
            throw new InvalidAuthException();
        }
        AuthData authData = new AuthData(loginRequest.username(), UUID.randomUUID().toString());
        authDataBase.createAuth(authData);
        return new LoginResponse(loginRequest.username(), authData.authToken());
    }

    private boolean hasValidPassword(LoginRequest loginRequest) throws DataAccessException {
        var hashedPassword = userDataBase.getUser(loginRequest.username()).password();
        return BCrypt.checkpw(loginRequest.password(), hashedPassword);
    }
}
