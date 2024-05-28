package service;

import dataaccess.Database;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClearTests {
    //setup
    Database testdatabase = new Database(new MemoryAuthDAO(), new MemoryGameDAO(), new MemoryUserDAO());

    @Test
    public void clearTest(){
        ClearService clearService = new ClearService(testdatabase);
        clearService.clear();
        assertTrue(testdatabase.isEmpty());
    }
}
