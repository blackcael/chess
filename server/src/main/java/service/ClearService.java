package service;

import dataaccess.DataAccessException;
import dataaccess.Database;

public class ClearService extends BaseService{
    public ClearService(Database database) {
        super(database);
    }
    public void clear() throws DataAccessException {
        authDataBase.clear();
        gameDataBase.clear();
        userDataBase.clear();
    }


}
