package dataaccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DatabaseManager {
    private static final String DATABASE_NAME;
    private static final String USER;
    private static final String PASSWORD;
    private static final String CONNECTION_URL;
    static final String GAME_TABLE = "game";
    static final String GAME_ID = "gameID";
    static final String GAME_NAME = "gameName";
    static final String WHITE_USERNAME = "whiteUsername";
    static final String BLACK_USERNAME = "blackUsername";
    static final String GAME_JSON = "gameJson";
    static final String USER_TABLE = "user";
    static final String USERNAME = "username";
    static final String USER_PASSWORD = "password";
    static final String AUTH_TOKEN = "authToken";
    static final String AUTH_TABLE = "auth";

    /*
     * Load the database information for the db.properties file.
     */
    static {
        try {
            try (var propStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
                if (propStream == null) throw new Exception("Unable to load db.properties");
                Properties props = new Properties();
                props.load(propStream);
                DATABASE_NAME = props.getProperty("db.name");
                USER = props.getProperty("db.user");
                PASSWORD = props.getProperty("db.password");

                var host = props.getProperty("db.host");
                var port = Integer.parseInt(props.getProperty("db.port"));
                CONNECTION_URL = String.format("jdbc:mysql://%s:%d", host, port);
            }
        } catch (Exception ex) {
            throw new RuntimeException("unable to process db.properties. " + ex.getMessage());
        }
    }

    /**
     * Creates the database if it does not already exist.
     */
    static void createDatabase() throws DataAccessException {
        try {
            var statement = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
            var conn = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
                createTables(conn);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }


    static private final String USER_TABLE_CREATE_STATEMENTS =
        "CREATE TABLE IF NOT EXISTS "+
                DatabaseManager.USER_TABLE +
                " (" +
                "`username` VARCHAR(256),"+
                "`password` VARCHAR(256)," +
                "`email` VARCHAR(256)," +
                "INDEX (username)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";

    static private final String AUTH_TABLE_CREATE_STATEMENTS =
            "CREATE TABLE IF NOT EXISTS "+
                    DatabaseManager.AUTH_TABLE +
                    " (" +
                    "`username` VARCHAR(256),"+
                    "`authToken` VARCHAR(256)," +
                    "INDEX (authToken)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci" ;


    static private final String GAME_TABLE_CREATE_STATEMENTS =
            "CREATE TABLE IF NOT EXISTS " +
                    DatabaseManager.GAME_TABLE +
                    " (" +
                    "`whiteUsername` VARCHAR(256),"+
                    "`blackUsername` VARCHAR(256),"+
                    "`gameName` VARCHAR(256),"+
                    "`gameJson` LONGTEXT DEFAULT NULL,"+
                    "`gameID` INT NOT NULL AUTO_INCREMENT,"+
                    "PRIMARY KEY (`gameID`)"+
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci"
    ;

    static void createTable(Connection connection, String createStatement){
            try (var preparedStatement = connection.prepareStatement(createStatement)) {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("FAILURE TO CREATE TABLE: " + e.toString());
            }
    }

    static void createTables(Connection connection){
        try (var preparedStatement = connection.prepareStatement("USE " + DatabaseManager.DATABASE_NAME)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        createTable(connection, USER_TABLE_CREATE_STATEMENTS);
        createTable(connection, GAME_TABLE_CREATE_STATEMENTS);
        createTable(connection, AUTH_TABLE_CREATE_STATEMENTS);
    }

        /**
     * Create a connection to the database and sets the catalog based upon the
     * properties specified in db.properties. Connections to the database should
     * be short-lived, and you must close the connection when you are done with it.
     * The easiest way to do that is with a try-with-resource block.
     * <br/>
     * <code>
     * try (var conn = DbInfo.getConnection(databaseName)) {
     * // execute SQL statements.
     * }
     * </code>
     */
    static Connection getConnection() throws DataAccessException {
        try {
            var conn = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            conn.setCatalog(DATABASE_NAME);
            return conn;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
