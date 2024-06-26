package service;

import dataaccess.Database;
import intermediary.LoginRequest;
import intermediary.RegisterRequest;
import model.UserData;

public class ServiceTestBase {
    Database database = new Database();
    RegisterService registerService = new RegisterService(database);
    RegisterRequest newUserRegisterRequest = new RegisterRequest("cblack1", "p@ssw0rd", "cblack1@byu.edu");
    UserData newUserData = new UserData("cblack1", "p@ssw0rd", "cblack1@byu.edu");
    LoginService loginService = new LoginService(database);
    LoginRequest loginRequest = new LoginRequest(newUserRegisterRequest.username(), newUserRegisterRequest.password());
    CreateGameService createGameService = new CreateGameService(database);
    JoinGameService joinGameService = new JoinGameService(database);
    ListGamesService listGamesService = new ListGamesService(database);

    protected String generateValidAuthToken() throws Exception {
        return registerService.register(newUserRegisterRequest).authToken();
    }
}
