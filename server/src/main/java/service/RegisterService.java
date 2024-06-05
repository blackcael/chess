package service;

import intermediary.AlreadyTakenException;
import intermediary.BadRequestException;
import intermediary.RegisterRequest;
import intermediary.RegisterResponse;
import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

public class RegisterService extends BaseService{
    public RegisterService(Database database) {
        super(database);
    }

    public RegisterResponse register(RegisterRequest registerRequest) throws Exception {
        if(registerRequest.password() == null){
            throw new BadRequestException();
        }

        if(userDataBase.getUser(registerRequest.username()) != null){
            throw new AlreadyTakenException();
        }
        UserData userData = new UserData(registerRequest.username(), hashPassword(registerRequest.password()), registerRequest.email());
        userDataBase.createUser(userData);
        AuthData authData = new AuthData(userData.username(), UUID.randomUUID().toString());
        authDataBase.createAuth(authData);
        return new RegisterResponse(registerRequest.username(), authData.authToken());
    }

    private String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
