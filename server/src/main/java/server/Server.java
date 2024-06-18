package server;

import dataaccess.*;
import handler.*;
import intermediary.CreateGameRequest;
import intermediary.JoinGameRequest;
import intermediary.LoginRequest;
import intermediary.RegisterRequest;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import spark.Spark;

public class Server {

    private Database database;
    private WebSocketHandler webSocketHandler;


    public int run(int desiredPort) {
        //Initialize Databases
        database = new Database();
        webSocketHandler = new WebSocketHandler(database);

        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        //Initialize Handlers (potential idea: make the handlers static? eliminate need for instantiation)
        RegisterHandler registerHandler = new RegisterHandler(database);
        LoginHandler loginHandler = new LoginHandler(database);
        LogoutHandler logoutHandler = new LogoutHandler(database);
        ListGamesHandler listGamesHandler = new ListGamesHandler(database);
        CreateGameHandler createGameHandler = new CreateGameHandler(database);
        JoinGameHandler joinGameHandler = new JoinGameHandler(database);
        ClearHandler clearHandler = new ClearHandler(database);

        //Spark Websocketing
        Spark.webSocket("/ws", webSocketHandler);

        //Spark Vanilla HTTP
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
