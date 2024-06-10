package ui;

import java.util.Arrays;

public class Client {
    public enum UIStatusType{
        PRELOGIN,
        POSTLOGIN,
        GAMEPLAY;
    };
    private UIStatusType clientStatus = UIStatusType.PRELOGIN;
    private PostloginUI postloginUI;
    private PreloginUI preloginUI;
    private ServerFacade serverFacade;
    public Client (){
        this.preloginUI = new PreloginUI(serverFacade);
        this.postloginUI = new PostloginUI(serverFacade);
    };

    public UIStatusType getClientStatus() {
        return clientStatus;
    }

    public void processArguments(String[] arguments){
        String command = arguments[0];
        String[] parameters = Arrays.copyOfRange(arguments, 1, arguments.length);
        if(clientStatus == UIStatusType.PRELOGIN) { //right type of equals?
            clientStatus = switch(command){
                case "login" -> preloginUI.login(parameters);
                case "register" -> preloginUI.register(parameters);
                default -> preloginUI.help();
            };

        }
        if(clientStatus == UIStatusType.POSTLOGIN){
            clientStatus = switch(command){
                case "create" -> postloginUI.createGame(parameters);
                case "list" -> postloginUI.listGames();
                case "join" -> postloginUI.joinGame(parameters);
                case "observe" -> postloginUI.observeGame(parameters);
                case "logout" -> postloginUI.logout();
                default -> postloginUI.help();
            };
        }
    }
}
