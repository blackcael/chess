package intermediary;

import dataaccess.MemoryGameDAO; //note of caution: circular dependency?

import java.util.ArrayList;

public record ListGamesResponse(ArrayList<MemoryGameDAO.ListGamesSubData> games) {
}
