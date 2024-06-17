package ui;

import websocket.WebSocketSerializer;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketCommunicator extends Endpoint{

    public Session session;

    public WebSocketCommunicator(int port){
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
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message){
                ServerMessage serverMessage = WebSocketSerializer.jsonToServerMessage(message);
                WebSocketNotifier.notify(serverMessage);
            }
        });
    }

    private void send(UserGameCommand userGameCommand) throws Exception{
        this.session.getBasicRemote().sendText(WebSocketSerializer.userGameCommandToJson(userGameCommand));
    }
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

}
