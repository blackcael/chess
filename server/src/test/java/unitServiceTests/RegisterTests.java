package unitServiceTests;
import dataaccess.*;
import intermediary.RegisterRequest;
import intermediary.RegisterResponse;
import model.UserData;
import org.junit.jupiter.api.Test;
import service.RegisterService;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTests {
    Database database = new Database(new MemoryAuthDAO(), new MemoryGameDAO(), new MemoryUserDAO());
    RegisterService registerService = new RegisterService(database);
    RegisterRequest newUserRegisterRequest = new RegisterRequest("turboSHID", "p@ssw0rd", "cblack1@byu.edu");
    UserData newUserData = new UserData("turboSHID", "p@ssw0rd", "cblack1@byu.edu");

    @Test
    public void registerNewUserTest() throws DataAccessException {
        RegisterResponse registerResponse = registerService.register(newUserRegisterRequest);
        assertEquals(database.userDataBase.getUser("turboSHID"), newUserData);
    }

    @Test
    public void generateAuthData() throws DataAccessException {
        RegisterResponse registerResponse = registerService.register(newUserRegisterRequest);
        assertEquals(registerResponse.username(), newUserRegisterRequest.username());
        System.out.println(registerResponse.authToken());
    }


    @Test
    public void registerDuplicateUserNameTest() throws DataAccessException {
        RegisterResponse registerResponse = registerService.register(newUserRegisterRequest);
        assertThrows(DataAccessException.class, () -> {RegisterResponse duplicateRegisterResponse = registerService.register(newUserRegisterRequest);});
    }
}
