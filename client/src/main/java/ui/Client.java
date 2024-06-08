package ui;

public class Client {
    public enum UIStatusType{
        PRELOGIN,
        POSTLOGIN,
        GAMEPLAY;
    };
    private UIStatusType uiStatus = UIStatusType.PRELOGIN;
    public Client (){
    };

    public UIStatusType getUiStatus() {
        return uiStatus;
    }

    public void setUiStatus(UIStatusType uiStatus) {
        this.uiStatus = uiStatus;
    }

    public void processArguments(String[] arguments){
        String command = arguments[0];
        if(uiStatus == UIStatusType.PRELOGIN) { //right type of equals?
            //switch case
        }
        if(uiStatus == UIStatusType.POSTLOGIN){
            //switch case
        }
    }
}
