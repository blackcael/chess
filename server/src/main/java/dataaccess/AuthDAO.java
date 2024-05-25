package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public interface AuthDAO {

    void clear();

    void createAuth(AuthData authData);

    AuthData getAuth(String authToken);

    void deleteAuth(String authToken);
}
