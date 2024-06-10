package ui;

import com.google.gson.Gson;
import intermediary.*;

import java.lang.reflect.Type;

public class ServerFacade {
    //will perform any logic / packaging for ClientToHttp calls
    private String authToken;
    private final HTTPCommunicator httpCommunicator;
    public ServerFacade(int port){
        this.httpCommunicator = new HTTPCommunicator(port);
    }

    public String register(String[] parameters){
        String body = new Gson().toJson(new RegisterRequest(parameters[0], parameters[1], parameters[2]));
        ResponseCodeAndObject response = httpCommunicator.executeHTTP("POST", "/user", body, null, RegisterResponse.class);
        RegisterResponse registerResponse = (RegisterResponse) response.responseObject();
        authToken = registerResponse.authToken();
        return response.toString();
    }

    public String login(String[] parameters){
        String body = new Gson().toJson(new LoginRequest(parameters[0], parameters[1]));
        ResponseCodeAndObject response = httpCommunicator.executeHTTP("POST", "/session", body, null, LoginResponse.class);
        LoginResponse loginResponse = (LoginResponse) response.responseObject();
        authToken = loginResponse.authToken();
        return response.toString();
    }


    public String logout(){
        ResponseCodeAndObject response = httpCommunicator.executeHTTP("DELETE", "/session", null, authToken, LogoutResponse.class);
        return response.toString();
    }

    public String createGame(String[] parameters){
        String body = new Gson().toJson(new CreateGameRequest(parameters[0]));
        ResponseCodeAndObject response = httpCommunicator.executeHTTP("POST", "/game", body, authToken, CreateGameResponse.class);
        return "gameCreated dawg";
    }

    public String joinGame(String[] parameters){
        String body = new Gson().toJson(new JoinGameRequest(parameters[0], Integer.valueOf(parameters[1])));
        ResponseCodeAndObject response = httpCommunicator.executeHTTP("PUT", "/game", body, authToken, null);
        return "if no errors, we playin chess, dawg";
    }

    public String listGames(){
        ResponseCodeAndObject response = httpCommunicator.executeHTTP("GET", "/game", null, authToken, ListGamesResponse.class);
        return "list of games:";
    }

}
