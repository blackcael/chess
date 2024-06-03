package service;

import dataaccess.*;
import intermediary.*;
import model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogoutTests {
    Database database = new Database();
    RegisterService registerService = new RegisterService(database);
    RegisterRequest newUserRegisterRequest = new RegisterRequest("cblack1", "p@ssw0rd", "cblack1@byu.edu");
    UserData newUserData = new UserData("cblack1", "p@ssw0rd", "cblack1@byu.edu");
    RegisterResponse registerResponse = registerService.register(newUserRegisterRequest);
    LoginService loginService = new LoginService(database);
    LoginRequest loginRequest = new LoginRequest(newUserRegisterRequest.username(), newUserRegisterRequest.password());

    public LogoutTests() throws Exception {
    }
    @AfterEach
    public void clearAll() throws Exception{
        ClearService clearService = new ClearService(database);
        clearService.clear();
    }

    @Test
    public void basicLogoutTest() throws Exception{
        LogoutService logoutService = new LogoutService(database);
        String testAuthToken = registerResponse.authToken();
        logoutService.logout(testAuthToken);
        assertNull(database.authDataBase.getAuth(testAuthToken));
    }

    @Test
    public void invalidLogoutTest() throws Exception{
        LogoutService logoutService = new LogoutService(database);
        String testAuthToken = registerResponse.authToken();
        logoutService.logout(testAuthToken);
        assertThrows(InvalidAuthException.class, () -> {
            logoutService.logout(testAuthToken);
        });
    }
}
