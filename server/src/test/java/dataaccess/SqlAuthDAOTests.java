package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;

import static org.junit.jupiter.api.Assertions.*;

public class SqlAuthDAOTests extends TestBase{

    @BeforeEach
    public void clearAll() throws Exception{
        ClearService clearService = new ClearService(database);
        clearService.clear();
    }

    @Test
    public void positiveCreateAuthTest() throws DataAccessException {
        database.authDataBase.createAuth(sampleAuthData);
        AuthData resultAuthData1 = database.authDataBase.getAuth(sampleAuthData.authToken());
        System.out.println(resultAuthData1.username());
        assertEquals(resultAuthData1.username(), sampleAuthData.username());
    }

    @Test
    public void negativeCreateAuthTest() throws DataAccessException{
        //? trying to create something with invalid data?
    }

    @Test
    public void positiveGetAuthTest() throws DataAccessException{
        database.authDataBase.createAuth(sampleAuthData);
        AuthData resultAuthData2 = database.authDataBase.getAuth(sampleAuthData.authToken());
        System.out.println(resultAuthData2.username());
        assertEquals(resultAuthData2.username(), sampleAuthData.username());
    }

    @Test
    public void negativeGetAuthTest() throws DataAccessException{
        assertNull(database.authDataBase.getAuth(invalidAuthData.authToken()));
    }

    @Test
    public void positiveDeleteAuthTest() throws DataAccessException{
        database.authDataBase.createAuth(sampleAuthData);
        database.authDataBase.deleteAuth(sampleAuthData.authToken());
        assertNull(database.authDataBase.getAuth(invalidAuthData.authToken()));
    }

    @Test
    public void negativeDeleteAuthTest() throws DataAccessException{
        database.authDataBase.deleteAuth(invalidAuthData.authToken());
        //dont crash
        //assertDoesNotThrow();
    }

    @Test
    public void clearTest() throws DataAccessException {
        database.authDataBase.clear();
        assertTrue(database.authDataBase.isEmpty());
    }
}
