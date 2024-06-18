package ui;

import chess.ChessGame;
import websocket.messages.*;

import static ui.Client.awaitingLoadGameResponse;
import static ui.Client.awaitingNotificationResponse;

public class WebSocketNotifier {

    public synchronized static void notify(ServerMessage serverMessage, WebSocketCommunicator parent, ChessGame.TeamColor color){
        switch(serverMessage.getServerMessageType()){
            case ServerMessage.ServerMessageType.NOTIFICATION -> printNotification((NotificationMessage)serverMessage);
            case ServerMessage.ServerMessageType.ERROR -> printError((ErrorMessage)serverMessage);
            case ServerMessage.ServerMessageType.LOAD_GAME -> loadGame((LoadGameMessage)serverMessage, color, parent);
        }
    }

    //WE MAY NEED TO DO SOME COOL SYNCHRONIZED BITS HERE

    private static void loadGame(LoadGameMessage message, ChessGame.TeamColor teamColor, WebSocketCommunicator parent){
        if (teamColor == null){
            teamColor = ChessGame.TeamColor.WHITE;  //INSTEAD WE COULD SIMPLY HERE DRAW BOTH BOARDS?
        }
        parent.setUpdatedGame(message.getGame());
        BoardPrinter.drawBoard(message.getGame().getBoard(), teamColor, null, null);
        awaitingLoadGameResponse = false;
    }

    private static void printNotification(NotificationMessage message){
        System.out.println(message.getMessage());
        awaitingNotificationResponse = false;
    }

    private static void printError(ErrorMessage message){
        System.out.println(message.getErrorMessage());
        awaitingLoadGameResponse = false;
        awaitingNotificationResponse = false;
    }
}
