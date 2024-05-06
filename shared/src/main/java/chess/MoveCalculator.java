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


    public Collection<ChessMove> kingMoves(){
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();
        return potentialMoves;
    }

    public Collection<ChessMove> queenMoves(){
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();
        return potentialMoves;
    }

    public Collection<ChessMove> bishopMoves(){
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();
        //northeast
        for(int i = 1; i < 8; i++){
            ChessPosition potentialPosition = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn()+i);
            //is this move validly on the board?
            if(potentialPosition.onBoard()){
                //is the square unoccupied?
                if(board.getPiece(potentialPosition) == null){
                    potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                }else{
                    if(board.getPiece(potentialPosition).getTeamColor() != pieceColor){
                        potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                    }
                    break; //stop looking
                }
            }
        }
        //northwest
        for(int i = 1; i < 8; i++){
            ChessPosition potentialPosition = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn()-i);
            //is this move validly on the board?
            if(potentialPosition.onBoard()){
                //is the square unoccupied?
                if(board.getPiece(potentialPosition) == null){
                    potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                }else{
                    if(board.getPiece(potentialPosition).getTeamColor() != pieceColor){
                        potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                    }
                    break; //stop looking
                }
            }
        }
        //southeast
        for(int i = 1; i < 8; i++){
            ChessPosition potentialPosition = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn()+i);
            //is this move validly on the board?
            if(potentialPosition.onBoard()){
                //is the square unoccupied?
                if(board.getPiece(potentialPosition) == null){
                    potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                }else{
                    if(board.getPiece(potentialPosition).getTeamColor() != pieceColor){
                        potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                    }
                    break; //stop looking
                }
            }
        }
        //southwest
        for(int i = 1; i < 8; i++){
            ChessPosition potentialPosition = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn()-i);
            //is this move validly on the board?
            if(potentialPosition.onBoard()){
                //is the square unoccupied?
                if(board.getPiece(potentialPosition) == null){
                    potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                }else{
                    if(board.getPiece(potentialPosition).getTeamColor() != pieceColor){
                        potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                    }
                    break; //stop looking
                }
            }
        }
        return potentialMoves;
    }

    public Collection<ChessMove> knightMoves(){
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();
        return potentialMoves;
    }
//=================================================== ROOK MOVES ===================================================//
    public Collection<ChessMove> rookMoves(){
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();
        //north moves
        for(int i = 1; i < 8; i++){
            ChessPosition potentialPosition = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn());
            //is this move validly on the board?
            if(potentialPosition.onBoard()){
                //is the square unoccupied?
                if(board.getPiece(potentialPosition) == null){
                    potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                }else{
                    if(board.getPiece(potentialPosition).getTeamColor() != pieceColor){
                        potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                    }
                    break; //stop looking
                }
            }
        }
        //south moves
        for(int i = 1; i < 8; i++){
            ChessPosition potentialPosition = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn());
            //is this move validly on the board?
            if(potentialPosition.onBoard()){
                //is the square unoccupied?
                if(board.getPiece(potentialPosition) == null){
                    potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                }else{
                    if(board.getPiece(potentialPosition).getTeamColor() != pieceColor){
                        potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                    }
                    break; //stop looking
                }
            }
        }

        //east moves
        for(int i = 1; i < 8; i++){
            ChessPosition potentialPosition = new ChessPosition(myPosition.getRow(), myPosition.getColumn()+i);
            //is this move validly on the board?
            if(potentialPosition.onBoard()){
                //is the square unoccupied?
                if(board.getPiece(potentialPosition) == null){
                    potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                }else{
                    if(board.getPiece(potentialPosition).getTeamColor() != pieceColor){
                        potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                    }
                    break; //stop looking
                }
            }
        }

        //west moves
        for(int i = 1; i < 8; i++){
            ChessPosition potentialPosition = new ChessPosition(myPosition.getRow(), myPosition.getColumn()-i);
            //is this move validly on the board?
            if(potentialPosition.onBoard()){
                //is the square unoccupied?
                if(board.getPiece(potentialPosition) == null){
                    potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                }else{
                    if(board.getPiece(potentialPosition).getTeamColor() != pieceColor){
                        potentialMoves.add(new ChessMove(myPosition, potentialPosition, type));
                    }
                    break; //stop looking
                }
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
