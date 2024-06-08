package ui;

public class PostloginUI extends BaseUI{

    static void help(){
        printHelpStatement("create <NAME>", "creates a game");
        printHelpStatement("list", "lists all games");
        printHelpStatement("join <ID> [WHITE|BLACK]", "join a game");
        printHelpStatement("observe <ID>", "passively watch game");
        printHelpStatement("logout", "terminate session");
        printHelpStatement("quit", "terminates program");
        printHelpStatement("help", "list commands and their descriptions");
    }

    static void logout(){
        //TODO
    }

    static void createGame(){
        //TODO
    }

    static void listGames(){
        //TODO
    }

    static void playGame(){
        //TODO
    }

    static void observeGame(){
        //Functionality to be added in phase 6
    }

}
