package dataaccess;
import model.GameData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDAO{
    //CRUD operations
    private Map<Integer, GameData> gameDataDB = new HashMap<>();
    public MemoryGameDAO(){}

    public void clear(){
        gameDataDB = new HashMap<>();
    }

    public GameData getGame(Integer gameID){
        return gameDataDB.get(gameID);
    }

    public void createGame(GameData gameData){
        gameDataDB.put(gameData.gameID(), gameData);
    }

    public ArrayList<GameData> getGameList(){
        return new ArrayList<>(gameDataDB.values());
    }

    public void updateGame(GameData updatedGameData) throws DataAccessException {
        //the best strategy may be to update every aspect of the game, even if it is redundant/changes nothing
        if (gameDataDB.replace(updatedGameData.gameID(), updatedGameData) == null) {
            throw new DataAccessException("Cannot Update Non-Existent Game");
        }
    }
}
