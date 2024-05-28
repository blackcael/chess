package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private ChessBoard board = new ChessBoard();
    private TeamColor teamTurn = TeamColor.WHITE;
    public ChessGame() {
        board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    private void changeTeamTurn(){
        setTeamTurn(opposingTeamColor(teamTurn));
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    public TeamColor opposingTeamColor(TeamColor color){
        if(color == TeamColor.BLACK){
            return TeamColor.WHITE;
        }
        return TeamColor.BLACK;
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ArrayList<ChessMove> validMoveList = new ArrayList<>();
        if(board.getPiece(startPosition) == null){
            return null;
        }
        ArrayList<ChessMove> potentialMoveList = new ArrayList<>(board.getPiece(startPosition).pieceMoves(board, startPosition));
        for(ChessMove move : potentialMoveList){
            ChessGame testGame = new ChessGame();
            testGame.setBoard(board.clone());
            testGame.setTeamTurn(teamTurn);
            boolean success = false;
            try{
                testGame.hypotheticalMakeMove(move);
                success = true;
            } catch (InvalidMoveException e){
                System.out.println("Error:" + e.getMessage());
            }
            if(success){
                validMoveList.add(move);
            }

        }
        return validMoveList;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        //if (not my turn || is not in the list of possible moves|| willBeInCheck) >> throw exception
        //0. If there is no piece
        if(board.getPiece(move.getStartPosition()) == null){
            throw new InvalidMoveException();
        }

        //1. If it is not our turn, throw exception.
        if(move.getTeamColor(board) != teamTurn){
            throw new InvalidMoveException();
        }

        //2. If the move is not within the list of our potential moves, throw exception.
        ChessPiece piece = board.getPiece(move.getStartPosition());

        ArrayList<ChessMove> potentialMoves = new ArrayList<>(piece.pieceMoves(board, move.getStartPosition()));
        if (!potentialMoves.contains(move)){
            throw new InvalidMoveException();
        }

        //3. Make the Move; If we are in check after the move is made, throw exception.
        if(move.getPromotionPiece() != null){  //passing the correct piece (promotion or normal piece) logic
            piece = new ChessPiece(piece.getTeamColor(), move.getPromotionPiece());
        }
        board.movePiece(move.getStartPosition(), move.getEndPosition(), piece);
        if(isInCheck(piece.getTeamColor())){
            throw new InvalidMoveException();
        }

        changeTeamTurn();
    }

    public void hypotheticalMakeMove(ChessMove move) throws InvalidMoveException {
        //if (not my turn || is not in the list of possible moves|| willBeInCheck) >> throw exception
        //0. If there is no piece
        if(board.getPiece(move.getStartPosition()) == null){
            throw new InvalidMoveException();
        }

        //2. If the move is not within the list of our potential moves, throw exception.
        ChessPiece piece = board.getPiece(move.getStartPosition());
        ArrayList<ChessMove> potentialMoves = new ArrayList<>(piece.pieceMoves(board, move.getStartPosition()));
        if (!potentialMoves.contains(move)){
            throw new InvalidMoveException();
        }
        //3. Make the Move; If we are in check after the move is made, throw exception.
        if(move.getPromotionPiece() != null){  //passing the correct piece (promotion or normal piece) logic
            piece = new ChessPiece(piece.getTeamColor(), move.getPromotionPiece());
        }
        board.movePiece(move.getStartPosition(), move.getEndPosition(), piece);
        if(isInCheck(piece.getTeamColor())){
            throw new InvalidMoveException();
        }
        changeTeamTurn();
    }


    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        //iterate all of our opponent's pieces, and get their current potential moves. If any of them inlcude the space upon which our king is, we are in check
        //step 1: assemble mega list of opponent's moves
        //step 2: find where OUR king is
        //step 3: if the position of our king is included in that list, return true, else, false.
        ArrayList<ChessMove> allMoves = new ArrayList<>(board.getAllMovesOfColor(opposingTeamColor(teamColor)));
        ChessPosition kingPosition = board.getKingPosition(teamColor);
        for(ChessMove move : allMoves){
            ChessPosition endPosition = move.getEndPosition();
            if(endPosition.equals(kingPosition)){
                return true;
            }
        }
        return false;
    }
    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    private boolean hasMoves(TeamColor teamColor){
        ChessGame.TeamColor realTurn = teamTurn;
        teamTurn = teamColor; //SIMULATE!
        for(ChessPosition position : board.getAllPiecePositionsOfColor(teamColor)){
            ArrayList<ChessMove> validMoveList = new ArrayList<>(validMoves(position));
            if(!validMoveList.isEmpty()){
                teamTurn = realTurn;
                return true;
            }
        }
        teamTurn = realTurn;
        return false;
    }

    public boolean isInCheckmate(TeamColor teamColor) {
        return(isInCheck(teamColor) && !hasMoves(teamColor));
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return(!isInCheck(teamColor) && !hasMoves(teamColor));
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
