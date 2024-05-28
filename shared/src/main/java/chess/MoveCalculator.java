package chess;

import java.util.ArrayList;
import java.util.Collection;

public class MoveCalculator {
    private final ChessPosition position;
    private final ChessBoard board;
    private final ChessGame.TeamColor pieceColor;
    MoveCalculator(ChessPosition position, ChessBoard board){
        this.position = position;
        this.board = board;
        this.pieceColor = board.getPiece(position).getTeamColor();
    }

    private enum MoveType {
        VALID, INVALID, CAPTURE
    }

    private MoveType getMoveType(ChessPosition position) {
        if (position.onBoard()) {
            if (board.getPiece(position) == null) {
                return MoveType.VALID;
            }
            if (board.getPiece(position).getTeamColor() != pieceColor) {
                return MoveType.CAPTURE;
            }
        }
        return MoveType.INVALID;
    }


    public Collection<ChessMove> kingMoves(){
        ArrayList<ChessMove> potentialMoves = new ArrayList<ChessMove>();
        for(int rowIter = -1; rowIter <=1; rowIter++){
            for(int colIter = -1; colIter <=1; colIter++){
                if(!(rowIter == 0 && colIter == 0)){
                    processPositionAlgorithm(potentialMoves, rowIter, colIter);
                }
            }
        }
        return potentialMoves;
    }

    private void processPositionAlgorithm(ArrayList<ChessMove> potentialMoves, int rowIter, int colIter){
        ChessPosition potentialPosition = new ChessPosition(position.getRow()+rowIter, position.getColumn()+colIter);
        if(getMoveType(potentialPosition) == MoveType.VALID || getMoveType(potentialPosition) == MoveType.CAPTURE){
            potentialMoves.add(new ChessMove(position, potentialPosition, null));
        }
    }

    public Collection<ChessMove> knightMoves(){
        ArrayList<ChessMove> potentialMoves = new ArrayList<ChessMove>();
        for(int rowIter = -2; rowIter <=2; rowIter++){
            for(int colIter = -2; colIter <=2; colIter++){
                if(!(rowIter == 0 || colIter == 0 || rowIter == colIter || rowIter == (-1 * colIter))){
                    processPositionAlgorithm(potentialMoves, rowIter, colIter);
                }
            }
        }
        return potentialMoves;
    }

    public Collection<ChessMove> bishopMoves(){
        ArrayList<ChessMove> potentialMoves = new ArrayList<ChessMove>();
        for(int rowIter = -1; rowIter <=1; rowIter += 2){
            for(int colIter = -1; colIter <=1; colIter +=2){
                for(int moveMag = 1; moveMag <= 7; moveMag++){
                    ChessPosition potentialPosition = new ChessPosition(
                            position.getRow()+(rowIter * moveMag),
                            position.getColumn()+(colIter * moveMag)
                    );
                    if(getMoveType(potentialPosition) == MoveType.VALID ){
                        potentialMoves.add(new ChessMove(position, potentialPosition, null));
                    }
                    if(getMoveType(potentialPosition) == MoveType.CAPTURE){
                        potentialMoves.add(new ChessMove(position, potentialPosition, null));
                        break;
                    }
                    if(getMoveType(potentialPosition) == MoveType.INVALID){
                        break;
                    }
                }
            }
        }
        return potentialMoves;
    }

    public Collection<ChessMove> rookMoves(){
        ArrayList<ChessMove> potentialMoves = new ArrayList<ChessMove>();
        for(int rowIter = -1; rowIter <=1; rowIter += 2){
            for(int moveMag = 1; moveMag <= 7; moveMag++){
                ChessPosition potentialPosition = new ChessPosition(
                        position.getRow()+(rowIter * moveMag),
                        position.getColumn()
                );
                if(getMoveType(potentialPosition) == MoveType.VALID ){
                    potentialMoves.add(new ChessMove(position, potentialPosition, null));
                }
                if(getMoveType(potentialPosition) == MoveType.CAPTURE){
                    potentialMoves.add(new ChessMove(position, potentialPosition, null));
                    break;
                }
                if(getMoveType(potentialPosition) == MoveType.INVALID){
                    break;
                }
            }
        }
        for(int colIter = -1; colIter <=1; colIter +=2){
            for(int moveMag = 1; moveMag <= 7; moveMag++){
                ChessPosition potentialPosition = new ChessPosition(
                        position.getRow(),
                        position.getColumn()+(colIter * moveMag)
                );
                if(getMoveType(potentialPosition) == MoveType.VALID ){
                    potentialMoves.add(new ChessMove(position, potentialPosition, null));
                }
                if(getMoveType(potentialPosition) == MoveType.CAPTURE){
                    potentialMoves.add(new ChessMove(position, potentialPosition, null));
                    break;
                }
                if(getMoveType(potentialPosition) == MoveType.INVALID){
                    break;
                }
            }
        }
        return potentialMoves;
    }

    public Collection<ChessMove> queenMoves() {
        ArrayList<ChessMove> potentialMoves = new ArrayList<ChessMove>();
        potentialMoves.addAll(rookMoves());
        potentialMoves.addAll(bishopMoves());
        return potentialMoves;
    }
    private boolean canPromote(ChessPosition position){
        if(pieceColor == ChessGame.TeamColor.WHITE){
            if(position.getRow() == 8){
                return true;
            }
        }
        if(pieceColor == ChessGame.TeamColor.BLACK){
            if(position.getRow() == 1){
                return true;
            }
        }
        return false;
    }

    private Collection<ChessMove> promotionMoves(ChessPosition newPosition){
        ArrayList<ChessMove> potentialMoves = new ArrayList<ChessMove>();
        potentialMoves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.ROOK));
        potentialMoves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.BISHOP));
        potentialMoves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.KNIGHT));
        potentialMoves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.QUEEN));
        return potentialMoves;
    }


    public Collection<ChessMove> pawnMoves(){
        ArrayList<ChessMove> potentialMoves = new ArrayList<ChessMove>();
        //passive moves
        int moveDir = 1;
        int initialPos = 2;
        if(pieceColor == ChessGame.TeamColor.BLACK){
            moveDir = -1;
            initialPos = 7;
        }
        ChessPosition potPosition1 = new ChessPosition(position.getRow()+moveDir, position.getColumn());
        if(getMoveType(potPosition1) == MoveType.VALID){
            if(canPromote(potPosition1)){
                potentialMoves.addAll(promotionMoves(potPosition1));
            }else{
                potentialMoves.add(new ChessMove(position, potPosition1, null));
                if(position.getRow() == initialPos){
                    ChessPosition potPosition2 = new ChessPosition(position.getRow()+(2*moveDir), position.getColumn());
                    if(getMoveType(potPosition2) == MoveType.VALID){
                        potentialMoves.add(new ChessMove(position, potPosition2, null));
                    }
                }
            }
        }

        //attack moves
        //left attack
        if(position.getColumn() != 1){
            ChessPosition attackPosL = new ChessPosition(position.getRow()+moveDir, position.getColumn()-1);
            if(getMoveType(attackPosL) == MoveType.CAPTURE){
                if(canPromote(attackPosL)){
                    potentialMoves.addAll(promotionMoves(attackPosL));
                }else{
                    potentialMoves.add(new ChessMove(position, attackPosL, null));
                }
            }
        }
        //right attack
        if(position.getColumn() != 8){
            ChessPosition attackPosR = new ChessPosition(position.getRow()+moveDir, position.getColumn()+1);
            if(getMoveType(attackPosR) == MoveType.CAPTURE){
                if(canPromote(attackPosR)){
                    potentialMoves.addAll(promotionMoves(attackPosR));
                }else{
                    potentialMoves.add(new ChessMove(position, attackPosR, null));
                }
            }
        }
        return potentialMoves;
    }
}
