package service;
import dataaccess.*;
import intermediary.AlreadyTakenException;
import intermediary.RegisterRequest;
import intermediary.RegisterResponse;
import model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Provider;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTests extends ServiceTestBase {

    public RegisterTests() throws DataAccessException {
    }


    @BeforeEach @AfterEach
    public void clearAll() throws Exception{
        ClearService clearService = new ClearService(database);
        clearService.clear();
    }

    @Test
    public void registerNewUserTest() throws Exception {
        RegisterResponse registerResponse = registerService.register(newUserRegisterRequest);
        assertEquals(database.userDataBase.getUser("cblack1").username(), newUserData.username());
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
