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


    public ResponseCodeAndObject logout(){
        ResponseCodeAndObject response = httpCommunicator.executeHTTP("DELETE", "/session", null, authToken, null);
        return response;
    }

    public ResponseCodeAndObject createGame(String[] parameters){
        String body = new Gson().toJson(new CreateGameRequest(parameters[0]));
        ResponseCodeAndObject response = httpCommunicator.executeHTTP("POST", "/game", body, authToken, CreateGameResponse.class);
        return response;
    }

    public ResponseCodeAndObject joinGame(String[] parameters){
        String body = new Gson().toJson(new JoinGameRequest(parameters[0], Integer.valueOf(parameters[1])));
        ResponseCodeAndObject response = httpCommunicator.executeHTTP("PUT", "/game", body, authToken, null);
        return response;
    }

    public ResponseCodeAndObject listGames(){
        ResponseCodeAndObject response = httpCommunicator.executeHTTP("GET", "/game", null, authToken, ListGamesResponse.class);
        return response;
    }

    public void clear(){
        ResponseCodeAndObject response = httpCommunicator.executeHTTP("DELETE", "/db", null, null, null);
    }

}
