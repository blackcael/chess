package dataaccess;

import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SqlGameDAOTests extends TestBase{

    @Test
    public void positiveCreateGameTest() throws DataAccessException{

    }

    @Test
    public void negativeCreateGameTest() throws DataAccessException{

    }

    @Test
    public void positiveGetGameTest() throws DataAccessException{

    }

    @Test
    public void negativeGetGameTest() throws DataAccessException{

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
        database.authDataBase.clear();
        assertTrue(database.authDataBase.isEmpty());
    }
}
