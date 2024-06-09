package ui;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class ServerFacade {
    //will perform any logic / packaging for ClientToHttp calls
    private String authToken;
    public ServerFacade(){
    }

    private record registerRequest(String username, String password, String email){}
    public String register(String[] parameters){
        String msg = new Gson().toJson(new registerRequest(parameters[0], parameters[1], parameters[2]));
        var response =  HTTPCalls.executeHTTP("GET", msg, authToken);
        return response;
    }

    private record loginRequest(String username, String password){}
    public String login(String[] parameters){

    }

    public String logout(){

    }

    public String createGame(){

    }

    public String joinGame(){

    }

    public String listGames(){

    }
}
