package dataaccess;

import model.GameData;

import java.util.ArrayList;
import java.util.HashMap;

public interface GameDAO {

    void clear() throws DataAccessException;

    GameData getGame(Integer gameID) throws DataAccessException;

    void createGame(GameData gameData) throws DataAccessException;

    ArrayList<ListGamesSubData> getGameList() throws DataAccessException;

    void updateGame(GameData updatedGameData) throws DataAccessException;

    boolean isEmpty();

    public static record ListGamesSubData(int gameID, String whiteUsername, String blackUsername, String gameName) {}
}