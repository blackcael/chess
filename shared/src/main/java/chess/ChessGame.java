package chess;

import javax.swing.text.html.HTMLDocument;
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

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    public TeamColor opposingTeamColor(TeamColor Color){
        if(Color == TeamColor.BLACK){
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
        if(board.getPiece(startPosition) == null){
            return null;
        }
        return new ArrayList<>(board.getPiece(startPosition).pieceMoves(board, startPosition));
        //TODO: complete, we need to filter out the moves that leave our king in danger.
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        //if move not in validMoves || isInCheck || not my turn
        if(move.getTeamColor() != teamTurn){
            throw new InvalidMoveException();
        }
        if(!validMoves(move.getStartPosition()).contains(move)){
            throw new InvalidMoveException();
        }
        //create a board where this theoretical move has been made. if we are still in check, throwError
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
        ChessGame.TeamColor opposingColor = opposingTeamColor(teamColor);
        MoveCalculator allCalc = new MoveCalculator(board, new ChessPosition(1,1), opposingColor, null);
        ArrayList<ChessMove> allMoves = new ArrayList<ChessMove>(allCalc.getAllColorMoves());
        for(ChessMove move : allMoves){
            if(move.getEndPosition() == board.getKingPosition(teamColor)){
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
    public boolean isInCheckmate(TeamColor teamColor) {
        if(isInCheck(teamColor) && validMoves(board.getKingPosition(teamColor)) == null){
            return true;
        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if(!isInCheck(teamColor) && validMoves(board.getKingPosition(teamColor))==null){
            return true;
        }
        return false;
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
