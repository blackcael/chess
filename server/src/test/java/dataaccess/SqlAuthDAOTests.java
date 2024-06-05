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
        AuthData resultAuthData = database.authDataBase.getAuth(sampleAuthData.authToken());
        System.out.println(resultAuthData.username());
        assertEquals(resultAuthData.username(), sampleAuthData.username());
    }

    @Test
    public void negativeCreateAuthTest() throws DataAccessException{
        //? trying to create something with invalid data?
    }

    @Test
    public void positiveGetAuthTest() throws DataAccessException{
        database.authDataBase.createAuth(sampleAuthData);
        AuthData resultAuthData = database.authDataBase.getAuth(sampleAuthData.authToken());
        System.out.println(resultAuthData.username());
        assertEquals(resultAuthData.username(), sampleAuthData.username());
    }

    @Test
    public void negativeGetAuthTest() throws DataAccessException{
        assertThrows(DataAccessException.class, () -> {
            AuthData resultAuthData = database.authDataBase.getAuth(invalidAuthData.authToken());
            System.out.println(resultAuthData.toString());
        });
    }

    @Test
    public void positiveDeleteAuthTest() throws DataAccessException{
        database.authDataBase.createAuth(sampleAuthData);
        database.authDataBase.deleteAuth(sampleAuthData.authToken());
        assertThrows(DataAccessException.class, () -> {
            AuthData resultAuthData = database.authDataBase.getAuth(sampleAuthData.authToken());
        });
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
    }
}
