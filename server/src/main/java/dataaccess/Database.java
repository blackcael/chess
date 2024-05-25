package dataaccess;

public class Database {
    public AuthDAO authDataBase;
    public GameDAO gameDataBase;
    public UserDAO userDataBase;
    public Database(AuthDAO authDataBase, GameDAO gameDataBase, UserDAO userDataBase){
        this.authDataBase = authDataBase;
        this.gameDataBase = gameDataBase;
        this.userDataBase = userDataBase;
    }
}
