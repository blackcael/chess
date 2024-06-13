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

@WebSocket
public class Server {

    private Database database;

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        //Initialize Databases
        database = new Database();

        //Initialize Handlers (potential idea: make the handlers static? eliminate need for instantiation)
        RegisterHandler registerHandler = new RegisterHandler(database);
        LoginHandler loginHandler = new LoginHandler(database);
        LogoutHandler logoutHandler = new LogoutHandler(database);
        ListGamesHandler listGamesHandler = new ListGamesHandler(database);
        CreateGameHandler createGameHandler = new CreateGameHandler(database);
        JoinGameHandler joinGameHandler = new JoinGameHandler(database);
        ClearHandler clearHandler = new ClearHandler(database);

        //Spark Vanilla HTTP
        Spark.post("/user", (req, res) -> registerHandler.handleRequest(req, res, RegisterRequest.class));
        Spark.post("/session", (req, res) -> loginHandler.handleRequest(req, res, LoginRequest.class));
        Spark.delete("/session", (req, res) -> logoutHandler.handleRequest(req, res, Object.class));
        Spark.get("/game", (req, res) -> listGamesHandler.handleRequest(req, res, String.class));
        Spark.post("/game", (req, res) -> createGameHandler.handleRequest(req, res, CreateGameRequest.class));
        Spark.put("/game", (req, res) -> joinGameHandler.handleRequest(req, res, JoinGameRequest.class));
        Spark.delete("/db", clearHandler::handleRequest);

        //Spark Websocketing
        Spark.webSocket("/ws", Server.class);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception{
        System.out.printf("received: %s", message);
        String response = WebSocketMessageHandler.handleMessage(database, message);
        session.getRemote().sendString("WebSocket response: " + response);
    }
}
