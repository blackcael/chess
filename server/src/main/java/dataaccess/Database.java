package dataaccess;
import handler.WebSocketConnection;

import java.sql.*;
import java.util.*;

public class Database {
    public AuthDAO authDataBase;
    public GameDAO gameDataBase;
    public UserDAO userDataBase;
    private int gameIDCounter = 1;
    private Map<Integer, HashSet<WebSocketConnection>> gameSessionMap;
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

    public void connectPlayerToGameSession(WebSocketConnection connection, int gameID){
        if(gameSessionMap.containsKey(gameID)){
            HashSet<WebSocketConnection> gameSession = gameSessionMap.get(gameID);
            gameSession.add(connection);
        }else{
            gameSessionMap.put(gameID, new HashSet<>(Collections.singletonList(connection)));
        }
    }

    public void disconnectPlayerFromGameSession(WebSocketConnection connection, int gameID){
        gameSessionMap.get(gameID).remove(connection);
    }

    public HashSet<WebSocketConnection> getGameParticipants(int gameID){
        return gameSessionMap.get(gameID);
    }
}
