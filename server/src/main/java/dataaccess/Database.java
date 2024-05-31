package dataaccess;
import java.sql.*;

public class Database {
    public AuthDAO authDataBase;
    public GameDAO gameDataBase;
    public UserDAO userDataBase;
    private int gameIDCounter = 1;
    boolean dataBaseSimulation = false;
    public Database() throws DataAccessException {
        if(dataBaseSimulation){
            this.authDataBase = new MemoryAuthDAO();
            this.gameDataBase = new MemoryGameDAO();
            this.userDataBase = new MemoryUserDAO();
        }
        else{
            Connection connection = DatabaseManager.getConnection();
            this.authDataBase = new SqlAuthDAO(connection);
            this.gameDataBase = new SqlGameDAO(connection);
            this.userDataBase = new SqlUserDAO(connection);
        }


    }
    public int getGameGenID(){
        return gameIDCounter++;
    }

    public boolean isEmpty(){
        return(authDataBase.isEmpty() && gameDataBase.isEmpty() && userDataBase.isEmpty());
    }
}
