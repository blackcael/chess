package ui;

public class PostloginUI extends BaseUI{
    public PostloginUI(ServerFacade serverFacade){
        super(serverFacade);
    };

    public void help(){
        printHelpStatement("create <NAME>", "creates a game");
        printHelpStatement("list", "lists all games");
        printHelpStatement("join <ID> [WHITE|BLACK]", "join a game");
        printHelpStatement("observe <ID>", "passively watch game");
        printHelpStatement("logout", "terminate session");
        printHelpStatement("quit", "terminates program");
        printHelpStatement("help", "list commands and their descriptions");
    }

    public void logout(){
        //TODO
    }

    public void createGame(String[] params){
        String gameName = params[0];
    }

    public void listGames(){
        //TODO
    }

    public void joinGame(String[] params){
        String gameID = params[0];
    }

    public void observeGame(String[] params){
        String gameID = params[0];
    }

}
