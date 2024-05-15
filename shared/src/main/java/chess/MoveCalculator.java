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
                        potentialMoves.add(new ChessMove(myPosition, potentialPosition, type, pieceColor));
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
                potentialMoves.add(new ChessMove(myPosition, potentialPosition, type, pieceColor));
                if(board.getPiece(potentialPosition) != null) break;
            } else{
                break;
            }
        }
        //northeast
        for(int i = 1; i < 8; i++){
            ChessPosition potentialPosition = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn()+i);
            if(validMove(potentialPosition)){
                potentialMoves.add(new ChessMove(myPosition, potentialPosition, type, pieceColor));
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
                potentialMoves.add(new ChessMove(myPosition, potentialPosition, type, pieceColor));
                if(board.getPiece(potentialPosition) != null) break;
            } else{
                break;
            }
        }
        //southwest
        for(int i = 1; i < 8; i++){
            ChessPosition potentialPosition = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn()-i);
            if(validMove(potentialPosition)){
                potentialMoves.add(new ChessMove(myPosition, potentialPosition, type, pieceColor));
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
                    potentialMoves.add(new ChessMove(myPosition, potentialPositionA, type, pieceColor));
                }
                ChessPosition potentialPositionB = new ChessPosition(myPosition.getRow() + (2*rowFactor), myPosition.getColumn() + (colFactor));
                if (validMove(potentialPositionB)) {
                    potentialMoves.add(new ChessMove(myPosition, potentialPositionB, type, pieceColor));
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
                potentialMoves.add(new ChessMove(myPosition, potentialPosition, type, pieceColor));
                if(board.getPiece(potentialPosition) != null) break;
            } else{
                break;
            }
        }
        //south moves
        for(int i = 1; i < 8; i++){
            ChessPosition potentialPosition = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn());
            if(validMove(potentialPosition)){
                potentialMoves.add(new ChessMove(myPosition, potentialPosition, type, pieceColor));
                if(board.getPiece(potentialPosition) != null) break;
            } else{
                break;
            }
        }

        //east moves
        for(int i = 1; i < 8; i++){
            ChessPosition potentialPosition = new ChessPosition(myPosition.getRow(), myPosition.getColumn()+i);
            if(validMove(potentialPosition)){
                potentialMoves.add(new ChessMove(myPosition, potentialPosition, type, pieceColor));
                if(board.getPiece(potentialPosition) != null) break;
            } else{
                break;
            }
        }

        //west moves
        for(int i = 1; i < 8; i++){
            ChessPosition potentialPosition = new ChessPosition(myPosition.getRow(), myPosition.getColumn()-i);
            if(validMove(potentialPosition)){
                potentialMoves.add(new ChessMove(myPosition, potentialPosition, type, pieceColor));
                if(board.getPiece(potentialPosition) != null) break;
            } else{
                break;
            }
        }
        return potentialMoves;
    }
    //=================================================== PAWN MOVES ===================================================//
    private boolean canPromote(ChessPosition potentialPosition){
        if(pieceColor == ChessGame.TeamColor.BLACK){
            if(potentialPosition.getRow() == 1){
                return true;
            }
        }
        if(pieceColor == ChessGame.TeamColor.WHITE){
            if(potentialPosition.getRow() == 8){
                return true;
            }
        }
        return false;
    }
    private Collection<ChessMove> promotePawnMoves(ChessPosition potentialPosition){
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();
        if (validMove(potentialPosition)) {
            if (canPromote(potentialPosition)) {
                potentialMoves.add(new ChessMove(myPosition, potentialPosition, ChessPiece.PieceType.BISHOP, pieceColor));
                potentialMoves.add(new ChessMove(myPosition, potentialPosition, ChessPiece.PieceType.KNIGHT, pieceColor));
                potentialMoves.add(new ChessMove(myPosition, potentialPosition, ChessPiece.PieceType.QUEEN, pieceColor));
                potentialMoves.add(new ChessMove(myPosition, potentialPosition, ChessPiece.PieceType.ROOK, pieceColor));
            }
        }
        return potentialMoves;
    }


    public Collection<ChessMove> pawnMoves() {
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();

        //white pawn
        if (pieceColor == ChessGame.TeamColor.WHITE) {
            //passiveMoves
            ChessPosition potentialPosition1 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn());
            if (board.getPiece(potentialPosition1) == null) {
                if(canPromote(potentialPosition1)){
                    potentialMoves.addAll(promotePawnMoves(potentialPosition1));
                }else {
                    potentialMoves.add(new ChessMove(myPosition, potentialPosition1, type, pieceColor));
                }
                //check to see if we can do a double move by seeing if we are still in the 2nd row:
                if (myPosition.getRow() == 2) { //if pawn hasn't moved...
                    ChessPosition potentialPosition2 = new ChessPosition(myPosition.getRow()+2, myPosition.getColumn());
                    if (board.getPiece(potentialPosition2) == null) {
                        potentialMoves.add(new ChessMove(myPosition, potentialPosition2, type, pieceColor));
                    }
                }
            }
            //attackMoves
            //left stab
            if(myPosition.getColumn() != 1){
                ChessPosition potentialAttackPosition = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1);
                if(board.getPiece(potentialAttackPosition) != null && board.getPiece(potentialAttackPosition).getTeamColor() == ChessGame.TeamColor.BLACK){                    if(canPromote(potentialAttackPosition)){
                        potentialMoves.addAll(promotePawnMoves(potentialAttackPosition));
                    }else {
                        potentialMoves.add(new ChessMove(myPosition, potentialAttackPosition, type, pieceColor));
                    }
                }
            }
            //right stab
            if(myPosition.getColumn() != 8){
                ChessPosition potentialAttackPosition = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1);
                if(board.getPiece(potentialAttackPosition) != null && board.getPiece(potentialAttackPosition).getTeamColor() == ChessGame.TeamColor.BLACK){                    if(canPromote(potentialAttackPosition)){
                        potentialMoves.addAll(promotePawnMoves(potentialAttackPosition));
                    }else {
                        potentialMoves.add(new ChessMove(myPosition, potentialAttackPosition, type, pieceColor));
                    }
                }
            }
        }

        //black pawn
        if (pieceColor == ChessGame.TeamColor.BLACK) {
            //passiveMoves
            ChessPosition potentialPosition1 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn());
            if (board.getPiece(potentialPosition1) == null) {
                if(canPromote(potentialPosition1)){
                    potentialMoves.addAll(promotePawnMoves(potentialPosition1));
                }else {
                    potentialMoves.add(new ChessMove(myPosition, potentialPosition1, type, pieceColor));
                }
                //check to see if we can do a double move by seeing if we are still in the 2nd row:
                if (myPosition.getRow() == 7) { //if pawn hasn't moved...
                    ChessPosition potentialPosition2 = new ChessPosition(myPosition.getRow()-2, myPosition.getColumn());
                    if (validMove(potentialPosition2)) {
                        potentialMoves.add(new ChessMove(myPosition, potentialPosition2, type, pieceColor));
                    }
                }
            }
            //attackMoves
            //left stab
            if(myPosition.getColumn() != 1){
                ChessPosition potentialAttackPosition = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1);
                if(board.getPiece(potentialAttackPosition) != null && board.getPiece(potentialAttackPosition).getTeamColor() == ChessGame.TeamColor.WHITE){
                    if(canPromote(potentialAttackPosition)){
                        potentialMoves.addAll(promotePawnMoves(potentialAttackPosition));
                    }else {
                        potentialMoves.add(new ChessMove(myPosition, potentialAttackPosition, type, pieceColor));
                    }
                }
            }
            //right stab
            if(myPosition.getColumn() != 8){
                ChessPosition potentialAttackPosition = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1);
                if(board.getPiece(potentialAttackPosition) != null && board.getPiece(potentialAttackPosition).getTeamColor() == ChessGame.TeamColor.WHITE){                    if(canPromote(potentialAttackPosition)){
                        potentialMoves.addAll(promotePawnMoves(potentialAttackPosition));
                    }else {
                        potentialMoves.add(new ChessMove(myPosition, potentialAttackPosition, type, pieceColor));
                    }
                }
            }
        }
        return potentialMoves;
    }
    //=================================================== ALL COLOR MOVES ===================================================//
    public Collection<ChessMove> getAllColorMoves(ChessGame.TeamColor COLOR){
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();
        for(int row = 1; row <= 8; row ++){
            for(int col = 1; col <= 8; col ++){
                ChessPosition tempPosition = new ChessPosition(row, col);
                if(board.getPiece(tempPosition) != null){
                    if(board.getPiece(tempPosition).getTeamColor() == COLOR){
                         MoveCalculator tempCalc = new MoveCalculator(board, tempPosition, COLOR, null);
                         potentialMoves.addAll(switch(board.getPiece(tempPosition).getPieceType()){
                                     case KING -> tempCalc.kingMoves();
                                     case QUEEN -> tempCalc.queenMoves();
                                     case BISHOP -> tempCalc.bishopMoves();
                                     case KNIGHT -> tempCalc.knightMoves();
                                     case ROOK -> tempCalc.rookMoves();
                                     case PAWN -> tempCalc.pawnMoves();
                                 }
                         );
                    }
                }
            }
        }
        return potentialMoves;
    }


}

