package dataaccess;

import java.sql.*;
import model.UserData;

import javax.xml.crypto.Data;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SqlUserDAO extends SqlBaseDAO implements UserDAO {
    public SqlUserDAO(Connection connection){
        super(connection);
    }

    public void clear() throws DataAccessException {
        executeSingleLineSQL("TRUNCATE " + DatabaseManager.USER_TABLE);
    }

    public void createUser(UserData user) throws DataAccessException {
        String sql = "INSERT INTO " + DatabaseManager.USER_TABLE + " VALUES (?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql);){
            stmt.setString(1, user.username());
            stmt.setString(2, user.password());
            stmt.setString(3, user.email());
            stmt.executeUpdate();
        } catch(SQLException e){
            throw new DataAccessException(e.toString());
        }
    }

    public UserData getUser(String username) throws DataAccessException{
        String sql = "SELECT * FROM " + DatabaseManager.USER_TABLE + " WHERE username = \"" + username + "\"";
        UserData resultUser = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String resultUserName = rs.getString(1);
                String resultPassword = rs.getString(2);
                String resultEmail = rs.getString(3);
                resultUser = new UserData(resultUserName, resultPassword, resultEmail);
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex.toString());
        }
        return resultUser;
    }

    public boolean isEmpty(){
        return isEmptyInputTableName(DatabaseManager.USER_TABLE);
    }

}
