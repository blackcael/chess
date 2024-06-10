package ui;

import chess.*;

public class GameplayUI {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final String SET_LIGHT_COLOR = EscapeSequences.SET_BG_COLOR_LIGHT_GREY;
    private static final String SET_DARK_COLOR = EscapeSequences.SET_BG_COLOR_DARK_GREEN;
    private static final String SET_BORDER_COLOR = EscapeSequences.SET_BG_COLOR_DARK_GREY;

    public static void drawBoards(ChessBoard board){
        drawWhitePerspectiveBoard(board);
        System.out.println(); //draws blank line
        drawBlackPerspectiveBoard(board);
    }

    private static void drawWhitePerspectiveBoard(ChessBoard board){
        drawColumnIndices();
        for(int rowIter = 0; rowIter < BOARD_SIZE_IN_SQUARES; rowIter ++){
            drawBorderRowIndex(Integer.toString(rowIter));
            for(int colIter = 0; colIter < BOARD_SIZE_IN_SQUARES; colIter ++){
                ChessPosition position = new ChessPosition(rowIter + 1, colIter + 1);
                drawBoardSquare(getSquareColor(rowIter, colIter), board.getPiece(position));
            }
            drawBorderRowIndex(Integer.toString(rowIter));

            System.out.println(EscapeSequences.RESET_BG_COLOR);
        }
        drawColumnIndices();
    }

    private static void drawBlackPerspectiveBoard(ChessBoard board){
        drawReverseColumnIndices();
        for(int rowIter = BOARD_SIZE_IN_SQUARES-1; rowIter >= 0; rowIter --){
            drawBorderRowIndex(Integer.toString(rowIter));
            for(int colIter = BOARD_SIZE_IN_SQUARES-1; colIter >= 0; colIter --){
                ChessPosition position = new ChessPosition(rowIter + 1, colIter + 1);
                drawBoardSquare(getSquareColor(rowIter, colIter), board.getPiece(position));
            }
            drawBorderRowIndex(Integer.toString(rowIter));
            System.out.println(EscapeSequences.RESET_BG_COLOR);
        }
        drawReverseColumnIndices();
    }

    private static String getSquareColor(int row, int col){
        int sum = row + col;
        if(sum % 2 == 0){
            return SET_LIGHT_COLOR;
        }else{
            return SET_DARK_COLOR;
        }
    }

    private static void drawBoardSquare(String squareColor, ChessPiece piece) {
        System.out.print(squareColor + getPieceString(piece));
    }

    private static String getPieceString(ChessPiece piece) {
        if(piece == null){
            return EscapeSequences.EMPTY;
        }
        if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
            return switch(piece.getPieceType()){
                case KING -> EscapeSequences.WHITE_KING;
                case QUEEN -> EscapeSequences.WHITE_QUEEN;
                case BISHOP -> EscapeSequences.WHITE_BISHOP;
                case KNIGHT -> EscapeSequences.WHITE_KNIGHT;
                case ROOK -> EscapeSequences.WHITE_ROOK;
                case PAWN -> EscapeSequences.WHITE_PAWN;
            };
        }
        if(piece.getTeamColor() == ChessGame.TeamColor.BLACK){
            return switch(piece.getPieceType()){
                case KING -> EscapeSequences.BLACK_KING;
                case QUEEN -> EscapeSequences.BLACK_QUEEN;
                case BISHOP -> EscapeSequences.BLACK_BISHOP;
                case KNIGHT -> EscapeSequences.BLACK_KNIGHT;
                case ROOK -> EscapeSequences.BLACK_ROOK;
                case PAWN -> EscapeSequences.BLACK_PAWN;
            };
        }
        return null;
    }

    private final static String[] COLUMN_INDEX_STRINGS=
            {"    a", "   b", "   c", "  d ", "  e", "  f", "   g", "   h    "};
    private final static String[] REVERSE_COLUMN_INDEX_STRINGS =
            {"    h", "   g", "   f", "  e ", "  d", "  c", "   b", "   a    "};

    private static void drawColumnIndices(){

        for(int i = 0; i < 8; i++){
            drawBorderSquare(COLUMN_INDEX_STRINGS[i]);
        }
        System.out.println(EscapeSequences.RESET_BG_COLOR);
    }

    private static void drawReverseColumnIndices(){

        for(int i = 0; i < 8; i++){
            drawBorderSquare(REVERSE_COLUMN_INDEX_STRINGS[i]);
        }
        System.out.println(EscapeSequences.RESET_BG_COLOR);
    }

    private static void drawBorderSquare(String singleChar){
        if (singleChar.equals(EscapeSequences.EMPTY)){
            System.out.print(SET_BORDER_COLOR + singleChar);
        }else{
            System.out.print(SET_BORDER_COLOR + singleChar);
        }
    }

    private static void drawBorderRowIndex(String singleChar){
        drawBorderSquare(" " + singleChar + " ");
    }



}
