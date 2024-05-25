package service;

import Intermediary.RegisterRequest;
import Intermediary.RegisterResponse;
import dataaccess.*;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class RegisterService extends BaseService{
    public RegisterService(Database database) {
        super(database);
    }

    public RegisterResponse register(RegisterRequest registerRequest) throws DataAccessException{
        if(userDataBase.getUser(registerRequest.username()) != null){ //TODO is this the wrong kind of equals?
            throw new DataAccessException("UserName Already Taken");
        }
        UserData userData = new UserData(registerRequest.username(), registerRequest.password(), registerRequest.email());
        userDataBase.createUser(userData);
        AuthData authData = new AuthData(userData.username(), UUID.randomUUID().toString());
        authDataBase.createAuth(authData);
        return new RegisterResponse(registerRequest.username(), authData.authToken());
    }
}
