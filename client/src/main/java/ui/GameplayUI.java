package ui;

import chess.*;

public class GameplayUI {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final String SET_LIGHT_COLOR = EscapeSequences.SET_BG_COLOR_LIGHT_GREY;
    private static final String SET_DARK_COLOR = EscapeSequences.SET_BG_COLOR_DARK_GREEN;
    private static final String SET_BORDER_COLOR = EscapeSequences.SET_BG_COLOR_DARK_GREY;

    public static void drawBoards(ChessBoard board){
        BoardPrinter.drawBoard(board, ChessGame.TeamColor.WHITE, null, null);
        System.out.println(); //draws blank line
        BoardPrinter.drawBoard(board, ChessGame.TeamColor.BLACK, null, null);
    }





}
