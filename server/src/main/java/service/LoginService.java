package service;

import dataaccess.Database;
import intermediary.BadRequestException;
import intermediary.InvalidAuthException;
import intermediary.LoginResponse;
import intermediary.LoginRequest;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class LoginService extends BaseService{
    public LoginService(Database database) {
        super(database);
    }

    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        if(userDataBase.getUser(loginRequest.username()) == null){
            throw new InvalidAuthException();
        }
        UserData userData = userDataBase.getUser(loginRequest.username());
        if(!userData.password().equals(loginRequest.password())){
            throw new InvalidAuthException();
        }
        AuthData authData = new AuthData(loginRequest.username(), UUID.randomUUID().toString());
        authDataBase.createAuth(authData);
        return new LoginResponse(loginRequest.username(), authData.authToken());
    }
}
