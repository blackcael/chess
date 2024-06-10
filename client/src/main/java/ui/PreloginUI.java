package ui;

import intermediary.ResponseCodeAndObject;

public class PreloginUI extends BaseUI{
    public PreloginUI(ServerFacade serverFacade){
        super(serverFacade);
    }
    public Client.UIStatusType help(){
        printHelpStatement("register, <USERNAME> <PASSWORD> <EMAIL>", "to create an new user");
        printHelpStatement("login <USERNAME> <PASSWORD>", "login a registered user");
        printHelpStatement("quit", "exits the program");
        printHelpStatement("help", "list commands and their descriptions");
        return Client.UIStatusType.PRELOGIN;
    }

    public Client.UIStatusType register(String[] parameters){
        //verify that we have 3 strings?
        ResponseCodeAndObject response = serverFacade.register(parameters);
        if (response.responseCode() == 200){
            System.out.println("Registration Accepted");
            return Client.UIStatusType.POSTLOGIN;
        }else{
            System.out.println(response.responseObject());
            System.out.println("Registration invalid");
            return Client.UIStatusType.PRELOGIN;
        }
    }

    public Client.UIStatusType login(String[] parameters){
        //verify that we have 2 strings?
        ResponseCodeAndObject response = serverFacade.login(parameters);
        if (response.responseCode() == 200){
            System.out.println("Login Information Accepted");
            return Client.UIStatusType.POSTLOGIN;
        }else{
            System.out.println(response.responseObject());
            System.out.println("Login Information Invalid");
            return Client.UIStatusType.PRELOGIN;
        }
    }
}
