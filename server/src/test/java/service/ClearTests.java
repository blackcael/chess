package service;

import dataaccess.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClearTests {
    //setup
    Database testdatabase = new Database();

    @Test
    public void clearTest() throws DataAccessException {
        ClearService clearService = new ClearService(testdatabase);
        clearService.clear();
        assertTrue(testdatabase.isEmpty());
    }
}
