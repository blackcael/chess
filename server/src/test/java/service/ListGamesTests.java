package service;

import dataaccess.*;
import intermediary.*;
import model.UserData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ListGamesTests {
    //setup
    // (initialize user with valid auth token)
    Database database = new Database(new MemoryAuthDAO(), new MemoryGameDAO(), new MemoryUserDAO());
    RegisterService registerService = new RegisterService(database);
    RegisterRequest newUserRegisterRequest = new RegisterRequest("cblack1", "p@ssw0rd", "cblack1@byu.edu");
    UserData newUserData = new UserData("cblack1", "p@ssw0rd", "cblack1@byu.edu");
    RegisterResponse registerResponse = registerService.register(newUserRegisterRequest);
    String authToken = registerResponse.authToken();

    public ListGamesTests() throws Exception {
    }

    @Test
    public void simpleListGamesTest() throws Exception {
        CreateGameService createGameService = new CreateGameService(database);
        createGameService.createGame(authToken, new CreateGameRequest("ThePhantomMenace"));
        createGameService.createGame(authToken, new CreateGameRequest("AttackOfTheClones"));
        createGameService.createGame(authToken, new CreateGameRequest("RevengeOfTheSith"));
        ListGamesService listGamesService = new ListGamesService(database);
        ListGamesResponse listGamesResponse = listGamesService.listGames(authToken);
        for(GameDAO.ListGamesSubData game : listGamesResponse.games()){
            System.out.println(game.gameName() +" "+ game.gameID());
        }
        assertNotNull(listGamesResponse);
    }

    @Test
    public void invalidAuthTokenTest() throws Exception{
        CreateGameService createGameService = new CreateGameService(database);
        createGameService.createGame(authToken, new CreateGameRequest("ThePhantomMenace"));
        createGameService.createGame(authToken, new CreateGameRequest("AttackOfTheClones"));
        createGameService.createGame(authToken, new CreateGameRequest("RevengeOfTheSith"));
        ListGamesService listGamesService = new ListGamesService(database);
        assertThrows(InvalidAuthException.class, () -> {
            ListGamesResponse listGamesResponse = listGamesService.listGames("66");
        });
    }
}
