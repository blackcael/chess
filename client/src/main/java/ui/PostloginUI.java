package ui;

import dataaccess.GameDAO;
import dataaccess.MemoryGameDAO;
import intermediary.*;
import intermediary.ResponseCodeAndObject;
import chess.*;

import java.util.ArrayList;
import java.util.List;

public class PostloginUI extends BaseUI{
    ArrayList<GameDAO.ListGamesSubData> gameList = new ArrayList<>();
    public PostloginUI(ServerFacade serverFacade){
        super(serverFacade);
    };

    public Client.UIStatusType help(){
        printHelpStatement("create <NAME>", "creates a game");
        printHelpStatement("list", "lists all games");
        printHelpStatement("join <ID> [WHITE|BLACK]", "join a game");
        printHelpStatement("observe <ID>", "passively watch game");
        printHelpStatement("logout", "terminate session");
        printHelpStatement("quit", "terminates program");
        printHelpStatement("help", "list commands and their descriptions");
        return Client.UIStatusType.POSTLOGIN;
    }

    public Client.UIStatusType logout(){
        ResponseCodeAndObject response = serverFacade.logout();
        if(response.responseCode() == 200){
            System.out.println("Successfully Logged Out");
        }
        return Client.UIStatusType.PRELOGIN;
    }

    public Client.UIStatusType createGame(String[] params){
        ResponseCodeAndObject response = serverFacade.createGame(params);
        if(response.responseCode() == 200){
            CreateGameResponse createGameResponse = (CreateGameResponse) response.responseObject();
            System.out.println("Successfully Created Game " + params[0] + ", with ID: " + createGameResponse.gameID());
        }
        else{
            System.out.println(response.responseObject());
        }
        return Client.UIStatusType.POSTLOGIN;
    }

    public Client.UIStatusType listGames(){
        ResponseCodeAndObject response = serverFacade.listGames();
        if(response.responseCode() == 200){
            ListGamesResponse listGamesResponse = (ListGamesResponse) response.responseObject();
            System.out.println("Games List:");
            gameList = listGamesResponse.games();
            int gameListIndex = 1;
            for(MemoryGameDAO.ListGamesSubData gameSubData : gameList){
                String whiteUsername = "null";
                if (gameSubData.whiteUsername() != null){
                    whiteUsername = gameSubData.whiteUsername();
                }
                String blackUsername = "null";
                if (gameSubData.blackUsername() != null){
                    blackUsername = gameSubData.blackUsername();
                }
                System.out.print("  " + Integer.toString(gameListIndex++));
                System.out.print(".  GameID: " + gameSubData.gameID());
                System.out.print(", GameName:" + gameSubData.gameName());
                System.out.print(", WhiteUsername: " + whiteUsername);
                System.out.println(", BlackUsername: " + blackUsername);
            }
        }
        else{
            System.out.println(response.responseObject());
        }
        return Client.UIStatusType.POSTLOGIN;
    }

    public Client.UIStatusType joinGame(String[] params){
        try {
            ResponseCodeAndObject response = serverFacade.joinGame(paramsIndexToID(params));
            if(response.responseCode() == 200){
                System.out.println("Successfully joined game as " + params[0]);
                ChessGame chessGame = new ChessGame();
                GameplayUI.drawBoards(chessGame.getBoard());
                return Client.UIStatusType.GAMEPLAY;
            }
            else{
                System.out.println(response.responseObject());
                return Client.UIStatusType.POSTLOGIN;
            }
        } catch (InvalidIndexError e) {
            System.out.println(e.getMessage());
            return Client.UIStatusType.POSTLOGIN;
        }
    }

    public Client.UIStatusType observeGame(String[] params){
        //implement in Phase 6
        return Client.UIStatusType.POSTLOGIN;
    }

    private String[] paramsIndexToID(String[] inputParams) throws InvalidIndexError {
        int index = Integer.valueOf(inputParams[1]) - 1;
        if (index >= gameList.size()){
            throw new InvalidIndexError();
        }
        return new String[] {inputParams[0], Integer.toString(gameList.get(index).gameID())};
    }

}
