package dataaccess;

import java.util.HashMap;
import java.util.Map;

import model.UserData;

public class MemoryUserDAO implements UserDAO {
    private Map<String, UserData> userDataDB = new HashMap<>();

    public void clear(){
        userDataDB = new HashMap<>();
    }

    public void createUser(UserData user){
        userDataDB.put(user.username(), user);
    }

    public UserData getUser(String username){
        return userDataDB.get(username);
    }
}
