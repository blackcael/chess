package ui;

import javax.websocket.*;
import java.net.URI;
import java.util.Scanner;

public class WebsocketCommunicator extends Endpoint{

    public Session session;

    public WebsocketCommunicator(int port) throws Exception{
        URI uri = new URI("ws://localhost:" + Integer.toString(port) + "/ws");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message){
                System.out.println(message);
            }
        });
    }

    public void send(String message) throws Exception{
        this.session.getBasicRemote().sendText(message);
    }
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}
