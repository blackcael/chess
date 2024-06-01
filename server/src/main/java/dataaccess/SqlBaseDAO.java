package dataaccess;

import model.UserData;

import javax.lang.model.type.ArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import java.lang.reflect.Type;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    protected void insertIntoTable(String sql, Object record) throws DataAccessException {
        try (PreparedStatement stmt = connection.prepareStatement(sql);) {
            ArrayList<String> componentList = recordToStringArray(record);
            for (int i = 0; i < componentList.size(); i++) {
                stmt.setString(i + 1, componentList.get(i));
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.toString());
        }
    }

    private ArrayList<String> recordToStringArray(Object record) throws DataAccessException {
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

}
