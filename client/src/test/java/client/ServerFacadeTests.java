package client;

import intermediary.*;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ServerFacadeTests {

    private static ServerFacade serverFacade;
    private static Server server;
    private final String[] registerParameters = {"cblack1", "p@ssword", "cail@mail.com"};
    private final String[] loginParameters = {registerParameters[0], registerParameters[1]};
    private final String[] createGameParameters = {"1v1MeBro"};

    @BeforeEach
    public void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new ServerFacade(port);
        serverFacade.clear();
    }

    @AfterEach
    public void stopServer() {
        server.stop();
    }

    @Test
    public void registerTestPositive() {
        ResponseCodeAndObject rcao = serverFacade.register(registerParameters);
        RegisterResponse registerResponse = (RegisterResponse) rcao.responseObject();
        assertEquals(registerResponse.username(), registerParameters[0]);
        assertEquals(200, rcao.responseCode());

    }

    @Test
    public void registerTestNegative() {
        ResponseCodeAndObject rcao = serverFacade.register(registerParameters);
        ResponseCodeAndObject rcao2 = serverFacade.register(registerParameters);
        assertEquals(403, rcao2.responseCode());
    }

    @Test
    public void loginTestPositive(){
        serverFacade.register(registerParameters);
        ResponseCodeAndObject rcao = serverFacade.login(registerParameters);
        LoginResponse loginResponse = (LoginResponse) rcao.responseObject();
        assertEquals(loginResponse.username(), loginParameters[0]);
        assertEquals(200, rcao.responseCode());
    }

    @Test
    public void loginTestNegative(){
        ResponseCodeAndObject rcao = serverFacade.login(registerParameters);
        assertEquals(401, rcao.responseCode());
    }

    @Test
    public void logoutTestPositive(){
        generateValidAuthToken();
        ResponseCodeAndObject rcao = serverFacade.logout();
        assertEquals(200, rcao.responseCode());
    }

    @Test
    public void logoutTestNegative(){
        ResponseCodeAndObject rcao = serverFacade.logout();
        assertEquals(401, rcao.responseCode());
    }

    @Test
    public void createGamePositive(){
        generateValidAuthToken();
        ResponseCodeAndObject rcao = serverFacade.createGame(createGameParameters);
        assertEquals(200, rcao.responseCode());
    }

    @Test
    public void createGameNegative(){
        ResponseCodeAndObject rcao = serverFacade.createGame(createGameParameters);
        assertEquals(401, rcao.responseCode());
        generateValidAuthToken();
        ResponseCodeAndObject rcao1 = serverFacade.createGame(createGameParameters);
        ResponseCodeAndObject rcao2 = serverFacade.createGame(createGameParameters);
        assertEquals(400, rcao2.responseCode());
    }

    @Test
    public void joinGamePositive(){
        //setup
        generateValidAuthToken();
        ResponseCodeAndObject prcao = serverFacade.createGame(createGameParameters);
        CreateGameResponse createGameResponse = (CreateGameResponse) prcao.responseObject();
        String gameID = Integer.toString(createGameResponse.gameID());

        String[] joinGameParameters = {"WHITE", gameID};
        ResponseCodeAndObject rcao = serverFacade.joinGame(joinGameParameters);
        System.out.println(rcao.toString());
        assertEquals(200, rcao.responseCode());

    }

    @Test
    public void joinGameNegative(){
        //setup
        generateValidAuthToken();
        ResponseCodeAndObject prcao = serverFacade.createGame(createGameParameters);
        CreateGameResponse createGameResponse = (CreateGameResponse) prcao.responseObject();
        String gameID = Integer.toString(createGameResponse.gameID());

        String[] joinGameParameters = {"WHITE", "91"};
        ResponseCodeAndObject rcao = serverFacade.joinGame(joinGameParameters);
        assertEquals(400, rcao.responseCode());

    }

    @Test
    public void listGamesPositive(){
        generateListOfValidGames();
        ResponseCodeAndObject rcao = serverFacade.listGames();
        System.out.println(rcao.toString());
        assertEquals(200, rcao.responseCode());
    }

    @Test
    public void listGamesNegative(){
        //? not really possible to create a good test for this...
    }

    @Test
    public void clearDataBase(){
        serverFacade.clear();
    }

    private static void generateValidAuthToken(){
        String[] parameters = {"validUsername", "p@ssword", "cail@mail.com"};
        ResponseCodeAndObject rcao = serverFacade.register(parameters);
        RegisterResponse registerResponse = (RegisterResponse) rcao.responseObject();
    }

    private static void generateListOfValidGames(){
        generateValidAuthToken();
        String[] gameNames = {"game1", "game2", "game3"};
        for (String gameName : gameNames){
            String [] gameNameParameter = {gameName};
            serverFacade.createGame(gameNameParameter);
        }
    }

}
