package ui;

import chess.ChessGame;
import websocket.messages.*;

public class WebSocketNotifier {

    public static void notify(ServerMessage serverMessage, ChessGame.TeamColor teamColor, WebSocketCommunicator parent){
        switch(serverMessage.getServerMessageType()){
            case ServerMessage.ServerMessageType.NOTIFICATION -> printNotification((NotificationMessage)serverMessage);
            case ServerMessage.ServerMessageType.ERROR -> printError((ErrorMessage)serverMessage);
            case ServerMessage.ServerMessageType.LOAD_GAME -> loadGame((LoadGameMessage)serverMessage, teamColor, parent);
        }
    }

    //WE MAY NEED TO DO SOME COOL SYNCHRONIZED BITS HERE

    private static void loadGame(LoadGameMessage message, ChessGame.TeamColor teamColor, WebSocketCommunicator parent){
        if (teamColor == null){
            teamColor = ChessGame.TeamColor.WHITE;  //INSTEAD WE COULD SIMPLY HERE DRAW BOTH BOARDS?
        }
        parent.setUpdatedGame(message.getGame());
        BoardPrinter.drawBoard(message.getGame().getBoard(), teamColor, null, null);
    }

    private static void printNotification(NotificationMessage message){
        System.out.println(message.getMessage());
    }

    private static void printError(ErrorMessage message){
        System.out.println(message.getErrorMessage());
    }
}
