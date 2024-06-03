package dataaccess;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SqlAuthDAOTests extends TestBase{

    @Test
    public void positiveCreateAuthTest() throws DataAccessException {
        database.authDataBase.createAuth(sampleAuthData);
    }

    @Test
    public void negativeCreateAuthTest() throws DataAccessException{

    }

    @Test
    public void positiveGetAuthTest() throws DataAccessException{

    }

    @Test
    public void negativeGetAuthTest() throws DataAccessException{

    }

    @Test
    public void positiveDeleteAuthTest() throws DataAccessException{

    }

    @Test
    public void negativeDeleteAuthTest() throws DataAccessException{

    }

    @Test
    public void clearTest() throws DataAccessException {
        database.authDataBase.clear();
        assertTrue(database.authDataBase.isEmpty());
    }
}
