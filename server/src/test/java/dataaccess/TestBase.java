package dataaccess;

import chess.ChessGame;
import model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestBase {
    protected final Database database = new Database();

    protected final UserData sampleUser = new UserData("cblack5", "password", "mail@mail");
    protected final UserData unregisteredUser = new UserData("unregisteredName", "unregpassword", "unreg@unreg.net");

    protected final AuthData sampleAuthData = new AuthData(sampleUser.username(), "superSecretAuthToken");
    protected final AuthData invalidAuthData = new AuthData("hackerman69", "hackingAttempt#1");

    protected final GameData sampleGameData = new GameData(99, "Dr. Phil", "Steve Harvey", "FPSChess1", new ChessGame());
    protected final GameData sampleGameData2 = new GameData(99, "Dr. Phil", "Steve Harvey", "FPSChess2", new ChessGame());
    protected final GameData sampleGameData3 = new GameData(99, "Dr. Phil", "Steve Harvey", "FPSChess3", new ChessGame());

    protected final GameData invalidGameData = new GameData(9, "tim", "tom", "invalidGame", new ChessGame());
    protected final GameData updatedGameData = new GameData(1, "newWhite", "newBlack", "updatedGameName", new ChessGame());

    protected final GameDAO.ListGamesSubData lgsd1 = new GameDAO.ListGamesSubData(sampleGameData.gameID(), sampleGameData.whiteUsername(), sampleGameData.blackUsername(), sampleGameData.gameName());
    protected final GameDAO.ListGamesSubData lgsd2 = new GameDAO.ListGamesSubData(sampleGameData2.gameID(), sampleGameData2.whiteUsername(), sampleGameData2.blackUsername(), sampleGameData2.gameName());
    protected final GameDAO.ListGamesSubData lgsd3 = new GameDAO.ListGamesSubData(sampleGameData3.gameID(), sampleGameData3.whiteUsername(), sampleGameData3.blackUsername(), sampleGameData3.gameName());

    protected ArrayList<GameDAO.ListGamesSubData> gameList = new ArrayList<>();
}

