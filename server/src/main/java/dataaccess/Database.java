package dataaccess;

public class Database {
    public AuthDAO authDataBase;
    public GameDAO gameDataBase;
    public UserDAO userDataBase;
    private int gameIDCounter = 0;
    public Database(AuthDAO authDataBase, GameDAO gameDataBase, UserDAO userDataBase){
        this.authDataBase = authDataBase;
        this.gameDataBase = gameDataBase;
        this.userDataBase = userDataBase;
    }
    public int getGameGenID(){
        return gameIDCounter++;
    }

    public boolean isEmpty(){
        return(authDataBase.isEmpty() && gameDataBase.isEmpty() && userDataBase.isEmpty());
    }
}
