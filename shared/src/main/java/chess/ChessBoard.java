package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

    public void removePiece(ChessPosition position){
        squares[position.getRow()-1][position.getColumn()-1] = null;
    }

    public void movePiece(ChessPosition startPosition, ChessPosition endPosition, ChessPiece piece){
        addPiece(endPosition, piece);
        removePiece(startPosition);
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

    public ChessPosition getKingPosition(ChessGame.TeamColor color){
        for(int row = 1; row <= 8; row++){
            for(int col = 1; col <= 8; col++){
                ChessPosition potentialPosition = new ChessPosition(row, col);
                if(getPiece(potentialPosition) != null){
                    if(getPiece(potentialPosition).getPieceType() == ChessPiece.PieceType.KING){
                        if(getPiece(potentialPosition).getTeamColor() == color){
                            return potentialPosition;
                        }
                    }
                }
            }
        }
        return null;
    }

    public Collection<ChessPosition> getAllPiecePositionsOfColor(ChessGame.TeamColor color){
        ArrayList<ChessPosition> positionList = new ArrayList<>();
        for(int row = 1; row <= 8; row ++){
            for(int col = 1; col <= 8; col ++){
                ChessPosition testPosition = new ChessPosition(row, col);
                if(getPiece(testPosition) != null){
                    if(getPiece(testPosition).getTeamColor() == color){
                        positionList.add(testPosition);
                    }
                }
            }
        }
        return positionList;
    }

    public Collection<ChessMove> getAllMovesOfColor(ChessGame.TeamColor color){
        ArrayList<ChessMove> moveList = new ArrayList<>();
        for(ChessPosition position : getAllPiecePositionsOfColor(color)){
            moveList.addAll(getPiece(position).pieceMoves(this, position));
        }
        return moveList;
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
        ChessBoard cloneBoard = new ChessBoard();
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                if(squares[row][col] != null) {
                    ChessPiece clonePiece = new ChessPiece(squares[row][col].getTeamColor(), squares[row][col].getPieceType());
                    cloneBoard.addPiece(new ChessPosition(row + 1, col + 1), clonePiece);
                }
            }
        }
        return cloneBoard;
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
