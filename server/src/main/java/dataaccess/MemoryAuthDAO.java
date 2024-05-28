package dataaccess;
import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO implements AuthDAO {
    //CRUD operations
    private Map<String, AuthData> authDataDB = new HashMap<>();

    public void clear(){
        authDataDB = new HashMap<>();
    }

    public void createAuth(AuthData authData){
        authDataDB.put(authData.authToken(), authData);
    }

    public AuthData getAuth(String authToken){
        return authDataDB.get(authToken);
    }

    public void deleteAuth(String authToken){
        authDataDB.remove(authToken);
    }

    public boolean isEmpty(){
        return authDataDB.isEmpty();
    }

}
