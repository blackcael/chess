package dataaccess;

import java.sql.*;
import model.UserData;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SqlUserDAO extends SqlBaseDAO implements UserDAO {
    public SqlUserDAO(Connection connection){
        super(connection);
    }

    public void clear() throws DataAccessException {
        String sql = "TRUNCATE user";
        executeSingleLineSQL(sql);
    }

    public void createUser(UserData user) throws DataAccessException {
        String sql = "INSERT INTO user values (?,?,?)";
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
        String sql = "SELECT * FROM user WHERE username = \"" + username + "\"";
        UserData resultUser = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql);  //TODO ADD A CONNECTION TO OUR DATABASE CLASS
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String resultUserName = rs.getString(1);
                String resultPassword = rs.getString(2);
                String resultEmail = rs.getString(3);
                resultUser = new UserData(resultUserName, resultPassword, resultEmail);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("e");
        }
        return resultUser;
    }

    public boolean isEmpty(){
        return false;
    }

}
