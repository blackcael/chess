package server;

import dataaccess.*;
import handler.*;
import intermediary.CreateGameRequest;
import intermediary.JoinGameRequest;
import intermediary.LoginRequest;
import intermediary.RegisterRequest;
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

        //Spark -> handleRequest?  ***Note, is run always looping? If so, that will create a problem with database...
        //note, i believe this initializes the "routes".
        Spark.post("/user", (req, res) -> registerHandler.handleRequest(req, res, RegisterRequest.class));
        Spark.post("/session", (req, res) -> loginHandler.handleRequest(req, res, LoginRequest.class));
        Spark.delete("/session", (req, res) -> logoutHandler.handleRequest(req, res, Object.class));
        Spark.get("/game", (req, res) -> listGamesHandler.handleRequest(req, res, String.class));
        Spark.post("/game", (req, res) -> createGameHandler.handleRequest(req, res, CreateGameRequest.class));
        Spark.put("/game", (req, res) -> joinGameHandler.handleRequest(req, res, JoinGameRequest.class));
        Spark.delete("/db", clearHandler::handleRequest);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
