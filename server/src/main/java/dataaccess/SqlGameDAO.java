package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

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
        executeSingleLineSQL("TRUNCATE " + DatabaseManager.GAME_TABLE);
    }

    private String gameToJson(ChessGame game) {
        return new Gson().toJson(game);
    }

    private ChessGame jsonToGame(String jsonString) {
        return new Gson().fromJson(jsonString, ChessGame.class);
    }

    public GameData getGame(Integer gameID) throws DataAccessException {
        String sql = "SELECT * FROM " + DatabaseManager.GAME_TABLE +" WHERE gameID = \"" + gameID.toString() + "\"";
        GameData resultGame = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            rs.next();
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
        int resultGameID = rs.getInt("gameID");
        var resultWhiteUsername = rs.getString("whiteUsername");
        var resultBlackUsername = rs.getString("blackUsername");
        var resultGameName = rs.getString("gameName");
        var resultGame = jsonToGame(rs.getString("gameJson"));
        return new GameData(resultGameID, resultWhiteUsername, resultBlackUsername, resultGameName, resultGame);
    }

    private record GameDataStrings(String whiteUsername, String blackUsername, String gameName, String game) { }
    public int createGame(GameData gameData) throws DataAccessException {
        GameDataStrings gameDataStrings = new GameDataStrings(
                gameData.whiteUsername(),
                gameData.blackUsername(),
                gameData.gameName(),
                gameToJson(gameData.game()));
        String sql = "INSERT INTO " + DatabaseManager.GAME_TABLE + " (whiteUsername, blackUsername, gameName, gameJson) VALUES (?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ArrayList<String> componentList = recordToStringArray(gameDataStrings);
            for (int i = 0; i < componentList.size(); i++) {
                stmt.setString(i + 1, componentList.get(i));
            }
            stmt.executeUpdate();
            try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
                generatedKeys.next();
                int id = generatedKeys.getInt(1);
                return id;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.toString());
        }
    }

    public ArrayList<ListGamesSubData> getGameList() throws DataAccessException {
        var retList = new ArrayList<ListGamesSubData>();
        var statement = "SELECT * FROM " + DatabaseManager.GAME_TABLE;
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
        String sql = "UPDATE " + DatabaseManager.GAME_TABLE + " SET whiteUsername = ?, blackUsername = ?, gameName = ?, gameJson = ? WHERE gameID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, updatedGameData.whiteUsername());
            stmt.setString(2, updatedGameData.blackUsername());
            stmt.setString(3, updatedGameData.gameName());
            stmt.setString(4, gameToJson(updatedGameData.game()));
            stmt.setInt(5, updatedGameData.gameID());
            stmt.executeUpdate();
            } catch (SQLException e) {
                throw new DataAccessException(e.toString());
        }

    }


    public boolean isEmpty() {
        return false;
    }
}
