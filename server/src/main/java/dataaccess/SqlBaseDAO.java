package dataaccess;

import model.UserData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlBaseDAO {
    Connection connection;
    protected SqlBaseDAO(Connection connection){
        this.connection = connection;
    }

    protected void executeSingleLineSQL(String sql) throws DataAccessException {
        try (PreparedStatement stmt = connection.prepareStatement(sql);){
            stmt.executeUpdate();
        }catch(SQLException e){
            throw new DataAccessException(e.toString());
        }
    }

    protected void executeSQLStatement(String sql, Object record) throws DataAccessException {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ArrayList<String> componentList = recordToStringArray(record);
            for (int i = 0; i < componentList.size(); i++) {
                stmt.setString(i + 1, componentList.get(i));
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.toString());
        }
    }

    protected ArrayList<String> recordToStringArray(Object record) throws DataAccessException {
        ArrayList<RecordComponent> componentList = new ArrayList<>(List.of(record.getClass().getRecordComponents()));
        ArrayList<String> returnList = new ArrayList<>();
        for (RecordComponent recordComponent : componentList) {
            var accessor = recordComponent.getAccessor();
            try {
                Object value = accessor.invoke(record);
                returnList.add(value != null ? value.toString() : "null");
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new DataAccessException(e.toString());
            }
        }
        return returnList;
    }

    protected boolean isEmptyInputTableName(String tableName){
        String sql = "SELECT COUNT(*) AS row_count FROM " + tableName;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            rs.next();
            int rowCount = rs.getInt("row_count");
            return(rowCount == 0);
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
