package handler;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;


public class WebSocketConnection {
    private String username;
    private Session session;

    public WebSocketConnection(String username, Session session){
        this.username = username;
        this.session = session;
    }

    public String getUsername(){
        return username;
    }

    public Session getSession(){
        return session;
    }

    public void send(String msg) throws IOException {
        session.getRemote().sendString(msg);
    }

}
