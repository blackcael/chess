package service;

import intermediary.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JoinGameTests extends ServiceTestBase{
    //setup
    // (initialize user with valid auth token)

    public JoinGameTests() throws Exception {
    }

    @BeforeEach @AfterEach
    public void clearAll() throws Exception{
        ClearService clearService = new ClearService(database);
        clearService.clear();
    }


    @Test
    public void simpleJoinGamesTest() throws Exception {
        String authToken = generateValidAuthToken();
        createGameService.createGame(authToken, new CreateGameRequest("ThePhantomMenace"));
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
        String authToken = generateValidAuthToken();
        assertThrows(BadRequestException.class, () -> {
            joinGameService.joinGame(authToken, new JoinGameRequest("WHITE", 66));
        });
    }

    @Test
    public void joinInvalidColor() throws Exception {
        String authToken = generateValidAuthToken();
        createGameService.createGame(authToken, new CreateGameRequest("ThePhantomMenace"));
        ListGamesResponse listGamesResponse = listGamesService.listGames(authToken);
        int testID = listGamesResponse.games().getFirst().gameID();
        JoinGameRequest joinGameRequest = new JoinGameRequest("BLACK", testID);
        joinGameService.joinGame(authToken, joinGameRequest);
        assertThrows(AlreadyTakenException.class, () -> {
            joinGameService.joinGame(authToken, new JoinGameRequest("BLACK", testID));
        });
    }

}
