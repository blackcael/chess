package service;

import dataaccess.Database;
import intermediary.BadRequestException;
import intermediary.InvalidAuthException;
import intermediary.JoinGameRequest;
import model.GameData;

public class JoinGameService extends BaseService{
    public JoinGameService(Database database) {
        super(database);
    }

    public void joinGame(String authToken, JoinGameRequest joinGameRequest) throws Exception {
        validateAuthToken(authToken);
        GameData gameData = gameDataBase.getGame(joinGameRequest.gameID());
        if(gameData == null){
            throw new BadRequestException("Game does not exist!");
        }
        String playerColor = joinGameRequest.playerColor();
        if(playerColor.equals("WHITE")){
            joinAsWhite(authToken, gameData);
        } else if (playerColor.equals("BLACK")){
            joinAsBlack(authToken, gameData);
        } else{
            throw new BadRequestException("Error: bad request");
        }
    }

    private void joinAsWhite(String authToken, GameData gameData) throws Exception{
        if(gameData.whiteUsername() != null){
            throw new BadRequestException("Error: already taken");
        }
        GameData newGameData = new GameData(gameData.gameID(), authDataBase.getAuth(authToken).username(), gameData.blackUsername(), gameData.gameName(), gameData.game());
        gameDataBase.updateGame(newGameData);
    }

    private void joinAsBlack(String authToken, GameData gameData) throws Exception{
        if(gameData.blackUsername() != null){
            throw new BadRequestException("Error: already taken");
        }
        GameData newGameData = new GameData(gameData.gameID(), gameData.whiteUsername(),authDataBase.getAuth(authToken).username(), gameData.gameName(), gameData.game());
        gameDataBase.updateGame(newGameData);
    }
}
