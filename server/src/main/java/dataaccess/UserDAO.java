package dataaccess;

import model.UserData;

import java.util.HashMap;
import java.util.Map;

public interface UserDAO {
    Map<String, Object> dataBase = new HashMap<>();

    void clear();

    void createUser(UserData user);

    UserData getUser(String username);
}
