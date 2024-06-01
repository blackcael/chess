package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public interface AuthDAO {

    void clear() throws DataAccessException;

    void createAuth(AuthData authData) throws DataAccessException;

    AuthData getAuth(String authToken);

    void deleteAuth(String authToken);

    boolean isEmpty();
}
