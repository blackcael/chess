package ui;

import chess.*;

public class GameplayUI extends BaseUI{

    private ChessGame chessGame;

    public GameplayUI(ServerFacade serverFacade) {
        super(serverFacade);
    }

//

    public Client.UIStatusType help(){
        printHelpStatement("redrawChessBoard, <USERNAME> <PASSWORD> <EMAIL>", "redraws the chess board in the current game state");
        printHelpStatement("leave <USERNAME> <PASSWORD>", "leave the game");
        printHelpStatement("makeMove <START_ROW><START_COL> <END_ROW><END_COL> <PROMOTION_PIECE>", "makes a move from start to end, if no promotion, do not specify a promotion piece");
        printHelpStatement("resign", "resign to your opponent, ends the game");
        printHelpStatement("highlightLegalMoves <ROW><COL>", "highlights the legal moves given a board position");
        printHelpStatement("help", "list commands and their descriptions");
        return Client.UIStatusType.GAMEPLAY;
    }
    public void startWebsocketConnection(){};


    public Client.UIStatusType redrawChessBoard(){
        BoardPrinter.drawBoard(chessGame.getBoard(), serverFacade.getColor(), null, null);
        return Client.UIStatusType.GAMEPLAY;
    }

    public Client.UIStatusType leave(){
        //remove self from <COLOR>username
        return Client.UIStatusType.POSTLOGIN;
    }

    public Client.UIStatusType makeMove(String[] params){
        //send <StartPos> <EndPos>
        return Client.UIStatusType.GAMEPLAY;
    }

    public Client.UIStatusType resign(){
        //send resignation Notice
        return Client.UIStatusType.GAMEPLAY;
    }

    public Client.UIStatusType highlightLegalMoves(String[] params){
        ChessPosition currentPosition = coordsToPosition(params[0]);
        BoardPrinter.drawBoard(chessGame.getBoard(), serverFacade.getColor(), chessGame.validMoves(currentPosition), currentPosition);
        return Client.UIStatusType.GAMEPLAY;
    }

    private final String[] COLUMN_INDICES = {"a","b","c","d","e","f","g","h"};
    private final String[] ROW_INDICES  = {"1","2","3","4","5","6","7","8"};
    private ChessPosition coordsToPosition(String stringPos){
        int column = 0;
        int row = 0;
        for(int i = 0; i < 8; i ++){
            if(stringPos.contains(COLUMN_INDICES[i])){
                column = i + 1;
            }
            if(stringPos.contains(ROW_INDICES[i])){
                row = i+1;
            }
        }
        return new ChessPosition(row, column);
    }






}
