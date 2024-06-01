package service;

import dataaccess.*;
import intermediary.*;
import model.UserData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateGameTests {
    //setup
    // (initialize user with valid auth token)
    Database database = new Database();
    RegisterService registerService = new RegisterService(database);
    RegisterRequest newUserRegisterRequest = new RegisterRequest("cblack1", "p@ssw0rd", "cblack1@byu.edu");
    UserData newUserData = new UserData("cblack1", "p@ssw0rd", "cblack1@byu.edu");
    RegisterResponse registerResponse = registerService.register(newUserRegisterRequest);
    String authToken = registerResponse.authToken();

    public CreateGameTests() throws Exception {
    }

    @Test
    public void simpleCreateGameTest() throws Exception {
        CreateGameService createGameService = new CreateGameService(database);
        CreateGameRequest createGameRequest = new CreateGameRequest("FPS_CHESS");
        CreateGameResponse createGameResponse = (createGameService.createGame(authToken, createGameRequest));
        assertEquals(database.gameDataBase.getGame(createGameResponse.gameID()).gameName(), createGameRequest.gameName());
    }

    @Test
    public void duplicateGameNameTest() throws Exception {
        CreateGameService createGameService = new CreateGameService(database);
        CreateGameRequest createGameRequest = new CreateGameRequest("FPS_CHESS");
        CreateGameResponse createGameResponse = (createGameService.createGame(authToken, createGameRequest));
        assertThrows(BadRequestException.class, () -> {
            createGameService.createGame(authToken, createGameRequest);
        });
    }

    @Test
    public void invalidAuthErrorTest() throws Exception{
        LogoutService logoutService = new LogoutService(database);
        logoutService.logout(authToken);
        CreateGameService createGameService = new CreateGameService(database);
        CreateGameRequest createGameRequest = new CreateGameRequest("FPS_CHESS");
        assertThrows(InvalidAuthException.class, () -> {
            createGameService.createGame(authToken, createGameRequest);
        });

    }
}
