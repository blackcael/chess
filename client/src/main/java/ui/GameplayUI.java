package ui;

import chess.*;

import java.util.Scanner;

public class GameplayUI extends BaseUI{

    private ChessGame chessGame;

    public GameplayUI(ServerFacade serverFacade) {
        super(serverFacade);
    }

//

    public Client.UIStatusType help(){
        printHelpStatement("redrawChessBoard", "redraws the chess board in the current game state");
        printHelpStatement("leave", "leave the game");
        printHelpStatement("makeMove <START_ROW><START_COL> <END_ROW><END_COL> <PROMOTION_PIECE>", "makes a move from start to end, if no promotion, do not specify a promotion piece");
        printHelpStatement("resign", "resign to your opponent, ends the game (will ask for confirmation)");
        printHelpStatement("highlightLegalMoves <ROW><COL>", "highlights the legal moves given a board position");
        printHelpStatement("help", "list commands and their descriptions");
        return Client.UIStatusType.GAMEPLAY;
    }

    public Client.UIStatusType redrawChessBoard(){
        BoardPrinter.drawBoard(chessGame.getBoard(), serverFacade.getColor(), null, null);
        return Client.UIStatusType.GAMEPLAY;
    }

    public Client.UIStatusType leave(){
        serverFacade.leave();
        return Client.UIStatusType.POSTLOGIN;
    }

    public Client.UIStatusType makeMove(String[] params){
        ChessPiece.PieceType promotionPiece = null;
        if(params.length == 3){
            promotionPiece = stringToPieceType(params[2]);
        }else if (invalidArgumentCount(params, 2)){
            return Client.UIStatusType.GAMEPLAY;
        }
        ChessPosition startPosition = coordsToPosition(params[0]);
        ChessPosition endPosition = coordsToPosition(params[1]);
        ChessMove chessMove = new ChessMove(startPosition, endPosition, promotionPiece);

        serverFacade.makeMove(chessMove);

        return Client.UIStatusType.GAMEPLAY;
    }

    public Client.UIStatusType resign(){
        System.out.print("Are you sure you want to resign? (yes/no): ");
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine().trim().toLowerCase();
        if(response.equals("yes")){
            serverFacade.resign();
        }
        return Client.UIStatusType.GAMEPLAY;
    }

    public Client.UIStatusType highlightLegalMoves(String[] params){
        if (invalidArgumentCount(params, 1)){
            return Client.UIStatusType.GAMEPLAY;
        }
        ChessPosition currentPosition = coordsToPosition(params[0]);
        BoardPrinter.drawBoard(chessGame.getBoard(), serverFacade.getColor(), chessGame.validMoves(currentPosition), currentPosition);
        return Client.UIStatusType.GAMEPLAY;
    }

    //helpers
    private final String[] COLUMN_INDICES = {"a","b","c","d","e","f","g","h"};
    private final String[] ROW_INDICES  = {"1","2","3","4","5","6","7","8"};
    private ChessPosition coordsToPosition(String stringPos){
        int column = 0;
        int row = 0;
        for(int i = 0; i < 8; i ++){
            if(stringPos.contains(COLUMN_INDICES[i])){
                column = i + 1;
            }
            if(stringPos.contains(ROW_INDICES[i])){
                row = i+1;
            }
        }
        return new ChessPosition(row, column);
    }

    private ChessPiece.PieceType stringToPieceType(String inputString){
        return switch(inputString.toLowerCase()){
            case "knight" -> ChessPiece.PieceType.KNIGHT;
            case "queen" -> ChessPiece.PieceType.QUEEN;
            case "rook" -> ChessPiece.PieceType.ROOK;
            case "bishop" -> ChessPiece.PieceType.BISHOP;
            default -> throw new IllegalStateException("Unexpected value: " + inputString);
        };
    }

    private void updateGame(){
        chessGame = serverFacade.getUpdatedGame();
    }







}
