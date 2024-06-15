package dataaccess;
import java.sql.*;
import java.util.*;

public class Database {
    public AuthDAO authDataBase;
    public GameDAO gameDataBase;
    public UserDAO userDataBase;
    private int gameIDCounter = 1;
    private Map<Integer, HashSet<String>> gameSessionMap;
    boolean dataBaseSimulation = false;
    public Database(){
        if(dataBaseSimulation){
            this.authDataBase = new MemoryAuthDAO();
            this.gameDataBase = new MemoryGameDAO();
            this.userDataBase = new MemoryUserDAO();
        }
        else{
            try{
                DatabaseManager.createDatabase();
                Connection connection = DatabaseManager.getConnection();
                this.authDataBase = new SqlAuthDAO(connection);
                this.gameDataBase = new SqlGameDAO(connection);
                this.userDataBase = new SqlUserDAO(connection);
            } catch (DataAccessException e){
                System.out.println("Error:" + e.toString());
            }
        }
    }
    public int getGameGenID(){
        return gameIDCounter++;
    }

    public boolean isEmpty(){
        return(authDataBase.isEmpty() && gameDataBase.isEmpty() && userDataBase.isEmpty());
    }

    public void connectPlayerToGameSession(String username, int gameID){
        if(gameSessionMap.containsKey(gameID)){
            HashSet<String> gameSession = gameSessionMap.get(gameID);
            gameSession.add(username);
        }else{
            gameSessionMap.put(gameID, new HashSet<String>(Collections.singletonList(username)));
        }
    }

    public void disconnectPlayerFromGameSession(String username, int gameID){
        gameSessionMap.get(gameID).remove(username);
    }

    public HashSet<String> getGameParticipants(int gameID){
        return gameSessionMap.get(gameID);
    }
}
