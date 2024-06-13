package websocket.commands;

public class ConnectCommand extends UserGameCommand{
    private final int gameID;
    public ConnectCommand(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        commandType = CommandType.CONNECT;
    }
    public int getGameID() {
        return gameID;
    }
}
