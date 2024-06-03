package service;
import dataaccess.*;
import intermediary.AlreadyTakenException;
import intermediary.RegisterRequest;
import intermediary.RegisterResponse;
import model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTests {
    Database database = new Database();
    RegisterService registerService = new RegisterService(database);
    RegisterRequest newUserRegisterRequest = new RegisterRequest("cblack1", "p@ssw0rd", "cblack1@byu.edu");
    UserData newUserData = new UserData("cblack1", "p@ssw0rd", "cblack1@byu.edu");

    public RegisterTests() throws DataAccessException {
    }
    @AfterEach
    public void clearAll() throws Exception{
        ClearService clearService = new ClearService(database);
        clearService.clear();
    }

    @Test
    public void registerNewUserTest() throws Exception {
        RegisterResponse registerResponse = registerService.register(newUserRegisterRequest);
        assertEquals(database.userDataBase.getUser("cblack1"), newUserData);
    }

    @Test
    public void generateAuthData() throws Exception {
        RegisterResponse registerResponse = registerService.register(newUserRegisterRequest);
        assertEquals(registerResponse.username(), newUserRegisterRequest.username());
        System.out.println(registerResponse.authToken());
    }

    @Test
    public void registerDuplicateUserNameTest() throws Exception {
        RegisterResponse registerResponse = registerService.register(newUserRegisterRequest);
        assertThrows(AlreadyTakenException.class, () -> {
            RegisterResponse duplicateRegisterResponse = registerService.register(newUserRegisterRequest);
        });
    }
}
