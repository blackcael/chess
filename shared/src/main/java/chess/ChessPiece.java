package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return switch (type) {
            case KING -> kingMoves(board, myPosition);
            case QUEEN -> queenMoves(board, myPosition);
            case BISHOP -> bishopMoves(board, myPosition);
            case KNIGHT -> knightMoves(board, myPosition);
            case ROOK -> rookMoves(board, myPosition);
            case PAWN -> pawnMoves(board, myPosition);
        };
    }

    private Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();
        return potentialMoves;
    }

    private Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();
        return potentialMoves;
    }

    private Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();
        return potentialMoves;
    }

    private Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();
        return potentialMoves;
    }

    private Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();
        return potentialMoves;
    }

    private Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();

        //white pawn
        if (pieceColor == ChessGame.TeamColor.WHITE) {
            //passiveMoves
            ChessPosition potentialPosition1 = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn());
            if (board.getPiece(potentialPosition1) == null) {
                potentialMoves.add(new ChessMove(myPosition, potentialPosition1, type)); //puts its own current type into promotion. TODO: problem?
            }
            //check to see if we can do a double move by seeing if we are still in the 2nd row:
            if (myPosition.getRow() == 1) {
                ChessPosition potentialPosition2 = new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn());
                if ((board.getPiece(potentialPosition1) == null) && (board.getPiece(potentialPosition2) == null)) {
                    potentialMoves.add(new ChessMove(myPosition, potentialPosition2, type));
                }
            }

            //attackMoves
            //left stab
            if (myPosition.getColumn() != 0) {
                ChessPosition potentialAttackPositionL = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1);
                if (board.getPiece(potentialAttackPositionL).getTeamColor() == ChessGame.TeamColor.BLACK) {
                    potentialMoves.add(new ChessMove(myPosition, potentialAttackPositionL, type));
                }
            }
            //right stab
            if (myPosition.getColumn() != 8) {
                ChessPosition potentialAttackPositionR = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1);
                if (board.getPiece(potentialAttackPositionR).getTeamColor() == ChessGame.TeamColor.BLACK) {
                    potentialMoves.add(new ChessMove(myPosition, potentialAttackPositionR, type));
                }
            }
        }

        //black pawn
        if (pieceColor == ChessGame.TeamColor.BLACK) {
            //passiveMoves
            ChessPosition potentialPosition1 = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn());
            if (board.getPiece(potentialPosition1) == null) {
                potentialMoves.add(new ChessMove(myPosition, potentialPosition1, type)); //puts its own current type into promotion. TODO: problem?
            }
            //check to see if we can do a double move by seeing if we are still in the 2nd row:
            if (myPosition.getRow() == 7) {
                ChessPosition potentialPosition2 = new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn());
                if ((board.getPiece(potentialPosition1) == null) && (board.getPiece(potentialPosition2) == null)) {
                    potentialMoves.add(new ChessMove(myPosition, potentialPosition2, type));
                }
            }

            //attackMoves
            //left stab
            if (myPosition.getColumn() != 0) {
                ChessPosition potentialAttackPositionL = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1);
                if (board.getPiece(potentialAttackPositionL).getTeamColor() == ChessGame.TeamColor.WHITE) {
                    potentialMoves.add(new ChessMove(myPosition, potentialAttackPositionL, type));
                }
            }
            //right stab
            if (myPosition.getColumn() != 8) {
                ChessPosition potentialAttackPositionR = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1);
                if (board.getPiece(potentialAttackPositionR).getTeamColor() == ChessGame.TeamColor.WHITE) {
                    potentialMoves.add(new ChessMove(myPosition, potentialAttackPositionR, type));
                }
            }
        }
        return potentialMoves;
    }
}
