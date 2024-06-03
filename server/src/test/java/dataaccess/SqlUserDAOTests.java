package dataaccess;

import model.UserData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SqlUserDAOTests extends TestBase{

    @Test
    public void positiveTestCreateUser() throws DataAccessException {
        database.userDataBase.createUser(sampleUser);
        UserData returnData = database.userDataBase.getUser(sampleUser.username());
        assertEquals(sampleUser.username(), returnData.username());
        assertEquals(sampleUser.password(), returnData.password());
    }

    @Test
    public void negativeTestCreateUser() throws DataAccessException {
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
        //cry
    }

    @Test
    public void positiveClearTest() throws DataAccessException{
        database.userDataBase.clear();
        assertTrue(database.userDataBase.isEmpty());
    }

}
