package service;

import exceptions.InvalidAuthException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogoutTests extends ServiceTestBase {

    public LogoutTests() throws Exception {
    }

    @AfterEach @BeforeEach
    public void clearAll() throws Exception{
        ClearService clearService = new ClearService(database);
        clearService.clear();
    }

    @Test
    public void basicLogoutTest() throws Exception{
        LogoutService logoutService = new LogoutService(database);
        String testAuthToken = generateValidAuthToken();
        logoutService.logout(testAuthToken);
        assertNull(database.authDataBase.getAuth(testAuthToken));
    }

    @Test
    public void invalidLogoutTest() throws Exception{
        LogoutService logoutService = new LogoutService(database);
        String testAuthToken = generateValidAuthToken();
        logoutService.logout(testAuthToken);
        assertThrows(InvalidAuthException.class, () -> {
            logoutService.logout(testAuthToken);
        });
    }
}
