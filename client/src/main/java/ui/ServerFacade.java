package ui;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import exceptions.ObserverException;
import intermediary.*;

public class ServerFacade {
    //will perform any logic / packaging for ClientToHttp calls
    private String authToken;
    private final HTTPCommunicator httpCommunicator;
    private WebSocketCommunicator webSocketCommunicator = null;
    private ChessGame.TeamColor color = null;
    private final int port;
    public ServerFacade(int port){
        this.port = port;
        this.httpCommunicator = new HTTPCommunicator(port);
    }

    //PRELOGIN
    public ResponseCodeAndObject register(String[] parameters){
        String body = new Gson().toJson(new RegisterRequest(parameters[0], parameters[1], parameters[2]));
        ResponseCodeAndObject response = httpCommunicator.executeHTTP("POST", "/user", body, null, RegisterResponse.class);
        if(response.responseCode() == 200) {
            RegisterResponse registerResponse = (RegisterResponse) response.responseObject();
            authToken = registerResponse.authToken();
        }
        return response;
    }

    public ResponseCodeAndObject login(String[] parameters){
        String body = new Gson().toJson(new LoginRequest(parameters[0], parameters[1]));
        ResponseCodeAndObject response = httpCommunicator.executeHTTP("POST", "/session", body, null, LoginResponse.class);
        if(response.responseCode() == 200) {
            LoginResponse loginResponse = (LoginResponse) response.responseObject();
            authToken = loginResponse.authToken();
        }
        return response;
    }

    //POSTLOGIN
    public ResponseCodeAndObject logout(){
        return httpCommunicator.executeHTTP("DELETE", "/session", null, authToken, null);
    }

    public ResponseCodeAndObject createGame(String[] parameters){
        String body = new Gson().toJson(new CreateGameRequest(parameters[0]));
        return httpCommunicator.executeHTTP("POST", "/game", body, authToken, CreateGameResponse.class);
    }

    public ResponseCodeAndObject joinGame(String[] parameters){
        int gameID = Integer.valueOf(parameters[1]);
        String body = new Gson().toJson(new JoinGameRequest(parameters[0], gameID));
        ResponseCodeAndObject response = httpCommunicator.executeHTTP("PUT", "/game", body, authToken, null);
        if(response.responseCode() == 200){
            if(parameters[0].equals("WHITE")){
                color = ChessGame.TeamColor.WHITE;
            }
            if(parameters[0].equals("BLACK")){
                color = ChessGame.TeamColor.BLACK;
            }
            connectThroughWebSocket(gameID);
        }
        return response;
    }

    public void observeGame(int gameID){
        connectThroughWebSocket(gameID);
    }

    public ResponseCodeAndObject listGames(){
        return httpCommunicator.executeHTTP("GET", "/game", null, authToken, ListGamesResponse.class);
    }

    //GAMEPLAY (no cool response types because it interacts utilizes the WebSocketNotifier on its response path)
    private void connectThroughWebSocket(int gameID){
        webSocketCommunicator = new WebSocketCommunicator(port, authToken, gameID, color);
        webSocketCommunicator.connect();
    }

    public void resign(){
        webSocketCommunicator.resign();
    }

    public void makeMove(ChessMove chessMove){
        webSocketCommunicator.makeMove(chessMove);
    }

    public void leave(){
        webSocketCommunicator.leave();
        webSocketCommunicator = null;
        color = null;
    }

    //misc methods
    public void clear(){
        httpCommunicator.executeHTTP("DELETE", "/db", null, null, null);
    }

    public ChessGame getUpdatedGame(){
        return webSocketCommunicator.getUpdatedGame();
    }

    public ChessGame.TeamColor getColor() throws ObserverException {
        if (color == null){
            throw new ObserverException();
        }
        return color;
    }

}
