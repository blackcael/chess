package ui;

import chess.*;

public class GameplayUI extends BaseUI{

    public GameplayUI(ServerFacade serverFacade) {
        super(serverFacade);
    }

    public static void drawBoards(ChessBoard board){  //TODO : REMOVE : DEPRECATED
        BoardPrinter.drawBoard(board, ChessGame.TeamColor.WHITE, null, null);
        System.out.println(); //draws blank line
        BoardPrinter.drawBoard(board, ChessGame.TeamColor.BLACK, null, null);
    }

    public Client.UIStatusType help(){

        return Client.UIStatusType.GAMEPLAY;
    }

    public Client.UIStatusType redrawChessBoard(){

        return Client.UIStatusType.GAMEPLAY;
    }

    public Client.UIStatusType leave(){

        return Client.UIStatusType.POSTLOGIN;
    }

    public Client.UIStatusType makeMove(String[] params){

        return Client.UIStatusType.GAMEPLAY;
    }

    public Client.UIStatusType resign(){

        return Client.UIStatusType.GAMEPLAY;
    }

    public Client.UIStatusType highlightLegalMoves(){

        return Client.UIStatusType.GAMEPLAY;
    }






}
