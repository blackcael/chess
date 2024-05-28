package unitServiceTests;

import dataaccess.*;
import intermediary.*;
import model.UserData;
import org.junit.jupiter.api.Test;
import service.LoginService;
import service.RegisterService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoginTests {
    Database database = new Database(new MemoryAuthDAO(), new MemoryGameDAO(), new MemoryUserDAO());
    RegisterService registerService = new RegisterService(database);
    RegisterRequest newUserRegisterRequest = new RegisterRequest("cblack1", "p@ssw0rd", "cblack1@byu.edu");
    UserData newUserData = new UserData("cblack1", "p@ssw0rd", "cblack1@byu.edu");
    RegisterResponse registerResponse = registerService.register(newUserRegisterRequest);

    public LoginTests() throws Exception {
    }

    @Test
    public void basicLoginTest() throws Exception{
        LoginService loginService = new LoginService(database);
        LoginRequest loginRequest = new LoginRequest(newUserRegisterRequest.username(), newUserRegisterRequest.password());
        LoginResponse loginResponse = loginService.login(loginRequest);
        assertEquals(database.authDataBase.getAuth(loginResponse.authToken()).authToken(), loginResponse.authToken());
    }

    @Test
    public void invalidLoginDataTest() throws Exception{
        LoginService loginService = new LoginService(database);
        LoginRequest loginRequest = new LoginRequest("cblack1", "BadPass");
        assertThrows(InvalidAuthException.class, () -> {
            LoginResponse loginResponse = loginService.login(loginRequest);
        });
    }
}
