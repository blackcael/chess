package ui;

public class PreloginUI extends BaseUI{
    public PreloginUI(ServerFacade serverFacade){
        super(serverFacade);
    }
    public void help(){
        printHelpStatement("register, <USERNAME> <PASSWORD> <EMAIL>", "to create an new user");
        printHelpStatement("login <USERNAME> <PASSWORD>", "login a registered user");
        printHelpStatement("quit", "exits the program");
        printHelpStatement("help", "list commands and their descriptions");
    }

    public void login(String [] parameters){
        //verify that we have 2 strings?
        String response = serverFacade.login(parameters);
    }

    public void register(String[] parameters){
        //verify that we have 3 strings?
        String response = serverFacade.register(parameters);
    }
}
