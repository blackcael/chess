package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] squares = new ChessPiece[8][8];
    //This created an 8 x 8 array titled "squares" that is composed of the class type "chess piece"
    public ChessBoard() {
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * kind of the jank solution, but if there is built in garbage solution, then it is actually elegant (think about it)
     * (How the game of chess normally starts)
     */
    private void setSpecialRow(ChessGame.TeamColor pieceColor){
        int rowVal = 0;
        if(pieceColor == ChessGame.TeamColor.WHITE){
            rowVal = 1;
        }
        if(pieceColor == ChessGame.TeamColor.BLACK){
            rowVal = 8;
        }
        addPiece(new ChessPosition(rowVal,1), new ChessPiece(pieceColor, ChessPiece.PieceType.ROOK));
        addPiece(new ChessPosition(rowVal,2), new ChessPiece(pieceColor, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(rowVal,3), new ChessPiece(pieceColor, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(rowVal,4), new ChessPiece(pieceColor, ChessPiece.PieceType.QUEEN));
        addPiece(new ChessPosition(rowVal,5), new ChessPiece(pieceColor, ChessPiece.PieceType.KING));
        addPiece(new ChessPosition(rowVal,6), new ChessPiece(pieceColor, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(rowVal,7), new ChessPiece(pieceColor, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(rowVal,8), new ChessPiece(pieceColor, ChessPiece.PieceType.ROOK));
    }
    private void setPawnRow(ChessGame.TeamColor pieceColor){
        int rowVal = 0;
        if(pieceColor == ChessGame.TeamColor.WHITE){
            rowVal = 2;
        }
        if(pieceColor == ChessGame.TeamColor.BLACK){
            rowVal = 7;
        }
        for(int colVal = 1; colVal <= 8; colVal++){
            addPiece(new ChessPosition(rowVal,colVal), new ChessPiece(pieceColor, ChessPiece.PieceType.PAWN));
        }
    }

    public void resetBoard() {
        squares = new ChessPiece[8][8];
        //PLACE ROW 1
        setPawnRow(ChessGame.TeamColor.BLACK);
        setSpecialRow(ChessGame.TeamColor.BLACK);
        setPawnRow(ChessGame.TeamColor.WHITE);
        setSpecialRow(ChessGame.TeamColor.WHITE);
    }

    @Override
    public ChessBoard clone(){
        ChessBoard board = new ChessBoard();
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                ChessPiece clonePiece = new ChessPiece(squares[row][col].getTeamColor(), squares[row][col].getPieceType());
                board.addPiece(new ChessPosition(row, col), clonePiece);
            }
        }
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }
}
