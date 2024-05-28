package unittests.service;

import dataaccess.*;
import intermediary.*;
import model.UserData;
import org.junit.jupiter.api.Test;
import service.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JoinGameTests {
    //setup
    // (initialize user with valid auth token)
    Database database = new Database(new MemoryAuthDAO(), new MemoryGameDAO(), new MemoryUserDAO());
    RegisterService registerService = new RegisterService(database);
    RegisterRequest newUserRegisterRequest = new RegisterRequest("cblack1", "p@ssw0rd", "cblack1@byu.edu");
    UserData newUserData = new UserData("cblack1", "p@ssw0rd", "cblack1@byu.edu");
    RegisterResponse registerResponse = registerService.register(newUserRegisterRequest);
    String authToken = registerResponse.authToken();

    public JoinGameTests() throws Exception {
    }


    @Test
    public void simpleJoinGamesTest() throws Exception {
        CreateGameService createGameService = new CreateGameService(database);
        createGameService.createGame(authToken, new CreateGameRequest("ThePhantomMenace"));
        JoinGameService joinGameService = new JoinGameService(database);
        ListGamesService listGamesService = new ListGamesService(database);
        ListGamesResponse listGamesResponse = listGamesService.listGames(authToken);
        int testID = listGamesResponse.games().getFirst().gameID();
        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE", testID);
        joinGameService.joinGame(authToken, joinGameRequest);
        System.out.println("Expected: "+ database.authDataBase.getAuth(authToken).username());
        System.out.println("Actual: "+database.gameDataBase.getGame(testID).whiteUsername());
        assertEquals(database.authDataBase.getAuth(authToken).username(), database.gameDataBase.getGame(testID).whiteUsername());
    }

    @Test
    public void joinInvalidGame() throws Exception {
        JoinGameService joinGameService = new JoinGameService(database);
        assertThrows(BadRequestException.class, () -> {
            joinGameService.joinGame(authToken, new JoinGameRequest("WHITE", 66));
        });
    }

    @Test
    public void joinInvalidColor() throws Exception {
        CreateGameService createGameService = new CreateGameService(database);
        createGameService.createGame(authToken, new CreateGameRequest("ThePhantomMenace"));
        JoinGameService joinGameService = new JoinGameService(database);
        ListGamesService listGamesService = new ListGamesService(database);
        ListGamesResponse listGamesResponse = listGamesService.listGames(authToken);
        int testID = listGamesResponse.games().getFirst().gameID();
        JoinGameRequest joinGameRequest = new JoinGameRequest("BLACK", testID);
        joinGameService.joinGame(authToken, joinGameRequest);
        assertThrows(AlreadyTakenException.class, () -> {
            joinGameService.joinGame(authToken, new JoinGameRequest("BLACK", testID));
        });
    }

}
