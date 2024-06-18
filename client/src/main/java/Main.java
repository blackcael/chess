import chess.*;
import ui.Client;
import ui.EscapeSequences;

import java.util.Scanner;

public class Main {
    private static final String SET_PROMPT_COLOR = EscapeSequences.SET_TEXT_COLOR_WHITE;

    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        clientLoop();
    }

    private synchronized static void clientLoop(){
        Client client = new Client();
        while (true){
            System.out.print(SET_PROMPT_COLOR + client.getClientStatus().toString() + ">>>");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            var arguments = line.split(" ");
            if(arguments[0].equals("quit")){
                //preClosing code (ie, logout?)
                break;
            }
            client.processArguments(arguments);
        }
        System.out.println("Program Terminated");
    }
}