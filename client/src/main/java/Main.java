import chess.*;
import ui.Client;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        clientLoop();
    }

    private static void clientLoop(){
        Client client = new Client();
        while (true){
            System.out.print(client.getClientStatus().toString() + ">>>");
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