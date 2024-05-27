package intermediary;

import dataaccess.MemoryGameDAO;

import java.util.ArrayList;

public record ListGamesResponse(ArrayList<MemoryGameDAO.ListGamesSubData> gameList) {
}
