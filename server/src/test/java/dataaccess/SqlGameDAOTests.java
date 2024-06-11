package dataaccess;

import intermediary.ListGamesSubData;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;

import javax.xml.crypto.Data;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SqlGameDAOTests extends TestBase{

    @BeforeEach
    public void clearAll() throws Exception{
        ClearService clearService = new ClearService(database);
        clearService.clear();
    }

    @Test
    public void positiveCreateGameTest() throws DataAccessException{
        int id = database.gameDataBase.createGame(sampleGameData);
        GameData returnData = database.gameDataBase.getGame(id);
        assertEquals(returnData.whiteUsername(), sampleGameData.whiteUsername());
    }

    @Test
    public void negativeCreateGameTest() throws DataAccessException{
        //?
    }

    @Test
    public void positiveGetGameTest() throws DataAccessException{
        int id = database.gameDataBase.createGame(sampleGameData);
        GameData resultData = database.gameDataBase.getGame(id);
        assertEquals(resultData.whiteUsername(), sampleGameData.whiteUsername());
    }

    @Test
    public void negativeGetGameTest() throws DataAccessException{
        assertNull(database.gameDataBase.getGame(invalidGameData.gameID()));
    }

    @Test
    public void positiveGetGameListTest() throws DataAccessException{
        gameList.add(lgsd1);
        gameList.add(lgsd2);
        gameList.add(lgsd3);
        database.gameDataBase.createGame(sampleGameData);
        database.gameDataBase.createGame(sampleGameData2);
        database.gameDataBase.createGame(sampleGameData3);
        ArrayList<ListGamesSubData> resultList = database.gameDataBase.getGameList();
        System.out.println(resultList.toString());
    }

    @Test
    public void negativeGetGameListTest() throws DataAccessException{
        //? this cant really fail, it returns null or nothing?
    }

    @Test
    public void positiveUpdateGameTest() throws DataAccessException{
        database.gameDataBase.createGame(sampleGameData);
        database.gameDataBase.updateGame(updatedGameData);
        GameData resultGameData = database.gameDataBase.getGame(updatedGameData.gameID());
        assertEquals(resultGameData.whiteUsername(), updatedGameData.whiteUsername());
    }

    @Test
    public void negativeUpdateGameTest() throws DataAccessException{
        //trying to update a game that DNE (doesn't break system, no error is thrown)
        database.gameDataBase.updateGame(invalidGameData);
    }

    @Test
    public void clearTest() throws DataAccessException{
        database.gameDataBase.clear();
        assertTrue(database.gameDataBase.isEmpty());

    }
}
