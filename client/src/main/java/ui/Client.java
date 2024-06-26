package ui;

import java.util.Arrays;

public class Client {

    static volatile boolean awaitingLoadGameResponse = false;
    static volatile boolean awaitingNotificationResponse = false;

    public enum UIStatusType{
        PRELOGIN,
        POSTLOGIN,
        GAMEPLAY;
    };
    private UIStatusType clientStatus = UIStatusType.PRELOGIN;
    private PostloginUI postloginUI;
    private PreloginUI preloginUI;
    private GameplayUI gameplayUI;
    private ServerFacade serverFacade = new ServerFacade(8080);
    public Client (){
        this.preloginUI = new PreloginUI(serverFacade);
        this.postloginUI = new PostloginUI(serverFacade);
        this.gameplayUI = new GameplayUI(serverFacade);
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
                case "observe", "o" -> postloginUI.observeGame(parameters);
                case "logout" -> postloginUI.logout();
                default -> postloginUI.help();
            };
        }

        if(clientStatus == UIStatusType.GAMEPLAY){
            clientStatus = switch(command){
                case "redrawChessBoard", "r" -> gameplayUI.redrawChessBoard();
                case "leave" -> gameplayUI.leave();
                case "makeMove", "mm" -> gameplayUI.makeMove(parameters);
                case "resign" -> gameplayUI.resign();
                case "highlightLegalMoves", "h" -> gameplayUI.highlightLegalMoves(parameters);
                default -> gameplayUI.help();
            };
            while (awaitingLoadGameResponse | awaitingNotificationResponse) {
                Thread.onSpinWait();
            };
        }
    }
}
