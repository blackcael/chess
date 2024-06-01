package dataaccess;

import java.sql.Connection;

public class SqlBaseDAO {
    Connection connection;
    protected SqlBaseDAO(Connection connection){
        this.connection = connection;
    }

}
