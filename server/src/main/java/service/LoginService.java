package service;

import dataaccess.Database;
import intermediary.BadRequestException;
import intermediary.LoginResponse;
import intermediary.LoginRequest;
import model.AuthData;

import java.util.UUID;

public class LoginService extends BaseService{
    public LoginService(Database database) {
        super(database);
    }

    public LoginResponse login(LoginRequest loginRequest) throws BadRequestException {
        if(userDataBase.getUser(loginRequest.username()) == null){
            throw new BadRequestException("Invalid UserName/Password");
        }
        AuthData authData = new AuthData(loginRequest.username(), UUID.randomUUID().toString());
        authDataBase.createAuth(authData);
        return new LoginResponse(loginRequest.username(), authData.authToken());
    }
}
