package service;

import dataaccess.*;
import exceptions.InvalidAuthException;
import intermediary.*;
import model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoginTests extends ServiceTestBase{
    Database database = new Database();
    RegisterService registerService = new RegisterService(database);
    RegisterRequest newUserRegisterRequest = new RegisterRequest("cblack1", "p@ssw0rd", "cblack1@byu.edu");
    UserData newUserData = new UserData("cblack1", "p@ssw0rd", "cblack1@byu.edu");
    RegisterResponse registerResponse = registerService.register(newUserRegisterRequest);

    public LoginTests() throws Exception {
    }
    @BeforeEach
    @AfterEach
    public void clearAll() throws Exception{
        ClearService clearService = new ClearService(database);
        clearService.clear();
    }

    @Test
    public void basicLoginTest() throws Exception{
        registerService.register(newUserRegisterRequest);
        LoginResponse loginResponse = loginService.login(loginRequest);
        assertEquals(database.authDataBase.getAuth(loginResponse.authToken()).authToken(), loginResponse.authToken());
    }

    @Test
    public void invalidLoginDataTest() throws Exception{
        LoginRequest loginRequest = new LoginRequest("cblack1", "BadPass");
        assertThrows(InvalidAuthException.class, () -> {
            LoginResponse loginResponse = loginService.login(loginRequest);
        });
    }
}
