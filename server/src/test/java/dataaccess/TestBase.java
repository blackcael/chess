package dataaccess;

import model.AuthData;
import model.UserData;

public class TestBase {
    Database database = new Database();
    UserData sampleUser = new UserData("cblack5", "password", "mail@mail");
    AuthData sampleAuthData = new AuthData(sampleUser.username(), "GreatGooglyMoogly");
}
