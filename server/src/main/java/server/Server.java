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

        //Initialize Handlers (potential idea: make the handlers static? eliminate need for instantiation)
        RegisterHandler registerHandler = new RegisterHandler(database);
        LoginHandler loginHandler = new LoginHandler(database);
        LogoutHandler logoutHandler = new LogoutHandler(database);
        ListGamesHandler listGamesHandler = new ListGamesHandler(database);
        CreateGameHandler createGameHandler = new CreateGameHandler(database);
        JoinGameHandler joinGameHandler = new JoinGameHandler(database);
        ClearHandler clearHandler = new ClearHandler(database);

        //switch case to handle all the different requests?
        Spark.post("/user", registerHandler::handleRequest);
        Spark.post("/session", loginHandler::handleRequest);
        Spark.delete("/session", logoutHandler::handleRequest);
        Spark.get("/game", listGamesHandler::handleRequest);
        Spark.post("/game", createGameHandler::handleRequest);
        Spark.put("/game", joinGameHandler::handleRequest);
        Spark.delete("/db", clearHandler::handleRequest);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
