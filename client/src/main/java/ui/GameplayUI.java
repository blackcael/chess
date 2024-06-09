package ui;

import chess.*;

public class GameplayUI {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    private static final String SET_LIGHT_COLOR = EscapeSequences.SET_BG_COLOR_LIGHT_GREY;
    private static final String SET_DARK_COLOR = EscapeSequences.SET_BG_COLOR_DARK_GREEN;

    public static void drawBoards(ChessBoard board){
        drawSingleBoard(board);
    }

    private static void drawWhitePerspectiveBoard(ChessBoard board){
        for(int rowIter = 0; rowIter < BOARD_SIZE_IN_SQUARES; rowIter ++){
            for(int colIter = 0; colIter < BOARD_SIZE_IN_SQUARES; colIter ++){
                ChessPosition position = new ChessPosition(rowIter + 1, colIter + 1);
                drawBoardSquare(getSquareColor(rowIter, colIter), board.getPiece(position));
            }
            System.out.println(EscapeSequences.RESET_BG_COLOR);
        }
    }

    private static void drawBlackPerspectiveBoard(ChessBoard board){
        for(int rowIter = 0; rowIter < BOARD_SIZE_IN_SQUARES; rowIter ++){
            for(int colIter = 0; colIter < BOARD_SIZE_IN_SQUARES; colIter ++){
                ChessPosition position = new ChessPosition(rowIter + 1, colIter + 1);
                drawBoardSquare(getSquareColor(rowIter, colIter), board.getPiece(position));
            }
            System.out.println(EscapeSequences.RESET_BG_COLOR);
        }
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

    private static void drawBorder(){

    }



}
