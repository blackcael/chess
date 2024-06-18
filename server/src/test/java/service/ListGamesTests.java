package service;

import exceptions.InvalidAuthException;
import intermediary.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ListGamesTests extends ServiceTestBase {
    //setup
    // (initialize user with valid auth token)

    public ListGamesTests() throws Exception {
    }
    @BeforeEach
    @AfterEach
    public void clearAll() throws Exception{
        ClearService clearService = new ClearService(database);
        clearService.clear();
    }

    @Test
    public void simpleListGamesTest() throws Exception {
        String authToken = generateValidAuthToken();
        createGameService.createGame(authToken, new CreateGameRequest("ThePhantomMenace"));
        createGameService.createGame(authToken, new CreateGameRequest("AttackOfTheClones"));
        createGameService.createGame(authToken, new CreateGameRequest("RevengeOfTheSith"));
        ListGamesService listGamesService = new ListGamesService(database);
        ListGamesResponse listGamesResponse = listGamesService.listGames(authToken);
        for(ListGamesSubData game : listGamesResponse.games()){
            System.out.println(game.gameName() +" "+ game.gameID());
        }
        assertNotNull(listGamesResponse);
    }

    @Test
    public void invalidAuthTokenTest() throws Exception{
        String authToken = generateValidAuthToken();
        createGameService.createGame(authToken, new CreateGameRequest("ThePhantomMenace"));
        createGameService.createGame(authToken, new CreateGameRequest("AttackOfTheClones"));
        createGameService.createGame(authToken, new CreateGameRequest("RevengeOfTheSith"));
        ListGamesService listGamesService = new ListGamesService(database);
        assertThrows(InvalidAuthException.class, () -> {
            ListGamesResponse listGamesResponse = listGamesService.listGames("66");
        });
    }
}
