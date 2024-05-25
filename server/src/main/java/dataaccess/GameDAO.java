package dataaccess;

import model.GameData;

import java.util.ArrayList;
import java.util.HashMap;

public interface GameDAO {

    void clear();

    GameData getGame(Integer gameID);

    void createGame(GameData gameData) throws DataAccessException;

    ArrayList<GameData> getGameList();

    void updateGame(GameData updatedGameData) throws DataAccessException;
}
