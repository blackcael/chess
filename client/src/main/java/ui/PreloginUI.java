package ui;

import java.util.Scanner;

public class PreloginUI extends BaseUI{

    static void help(){
        printHelpStatement("register, <USERNAME> <PASSWORD> <EMAIL>", "to create an new user");
        printHelpStatement("login <USERNAME> <PASSWORD>", "login a registered user");
        printHelpStatement("quit", "exits the program");
        printHelpStatement("help", "list commands and their descriptions");
    }

    static void quit(){
        //TODO
    }

    static void login(String [] arguments){

    }

    static void register(){
        //TODO
    }
}
