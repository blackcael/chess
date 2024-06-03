package dataaccess;

import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.*;

public class SqlGameDAOTests extends TestBase{

    @BeforeEach
    public void clearAll() throws Exception{
        ClearService clearService = new ClearService(database);
        clearService.clear();
    }

    @Test
    public void positiveCreateGameTest() throws DataAccessException{
        database.gameDataBase.createGame(sampleGameData);
        GameData returnData = database.gameDataBase.getGame(sampleGameData.gameID());
        assertEquals(returnData.whiteUsername(), sampleGameData.whiteUsername());
    }

    @Test
    public void negativeCreateGameTest() throws DataAccessException{
        //?
    }

    @Test
    public void positiveGetGameTest() throws DataAccessException{
        database.gameDataBase.createGame(sampleGameData);
        GameData resultData = database.gameDataBase.getGame(sampleGameData.gameID());
        assertEquals(resultData.whiteUsername(), sampleGameData.whiteUsername());
        assertEquals(resultData.gameID(), sampleGameData.gameID());
    }

    @Test
    public void negativeGetGameTest() throws DataAccessException{
        assertThrows(DataAccessException.class, () -> {
            GameData resultGameData = database.gameDataBase.getGame(invalidGameData.gameID());
            System.out.println(resultGameData.toString());
        });
    }

    @Test
    public void positiveGetGameListTest() throws DataAccessException{

    }

    @Test
    public void negativeGetGameListTest() throws DataAccessException{

    }

    @Test
    public void positiveUpdateGameTest() throws DataAccessException{

    }

    @Test
    public void negativeUpdateGameTest() throws DataAccessException{

    }

    @Test
    public void clearTest() throws DataAccessException{
        database.gameDataBase.clear();
        //assertTrue(database.authDataBase.isEmpty());
    }
}
