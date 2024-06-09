package ui;

public class PreloginUI extends BaseUI{

    public PreloginUI(ServerFacade serverFacade){};
    public void help(){
        printHelpStatement("register, <USERNAME> <PASSWORD> <EMAIL>", "to create an new user");
        printHelpStatement("login <USERNAME> <PASSWORD>", "login a registered user");
        printHelpStatement("quit", "exits the program");
        printHelpStatement("help", "list commands and their descriptions");
    }

    public void login(String [] parameters){
        String username = parameters[0];
        String password = parameters[1];
    }

    public void register(String[] parameters){
        String username = parameters[0];
        String password = parameters[1];
        String email = parameters[2];
    }
}
