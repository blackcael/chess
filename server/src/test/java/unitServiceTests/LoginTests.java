package unitServiceTests;

import dataaccess.*;
import intermediary.LoginRequest;
import intermediary.LoginResponse;
import intermediary.RegisterRequest;
import intermediary.RegisterResponse;
import model.UserData;
import org.junit.jupiter.api.Test;
import intermediary.BadRequestException;
import service.LoginService;
import service.RegisterService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoginTests {
    Database database = new Database(new MemoryAuthDAO(), new MemoryGameDAO(), new MemoryUserDAO());
    RegisterService registerService = new RegisterService(database);
    RegisterRequest newUserRegisterRequest = new RegisterRequest("turboSHID", "p@ssw0rd", "cblack1@byu.edu");
    UserData newUserData = new UserData("turboSHID", "p@ssw0rd", "cblack1@byu.edu");
    RegisterResponse registerResponse = registerService.register(newUserRegisterRequest);

    public LoginTests() throws DataAccessException {
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
        LoginRequest loginRequest = new LoginRequest("WrongUser", "BadPass");
        assertThrows(BadRequestException.class, () -> {
            LoginResponse loginResponse = loginService.login(loginRequest);
        });
    }
}
