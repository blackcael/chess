package ui;

public class BaseUI {
    protected ServerFacade serverFacade;
    protected BaseUI(ServerFacade serverFacade){
        this.serverFacade = serverFacade;
    }

    private static final String SET_STANDARD_TEXT_COLOR = EscapeSequences.SET_BG_COLOR_WHITE;
    private static final String SET_HELP_TEXT_COLOR = EscapeSequences.SET_TEXT_COLOR_BLUE;

    protected static void printHelpStatement(String helpStatement, String description){
        System.out.println(SET_HELP_TEXT_COLOR + helpStatement + SET_STANDARD_TEXT_COLOR + " - " + description);
    }
}
