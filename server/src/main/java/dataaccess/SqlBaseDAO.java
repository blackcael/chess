package dataaccess;

import model.UserData;

import javax.lang.model.type.ArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import java.lang.reflect.Type;
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
        try (PreparedStatement stmt = connection.prepareStatement(sql);){
            ArrayList<RecordComponent> componentList = new ArrayList<>(List.of(record.getClass().getRecordComponents()));
            for(int i = 0; i < componentList.size(); i++) {
                var accessor = componentList.get(i).getAccessor();
                String value = (String) accessor.invoke(record);
                stmt.setString(i+1, value);
            }
            stmt.executeUpdate();
        } catch(SQLException e){
            throw new DataAccessException(e.toString());
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
