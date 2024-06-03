package dataaccess;

import model.AuthData;
import model.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlAuthDAO extends SqlBaseDAO implements AuthDAO{
    public SqlAuthDAO(Connection connection) {
        super(connection);
    }

    public void clear() throws DataAccessException {
        executeSingleLineSQL("TRUNCATE auth");
    }

    public void createAuth(AuthData authData) throws DataAccessException {
        String sql = "INSERT INTO auth values (?,?)";
        insertIntoTable(sql, authData);
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        String sql = "SELECT * FROM auth WHERE authKey = \"" + authToken + "\"";
        AuthData resultAuth = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql);  //TODO ADD A CONNECTION TO OUR DATABASE CLASS
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String resultUserName = rs.getString(1);
                String resultAuthToken = rs.getString(2);
                resultAuth = new AuthData(resultUserName, resultAuthToken);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("e");
        }
        if(resultAuth == null){
            throw new DataAccessException("500, authToken DNE in database");
        }
        return resultAuth;
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        String sql = "DELETE FROM auth WHERE authKey = \"" + authToken + "\"";
        executeSingleLineSQL(sql);
    }

    public boolean isEmpty() {
        return false;
    }
}
