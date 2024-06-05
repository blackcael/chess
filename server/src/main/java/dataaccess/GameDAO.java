package dataaccess;

import model.GameData;

import java.util.ArrayList;

public interface GameDAO {

    void clear() throws DataAccessException;

    GameData getGame(Integer gameID) throws DataAccessException;

    int createGame(GameData gameData) throws DataAccessException;

    ArrayList<ListGamesSubData> getGameList() throws DataAccessException;

    void updateGame(GameData updatedGameData) throws DataAccessException;

    boolean isEmpty();

    public static record ListGamesSubData(int gameID, String whiteUsername, String blackUsername, String gameName) {}
}