package client;

import chess.ChessGame;
import org.junit.jupiter.api.Test;
import ui.BoardPrinter;
import ui.GameplayUI;

public class BoardTest {

    @Test
    public void printBoardTest(){
        ChessGame chessGame = new ChessGame();
        BoardPrinter.drawBoards(chessGame.getBoard());
    }
}
