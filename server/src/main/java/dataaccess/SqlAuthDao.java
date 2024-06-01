package dataaccess;

import model.AuthData;

import java.sql.Connection;

public class SqlAuthDao extends SqlBaseDAO implements AuthDAO{
    public SqlAuthDao(Connection connection) {
        super(connection);
    }

    public void clear() throws DataAccessException {
        String sql = "TRUNCATE auth";
        executeSingleLineSQL(sql);
    }

    public void createAuth(AuthData authData) throws DataAccessException {
        String sql = "INSERT INTO user values (?,?,?)";
        insertIntoTable(sql, authData);
    }

    public AuthData getAuth(String authToken) {
        return null;
    }

    public void deleteAuth(String authToken) {

    }

    public boolean isEmpty() {
        return false;
    }
}
