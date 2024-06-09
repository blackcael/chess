package client;

import chess.ChessGame;
import org.junit.jupiter.api.Test;
import ui.GameplayUI;

public class BoardTest {

    @Test
    public void printBoardTest(){
        ChessGame chessGame = new ChessGame();
        GameplayUI.drawBoards(chessGame.getBoard());
    }
}
