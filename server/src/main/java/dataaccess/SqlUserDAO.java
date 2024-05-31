package dataaccess;

import java.util.HashMap;
import java.util.Map;
import java.sql.*;

import model.UserData;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SqlUserDAO implements UserDAO {
    Connection connection;
    SqlUserDAO(Connection connection){
        this.connection = connection;
    }

    public void clear() throws DataAccessException {
        String sql = "TRUNCATE user";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
        }catch(SQLException e){
            throw new DataAccessException(e.toString());
        }
    }

    public void createUser(UserData user) throws DataAccessException {
        String sql = "INSERT INTO user values (?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.username());
            stmt.setString(2, user.password());
            stmt.setString(3, user.email());
        } catch(SQLException e){
            throw new DataAccessException(e.toString());
        }
    }

    public UserData getUser(String username) throws DataAccessException{
        String sql = "SELECT * FROM user WHERE username = " + username;
        UserData newUser = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql);  //TODO ADD A CONNECTION TO OUR DATABASE CLASS
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String newUserName = rs.getString(1);
                String newPassword = rs.getString(2);
                String newEmail = rs.getString(3);
                newUser = new UserData(newUserName, newPassword, newEmail);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("e");
        }
        return newUser;
    }

    public boolean isEmpty(){
        return false;
    }

}
