package dataaccess;

import java.util.HashMap;
import java.util.Map;
import java.sql.*;

import model.UserData;

public class SqlUserDAO implements UserDAO {
    Connection connection;
    SqlUserDAO(Connection connection){
        this.connection = connection;
    }

    public void clear(){

    }

    public void createUser(UserData user){

    }

    public UserData getUser(String username) throws Exception{
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

    }
}
