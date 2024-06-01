package dataaccess;

import org.junit.jupiter.api.Test;

public class SqlAuthDAOTests extends TestBase{

    @Test
    void createAuthTest() throws DataAccessException {
        database.authDataBase.createAuth(sampleAuthData);
    }
}
