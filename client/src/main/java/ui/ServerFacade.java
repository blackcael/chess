package ui;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import intermediary.*;

import java.lang.reflect.Type;

public class ServerFacade {
    //will perform any logic / packaging for ClientToHttp calls
    private String authToken;
    private final HTTPCommunicator httpCommunicator;
    private final WebSocketCommunicator webSocketCommunicator;
    private ChessGame.TeamColor color = null;
    public ServerFacade(int port){
        this.httpCommunicator = new HTTPCommunicator(port);
        this.webSocketCommunicator = new WebSocketCommunicator(port);
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
        String body = new Gson().toJson(new JoinGameRequest(parameters[0], Integer.valueOf(parameters[1])));
        ResponseCodeAndObject response = httpCommunicator.executeHTTP("PUT", "/game", body, authToken, null);
        if(response.responseCode() == 200){
            if(parameters[0].equals("WHITE")){
                color = ChessGame.TeamColor.WHITE;
            }
            if(parameters[0].equals("BLACK")){
                color = ChessGame.TeamColor.BLACK;
            }
        }
        return response;
    }

    public ResponseCodeAndObject listGames(){
        return httpCommunicator.executeHTTP("GET", "/game", null, authToken, ListGamesResponse.class);
    }

    //GAMEPLAY (no cool response types because it interacts utilizes the WebSocketNotifier on its response path)
    public void resign(){
        //TODO implement
    }

    public void makeMove(ChessMove chessMove){
        //TODO implement
    }

    public void leave(){
        //TODO implement
    }




    //misc methods
    public void clear(){
        httpCommunicator.executeHTTP("DELETE", "/db", null, null, null);
    }

    public ChessGame.TeamColor getColor(){
        if (color == null){
            throw new RuntimeException("Cannot getColor if color is not assigned");
        }
        return color;
    }

}
