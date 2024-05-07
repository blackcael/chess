package chess;

import java.util.ArrayList;
import java.util.Collection;

public class MoveCalculator {
    private final ChessBoard board;
    private final ChessPosition myPosition;
    private final ChessGame.TeamColor pieceColor;
    private final ChessPiece.PieceType type;

    public MoveCalculator(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, ChessPiece.PieceType type){
        this.board = board;
        this.myPosition = myPosition;
        this.pieceColor = pieceColor;
        this.type = type;
    }

    //consider condensing the code via an abstracting function
    private boolean validMove(ChessPosition potentialPosition){
        if(potentialPosition.onBoard()){ //is the square on the board?
            if(board.getPiece(potentialPosition) == null){ //if so, is the square unoccupied?
                return true;
            }else{
                if(board.getPiece(potentialPosition).getTeamColor() != pieceColor){ //if not, is the square occupied by an enemy?
                    return true;
                }
            }
        }
        return false;
    }

    //=================================================== KING MOVES ===================================================//
    public Collection<ChessMove> kingMoves(){
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();
        for(int rowIterator = -1; rowIterator <= 1; rowIterator++){
            for(int colIterator = -1; colIterator <= 1; colIterator++){
                if(!(rowIterator == 0 && colIterator == 0)){
                    ChessPosition potentialPosition =
                            new ChessPosition(myPosition.getRow()+rowIterator, myPosition.getColumn()+colIterator);
                    if(validMove(potentialPosition)){
                        potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                    }
                }
            }
        }
        return potentialMoves;
    }
    //================================================== QUEEN MOVES ==================================================//
    public Collection<ChessMove> queenMoves(){
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();
        potentialMoves.addAll(bishopMoves());
        potentialMoves.addAll(rookMoves());
        return potentialMoves;
    }

    //================================================== BISHOP MOVES ==================================================//
    public Collection<ChessMove> bishopMoves(){
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();
        //southeast
        for(int i = 1; i < 8; i++){
            ChessPosition potentialPosition = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn()+i);
            if(validMove(potentialPosition)){
                potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                if(board.getPiece(potentialPosition) != null) break;
            } else{
                break;
            }
        }
        //northeast
        for(int i = 1; i < 8; i++){
            ChessPosition potentialPosition = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn()+i);
            if(validMove(potentialPosition)){
                potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                if(board.getPiece(potentialPosition) != null) break;
            } else{
                break;
            }
        }
        //northwest
        for(int i = 1; i < 8; i++){
            ChessPosition potentialPosition = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn()-i);
            //is this move validly on the board?
            if(validMove(potentialPosition)){
                potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                if(board.getPiece(potentialPosition) != null) break;
            } else{
                break;
            }
        }
        //southwest
        for(int i = 1; i < 8; i++){
            ChessPosition potentialPosition = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn()-i);
            if(validMove(potentialPosition)){
                potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                if(board.getPiece(potentialPosition) != null) break;
            } else{
                break;
            }
        }
        return potentialMoves;
    }
    //================================================== KNIGHT MOVES ==================================================//
    public Collection<ChessMove> knightMoves(){
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();
        for(int rowFactor = -1; rowFactor <= 1; rowFactor += 2) {
            for (int colFactor = -1; colFactor <= 1; colFactor += 2) {
                ChessPosition potentialPositionA = new ChessPosition(myPosition.getRow() + (rowFactor), myPosition.getColumn() + (2*colFactor));
                if (validMove(potentialPositionA)) {
                    potentialMoves.add(new ChessMove(myPosition, potentialPositionA, type));
                }
                ChessPosition potentialPositionB = new ChessPosition(myPosition.getRow() + (2*rowFactor), myPosition.getColumn() + (colFactor));
                if (validMove(potentialPositionB)) {
                    potentialMoves.add(new ChessMove(myPosition, potentialPositionB, type));
                }
            }
        }
        return potentialMoves;
    }
    //=================================================== ROOK MOVES ===================================================//
    public Collection<ChessMove> rookMoves(){
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();
        //north moves
        for(int i = 1; i < 8; i++){
            ChessPosition potentialPosition = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn());
            if(validMove(potentialPosition)){
                potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                if(board.getPiece(potentialPosition) != null) break;
            } else{
                break;
            }
        }
        //south moves
        for(int i = 1; i < 8; i++){
            ChessPosition potentialPosition = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn());
            if(validMove(potentialPosition)){
                potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                if(board.getPiece(potentialPosition) != null) break;
            } else{
                break;
            }
        }

        //east moves
        for(int i = 1; i < 8; i++){
            ChessPosition potentialPosition = new ChessPosition(myPosition.getRow(), myPosition.getColumn()+i);
            if(validMove(potentialPosition)){
                potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                if(board.getPiece(potentialPosition) != null) break;
            } else{
                break;
            }
        }

        //west moves
        for(int i = 1; i < 8; i++){
            ChessPosition potentialPosition = new ChessPosition(myPosition.getRow(), myPosition.getColumn()-i);
            if(validMove(potentialPosition)){
                potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                if(board.getPiece(potentialPosition) != null) break;
            } else{
                break;
            }
        }
        return potentialMoves;
    }
    //=================================================== PAWN MOVES ===================================================//
    public Collection<ChessMove> pawnMoves() {
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
            if (myPosition.getRow() == 6) {
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
