package server;

import dataaccess.*;
import handler.*;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        //Initialize Databases
        AuthDAO authDataBase = new MemoryAuthDAO();
        GameDAO gameDataBase = new MemoryGameDAO();
        UserDAO userDataBase = new MemoryUserDAO();
        Database database = new Database(authDataBase, gameDataBase, userDataBase);

        //Initialize Handlers
        RegisterHandler registerHandler = new RegisterHandler(database);
        LoginHandler loginHandler = new LoginHandler(database);
        LogoutHandler logoutHandler = new LogoutHandler(database);
        ClearHandler clearHandler = new ClearHandler(database);

        //switch case to handle all the different requests?
        Spark.post("/user", registerHandler::handleRequest);
        Spark.post("/session", loginHandler::handleRequest);
        Spark.delete("/session", logoutHandler::handleRequest);
        Spark.delete("/db", clearHandler::handleRequest);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
