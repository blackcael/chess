package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import model.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlGameDAO extends SqlBaseDAO implements GameDAO {
    public SqlGameDAO(Connection connection) {
        super(connection);
    }

    public void clear() throws DataAccessException {
        executeSingleLineSQL("TRUNCATE game");
    }

    private String gameToJson(ChessGame game) {
        return new Gson().toJson(game);
    }

    private ChessGame jsonToGame(String jsonString) {
        return new Gson().fromJson(jsonString, ChessGame.class);
    }

    public GameData getGame(Integer gameID) throws DataAccessException {
        String sql = "SELECT * FROM game WHERE gameID = \"" + gameID.toString() + "\"";
        GameData resultGame = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            resultGame = readGame(rs);
        } catch (SQLException ex) {
            throw new DataAccessException(ex.toString());
        }
        if(resultGame == null){
            throw new DataAccessException("gameID DNE in database");
        }
        return resultGame;
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int resultGameID = rs.getInt("gameID");
            var resultWhiteUsername = rs.getString("whiteUsername");
            var resultBlackUsername = rs.getString("blackUsername");
            var resultGameName = rs.getString("gameName");
            var resultGame = jsonToGame(rs.getString("gameJson"));
            return new GameData(resultGameID, resultWhiteUsername, resultBlackUsername, resultGameName, resultGame);
        }
        return null;
    }

    private record GameDataStrings(int ID, String whiteUsername, String blackUsername, String gameName, String game) { }
    public void createGame(GameData gameData) throws DataAccessException {
        GameDataStrings gameDataStrings = new GameDataStrings(
                gameData.gameID(),
                gameData.whiteUsername(),
                gameData.blackUsername(),
                gameData.gameName(),
               // gameToJson(gameData.game()));
                "gameJson");
        String sql = "INSERT INTO game values (?,?,?,?,?)";
        insertIntoTable(sql, gameDataStrings);
    }

    public ArrayList<ListGamesSubData> getGameList() throws DataAccessException {
        var retList = new ArrayList<ListGamesSubData>();
        var statement = "SELECT * FROM game";
        try (var preparedStatement = connection.prepareStatement(statement)) {
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    GameData tempGame = (readGame(rs));
                    retList.add(new ListGamesSubData(
                            tempGame.gameID(), tempGame.whiteUsername(), tempGame.blackUsername(), tempGame.gameName()));

                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.toString());
        }
        return retList;
    }


    public void updateGame(GameData updatedGameData) throws DataAccessException {
        String sql =
                "UPDATE game SET whiteUsername = \"" +  updatedGameData.whiteUsername()
                        + "\", blackUsername = \"" +  updatedGameData.blackUsername()
                        + "\", gameName = \"" +  updatedGameData.gameName()
                        + "\", gameJson = \"" +  gameToJson(updatedGameData.game())
                        + "\" WHERE gameID = " +  updatedGameData.gameID();
        executeSingleLineSQL(sql);
    }

    public boolean isEmpty() {
        return false;
    }
}
