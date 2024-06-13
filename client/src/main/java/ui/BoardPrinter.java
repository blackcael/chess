package ui;

import chess.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;

public class BoardPrinter {
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final String SET_LIGHT_COLOR = EscapeSequences.SET_BG_COLOR_LIGHT_GREY;
    private static final String SET_DARK_COLOR = EscapeSequences.SET_BG_COLOR_DARK_GREEN;
    private static final String SET_LIGHT_HIGHLIGHT_COLOR = EscapeSequences.SET_BG_COLOR_YELLOW;
    private static final String SET_DARK_HIGHLIGHT_COLOR = EscapeSequences.SET_BG_COLOR_YELLOW;
    private static final String SET_CURRENT_SQUARE_COLOR = EscapeSequences.SET_BG_COLOR_BLUE;

    private static final String SET_BORDER_COLOR = EscapeSequences.SET_BG_COLOR_DARK_GREY;

    public static void drawBoards(ChessBoard board){
        BoardPrinter.drawBoard(board, ChessGame.TeamColor.WHITE, null, null);
        System.out.println(); //draws blank line
        BoardPrinter.drawBoard(board, ChessGame.TeamColor.BLACK, null, null);
    }


    public static void drawBoard(ChessBoard board, ChessGame.TeamColor color, Collection<ChessMove> potentialMoves, ChessPosition currentPosition){
        SquareType[][] squareTypeMatrix = generateSquareTypeMatrix(potentialMoves, currentPosition); //TODO null-handling?
        int startIndex = (color.equals(ChessGame.TeamColor.WHITE))? 0 : BOARD_SIZE_IN_SQUARES-1;
        drawColumnIndices(color);

        for(int rowIter = startIndex; endCondition(color, rowIter); rowIter = increment(color, rowIter)){
            drawBorderRowIndex(Integer.toString(rowIter));
            for(int colIter = startIndex; endCondition(color, colIter); colIter = increment(color, colIter)){
                ChessPosition position = new ChessPosition(rowIter + 1, colIter + 1);
                drawBoardSquare(getSquareColor(rowIter, colIter, squareTypeMatrix), board.getPiece(position));
            }
            drawBorderRowIndex(Integer.toString(rowIter));
            System.out.println(EscapeSequences.RESET_BG_COLOR);
        }
        drawColumnIndices(color);
    }

    private static boolean endCondition(ChessGame.TeamColor color, int iter){
        if (color == ChessGame.TeamColor.WHITE){
            return (iter < BOARD_SIZE_IN_SQUARES);
        }
        return (iter >= 0);
    }

    private static int increment(ChessGame.TeamColor color, int iter){
        if (color == ChessGame.TeamColor.WHITE){
            return iter + 1;
        }
        return iter -1;
    }

    private enum SquareType{
        INVALID_MOVE,
        VALID_MOVE,
        CURRENT_POSITION;
    };

    private static SquareType[][] generateSquareTypeMatrix(Collection<ChessMove> potentialMoves, ChessPosition currentPosition){
        SquareType[][] squareTypes = new SquareType[BOARD_SIZE_IN_SQUARES][BOARD_SIZE_IN_SQUARES];
        ArrayList<ChessPosition> potentialPositions = potentialMovesToPotentialPositions(potentialMoves);
        for(int rowIter = 0; rowIter < BOARD_SIZE_IN_SQUARES; rowIter ++) {
            for (int colIter = 0; colIter < BOARD_SIZE_IN_SQUARES; colIter++) {
                ChessPosition tempPosition = new ChessPosition(rowIter+1, colIter+1);
                if(tempPosition.equals(currentPosition)){
                    squareTypes[rowIter][colIter] = SquareType.CURRENT_POSITION;
                }else if(potentialPositions.contains(tempPosition)){
                    squareTypes[rowIter][colIter] = SquareType.VALID_MOVE;
                }else{
                    squareTypes[rowIter][colIter] = SquareType.INVALID_MOVE;
                }
            }
        }
        return squareTypes;
    }

    private static ArrayList<ChessPosition> potentialMovesToPotentialPositions(Collection<ChessMove> potentialMoves){
        ArrayList<ChessPosition> potentialPositions = new ArrayList<>();
        for(ChessMove move : potentialMoves){
            potentialPositions.add(move.getEndPosition());
        }
        return potentialPositions;
    }

    private static String getSquareColor(int row, int col, SquareType[][] squareTypes){
        if(squareTypes[row][col] == SquareType.CURRENT_POSITION){
            return SET_CURRENT_SQUARE_COLOR;
        }
        if(squareTypes[row][col] == SquareType.VALID_MOVE){
            if(darkOrLight(row, col).equals(SET_LIGHT_COLOR)){
                return SET_LIGHT_HIGHLIGHT_COLOR;
            }
            return SET_DARK_HIGHLIGHT_COLOR;
        }
        return darkOrLight(row, col);
    }

    private static String darkOrLight(int row, int col){
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


    private static void drawColumnIndices(ChessGame.TeamColor color){
        if(color.equals(ChessGame.TeamColor.WHITE)){
            drawForwardColumnIndices();
        }else{
            drawReverseColumnIndices();
        }
    }

    private static void drawForwardColumnIndices(){

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

