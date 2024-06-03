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
        executeSingleLineSQL("TRUNCATE user");
    }

    private String gameToJson(ChessGame game) {
        return new Gson().toJson(game);
    }

    private ChessGame jsonToGame(String jsonString) {
        return new Gson().fromJson(jsonString, ChessGame.class);
    }

    public GameData getGame(Integer gameID) throws DataAccessException {
        String sql = "SELECT * FROM game WHERE gameID = \"" + gameID.toString() + "\"";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return readGame(rs);
        } catch (SQLException ex) {
            throw new DataAccessException("error is getGame");
        }
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        int resultGameID = rs.getInt(1);
        String resultWhiteUsername = rs.getString(2);
        String resultBlackUsername = rs.getString(3);
        String resultGameName = rs.getString(4);
        ChessGame resultGame = jsonToGame(rs.getString(5));
        return new GameData(resultGameID, resultWhiteUsername, resultBlackUsername, resultGameName, resultGame);
    }

    private record GameDataStrings(int ID, String whiteUsername, String blackUsername, String gameName, String game) { }
    public void createGame(GameData gameData) throws DataAccessException {
        GameDataStrings gameDataStrings = new GameDataStrings(
                gameData.gameID(),
                gameData.whiteUsername(),
                gameData.blackUsername(),
                gameData.gameName(),
                gameToJson(gameData.game()));
        String sql = "INSERT INTO auth values (?,?,?,?,?)";
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

    }

    public boolean isEmpty() {
        return false;
    }
}
