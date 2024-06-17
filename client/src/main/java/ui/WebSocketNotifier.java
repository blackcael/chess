package ui;

import chess.ChessGame;
import websocket.messages.*;

public class WebSocketNotifier {

    public static void notify(ServerMessage serverMessage){
        switch(serverMessage.getServerMessageType()){
            case ServerMessage.ServerMessageType.NOTIFICATION -> printNotification((NotificationMessage)serverMessage);
            case ServerMessage.ServerMessageType.ERROR -> printError((ErrorMessage)serverMessage);
            case ServerMessage.ServerMessageType.LOAD_GAME -> loadGame((LoadGameMessage)serverMessage);
        }
    }

    //WE MAY NEED TO DO SOME COOL SYNCHRONIZED BITS HERE

    private static void loadGame(LoadGameMessage message){
        //TODO get correct color!!
            //figure out if we are player or observer
            // if (player && player == BLACK) teamColor = Black.  !Perhaps do this by comparing game usernames with our username?
        ChessGame.TeamColor teamColor = ChessGame.TeamColor.WHITE; //TODO get correct color!!
        BoardPrinter.drawBoard(message.getGame().getBoard(), teamColor, null, null);
    }

    private static void printNotification(NotificationMessage message){
        System.out.println(message.getMessage());
    }

    private static void printError(ErrorMessage message){
        System.out.println(message.getErrorMessage());
    }
}
