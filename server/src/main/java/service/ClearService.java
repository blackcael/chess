package service;

import dataaccess.Database;

public class ClearService extends BaseService{
    public ClearService(Database database) {
        super(database);
    }
    public void clear(){
        authDataBase.clear();
        gameDataBase.clear();
        userDataBase.clear();
    }


}
