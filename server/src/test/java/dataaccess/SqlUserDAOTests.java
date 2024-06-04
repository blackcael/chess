package dataaccess;

import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;

import static org.junit.jupiter.api.Assertions.*;

public class SqlUserDAOTests extends TestBase{

    @BeforeEach
    public void clearAll() throws Exception{
        ClearService clearService = new ClearService(database);
        clearService.clear();
    }

    @Test
    public void positiveCreateUserTest() throws DataAccessException {
        database.userDataBase.createUser(sampleUser);
        UserData returnData = database.userDataBase.getUser(sampleUser.username());
        assertEquals(sampleUser.username(), returnData.username());
        assertEquals(sampleUser.password(), returnData.password());
    }

    @Test
    public void negativeCreateUserTest() throws DataAccessException {
        //not sure what to do for this...
    }

    @Test
    public void  positiveGetUserTest() throws DataAccessException {
        database.userDataBase.createUser(sampleUser);
        UserData returnData = database.userDataBase.getUser(sampleUser.username());
        assertEquals(sampleUser.username(), returnData.username());
        assertEquals(sampleUser.password(), returnData.password());
    }

    @Test
    public void negativeGetUserTest() throws DataAccessException{
        assertThrows(DataAccessException.class, () -> {
            database.userDataBase.getUser(unregisteredUser.username());
        });
    }

    @Test
    public void positiveClearTest() throws DataAccessException{
        database.userDataBase.clear();
        assertTrue(database.userDataBase.isEmpty());
    }

}
