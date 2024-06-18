package ui;

import chess.ChessGame;
import chess.ChessMove;
import websocket.WebSocketSerializer;
import websocket.commands.*;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static ui.Client.awaitingLoadGameResponse;
import static ui.Client.awaitingNotificationResponse;




public class WebSocketCommunicator extends Endpoint{

    public Session session;
    private final ChessGame.TeamColor teamColor = null;
    private int gameID;
    private String authToken;
    private ChessGame game;
    private ChessGame.TeamColor color;

    public WebSocketCommunicator(int port, String authToken, int gameID, ChessGame.TeamColor color){
        this.authToken = authToken;
        this.gameID = gameID;
        this.color = color;

        URI uri = null;
        try {
            uri = new URI("ws://localhost:" + Integer.toString(port) + "/ws");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            this.session = container.connectToServer(this, uri);
        } catch (DeploymentException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //declare receiver
        WebSocketCommunicator parent = this;
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message){
                ServerMessage serverMessage = WebSocketSerializer.jsonToServerMessage(message);
                WebSocketNotifier.notify(serverMessage, parent, color);
            }
        });
    }
    public ChessGame getUpdatedGame(){
        return game;
    }
    public void setUpdatedGame(ChessGame game){
        this.game = game;
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    //executables
    public void makeMove(ChessMove chessMove){
        awaitingLoadGameResponse = true;
        send(new MakeMoveCommand(authToken, gameID, chessMove));
    }

    public void leave(){
        send(new LeaveCommand(authToken, gameID));
    }

    public void resign(){
        awaitingNotificationResponse = true;
        send(new ResignCommand(authToken, gameID));
    }

    public void connect(){
        awaitingLoadGameResponse = true;
        send(new ConnectCommand(authToken, gameID));
    }


    private void send(UserGameCommand userGameCommand){
        try {
            this.session.getBasicRemote().sendText(WebSocketSerializer.userGameCommandToJson(userGameCommand));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
