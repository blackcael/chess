package unitServiceTests;

import dataaccess.*;
import intermediary.*;
import model.UserData;
import org.junit.jupiter.api.Test;
import service.CreateGameService;
import service.LogoutService;
import service.RegisterService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateGameTests {
    //setup
    // (initialize user with valid auth token)
    Database database = new Database(new MemoryAuthDAO(), new MemoryGameDAO(), new MemoryUserDAO());
    RegisterService registerService = new RegisterService(database);
    RegisterRequest newUserRegisterRequest = new RegisterRequest("turboSHID", "p@ssw0rd", "cblack1@byu.edu");
    UserData newUserData = new UserData("turboSHID", "p@ssw0rd", "cblack1@byu.edu");
    RegisterResponse registerResponse = registerService.register(newUserRegisterRequest);
    String authToken = registerResponse.authToken();

    public CreateGameTests() throws DataAccessException {
    }

    @Test
    public void simpleCreateGameTest() throws Exception {
        CreateGameService createGameService = new CreateGameService(database);
        String sampleGameName = "FPS_CHESS";
        CreateGameResponse createGameResponse = (createGameService.createGame(authToken, sampleGameName));
        assertEquals(createGameResponse.gameName(), sampleGameName);
    }

    @Test
    public void duplicateGameNameTest() throws Exception {
        CreateGameService createGameService = new CreateGameService(database);
        String sampleGameName = "FPS_CHESS";
        CreateGameResponse createGameResponse = (createGameService.createGame(authToken, sampleGameName));
        assertThrows(BadRequestException.class, () -> {
            createGameService.createGame(authToken, sampleGameName);
        });
    }

    @Test
    public void invalidAuthErrorTest() throws Exception{
        LogoutService logoutService = new LogoutService(database);
        logoutService.logout(authToken);
        CreateGameService createGameService = new CreateGameService(database);
        String sampleGameName = "FPS_CHESS";
        assertThrows(InvalidAuthException.class, () -> {
            createGameService.createGame(authToken, sampleGameName);
        });

    }
}
