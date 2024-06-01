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
    void negativeTestCreateUser() throws DataAccessException {
        //not sure what to do for this...
    }

    @Test
    void  positiveGetUserTest() throws DataAccessException {
        database.userDataBase.createUser(sampleUser);
        UserData returnData = database.userDataBase.getUser(sampleUser.username());
        assertEquals(sampleUser.username(), returnData.username());
        assertEquals(sampleUser.password(), returnData.password());
    }

}
