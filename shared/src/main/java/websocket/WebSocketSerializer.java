package websocket;

import com.google.gson.Gson;
import intermediary.ResponseCodeAndObject;
import websocket.commands.*;
import websocket.messages.*;

import java.lang.reflect.Type;

public class WebSocketSerializer {
    private record UserGameCommandIntermediary(UserGameCommand.CommandType commandType, String jsonString) {
    }

    private record ServerMessageIntermediary(ServerMessage.ServerMessageType messageType, String jsonString) {
    }

    static public String userGameCommandToJson(UserGameCommand userGameCommand) {
//        String serializedObject = new Gson().toJson(userGameCommand);
//        UserGameCommandIntermediary ugci = new UserGameCommandIntermediary(userGameCommand.getCommandType(), serializedObject);
//        return new Gson().toJson(ugci);
        return new Gson().toJson(userGameCommand);
    }

    static public UserGameCommand jsonToUserCommand(String jsonString) {
        UserGameCommandIntermediary ugci = new Gson().fromJson(jsonString, UserGameCommandIntermediary.class);
        return switch (ugci.commandType()) {
            case UserGameCommand.CommandType.CONNECT -> new Gson().fromJson(jsonString, ConnectCommand.class);
            case UserGameCommand.CommandType.LEAVE -> new Gson().fromJson(jsonString, LeaveCommand.class);
            case UserGameCommand.CommandType.MAKE_MOVE -> new Gson().fromJson(jsonString, MakeMoveCommand.class);
            case UserGameCommand.CommandType.RESIGN -> new Gson().fromJson(jsonString, ResignCommand.class);
        };
    }

    static public String serverMessageToJson(ServerMessage serverMessage) {
//        String serializedObject = new Gson().toJson(serverMessage);
//        ServerMessageIntermediary smi = new ServerMessageIntermediary(serverMessage.getServerMessageType(), serializedObject);
//        return new Gson().toJson(smi);
        return new Gson().toJson(serverMessage);
    }

    static public ServerMessage jsonToServerMessage(String jsonString) {
        ServerMessageIntermediary smi = new Gson().fromJson(jsonString, ServerMessageIntermediary.class);
        return switch (smi.messageType()) {
            case ServerMessage.ServerMessageType.ERROR -> new Gson().fromJson(jsonString, ErrorMessage.class);
            case ServerMessage.ServerMessageType.LOAD_GAME -> new Gson().fromJson(jsonString, LoadGameMessage.class);
            case ServerMessage.ServerMessageType.NOTIFICATION -> new Gson().fromJson(jsonString, NotificationMessage.class);
        };
    }
}

