package unitServiceTests;

import dataaccess.Database;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import org.junit.jupiter.api.Test;
import service.ClearService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClearTests {
    //setup
    Database testdatabase = new Database(new MemoryAuthDAO(), new MemoryGameDAO(), new MemoryUserDAO());
    Database defEmptydatabase = new Database(new MemoryAuthDAO(), new MemoryGameDAO(), new MemoryUserDAO());


    @Test
    public void clearTest(){
        ClearService clearService = new ClearService(testdatabase);
        clearService.clear();
        assertEquals(testdatabase, defEmptydatabase);
    }
}
