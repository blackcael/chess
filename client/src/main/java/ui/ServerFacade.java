package ui;

import com.google.gson.Gson;
import intermediary.*;

import java.lang.reflect.Type;

public class ServerFacade {
    //will perform any logic / packaging for ClientToHttp calls
    private String authToken;
    public ServerFacade(){
    }

    public String register(String[] parameters){
        String body = new Gson().toJson(new RegisterRequest(parameters[0], parameters[1], parameters[2]));
        String response = HTTPCalls.executeHTTP("POST", "/user", body, null);
        RegisterResponse registerResponse = new Gson().fromJson(response, RegisterResponse.class);
        authToken = registerResponse.authToken();
        return "good job ur registered dawg";
    }

    public String login(String[] parameters){
        String body = new Gson().toJson(new LoginRequest(parameters[0], parameters[2]));
        String response = HTTPCalls.executeHTTP("POST", "/session", body, null);
        LoginResponse loginResponse = new Gson().fromJson(response, LoginResponse.class);
        authToken = loginResponse.authToken();
        return "logged in dawg";
    }


    public String logout(){
        String response = HTTPCalls.executeHTTP("DELETE", "/session", null, authToken);
        return "if no errors, we ballin out, dawg";
    }

    public String createGame(String[] parameters){
        String body = new Gson().toJson(new CreateGameRequest(parameters[0]));
        String response = HTTPCalls.executeHTTP("POST", "/game", body, authToken);
        CreateGameResponse createGameResponse = new Gson().fromJson(response, CreateGameResponse.class);
        return "gameCreated dawg";
    }

    public String joinGame(String[] parameters){
        String body = new Gson().toJson(new JoinGameRequest(parameters[0], Integer.valueOf(parameters[1])));
        String response = HTTPCalls.executeHTTP("PUT", "/game", body, authToken);
        return "if no errors, we playin chess, dawg";
    }

    public String listGames(){
        String response = HTTPCalls.executeHTTP("GET", "/game", null, authToken);
        ListGamesResponse listGamesResponse = new Gson().fromJson(response, ListGamesResponse.class);
        return "list of games:";
    }


}
