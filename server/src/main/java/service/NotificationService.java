package service;

import websocket.messages.ServerMessage;

import java.util.Set;

public class NotificationService{
    private Set<String> playerList;
    private final String player;
    NotificationService(Set<String> playerList, String player){
        this.playerList = playerList;
        this.player = player;
    }
    public void alertEveryone(ServerMessage servermessage){
        for(String iterPlayer: playerList){
            iterPlayer.send(serverMessageToJson(servermessage));
        }
    }

    public void alertSender(ServerMessage servermessage){
        player.send(serverMessageToJson(servermessage));
    }


    private String serverMessageToJson(ServerMessage serverMessage){
    }


}
