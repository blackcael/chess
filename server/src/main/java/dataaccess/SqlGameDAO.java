package dataaccess;

import chess.ChessGame;
import model.GameData;
import model.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlGameDAO extends SqlBaseDAO implements GameDAO {
    protected SqlGameDAO(Connection connection) {
        super(connection);
    }

    public void clear() throws DataAccessException {
        String sql = "TRUNCATE user";
        executeSingleLineSQL(sql);
    }

    record GameDataStrings(int ID, String whiteUsername, String blackUsername, String gameName, String game){}

    private String gametoJson(ChessGame game){
        //TODO implement
    }

    private ChessGame jsonToGame(String jsonString){
        //TODO implement
    }

    public GameData getGame(Integer gameID) throws DataAccessException {
        String sql = "SELECT * FROM game WHERE gameID = \"" + gameID.toString() + "\"";
        GameData resultGameData = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int resultGameID = rs.getInt(1);
                String resultWhiteUsername = rs.getString(2);
                String resultBlackUsername = rs.getString(3);
                String resultGameName = rs.getString(4);
                ChessGame resultGame = jsonToGame(rs.getString(5));
                resultGameData = new GameData(resultGameID, resultWhiteUsername, resultBlackUsername, resultGameName, resultGame);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("e");
        }
        return resultGameData;
    }

    public void createGame(GameData gameData) throws DataAccessException {
        GameDataStrings gameDataStrings = new GameDataStrings(
                gameData.gameID(),
                gameData.whiteUsername(),
                gameData.blackUsername(),
                gameData.gameName(),
                gametoJson(gameData.game()));
        String sql = "INSERT INTO auth values (?,?,?,?,?)";
        insertIntoTable(sql, gameDataStrings);
    }

    public ArrayList<ListGamesSubData> getGameList() {
        return null;
    }

    public void updateGame(GameData updatedGameData) throws DataAccessException {

    }

    public boolean isEmpty() {
        return false;
    }
}
