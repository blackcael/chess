package dataaccess;

import chess.ChessGame;
import model.*;
import org.junit.jupiter.api.AfterEach;
import service.ClearService;

public class TestBase {
    protected final Database database = new Database();

    protected final UserData sampleUser = new UserData("cblack5", "password", "mail@mail");
    protected final UserData unRegisteredUser = new UserData("unregisteredName", "unregpassword", "unreg@unreg.net");

    protected final AuthData sampleAuthData = new AuthData(sampleUser.username(), "superSecretAuthToken");
    protected final AuthData invalidAuthData = new AuthData("hackerman69", "hackingAttempt#1");

    protected final GameData sampleGameData = new GameData(1, "Dr. Phil", "Steve Harvey", "FPSChess2", new ChessGame());
    protected final GameData invalidGameData = new GameData(9, "tim", "tom", "invalidGame", new ChessGame());
    protected final GameData updatedGameData = new GameData(1, "newWhite", "newBlack", "updatedGameName", new ChessGame());
}

