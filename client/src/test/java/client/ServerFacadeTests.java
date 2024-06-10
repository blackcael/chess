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

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new ServerFacade(port);
    }

    @AfterAll
    static void stopServer() {
        serverFacade.clear();
        server.stop();
    }

    @Test
    public void registerTestPositive() {
        ResponseCodeAndObject rcao = serverFacade.register(registerParameters);
        RegisterResponse registerResponse = (RegisterResponse) rcao.responseObject();
        assertEquals(registerResponse.username(), registerParameters[0]);
    }

    @Test
    public void registerTestNegative() {
        ResponseCodeAndObject rcao = serverFacade.register(registerParameters);
        ResponseCodeAndObject rcao2 = serverFacade.register(registerParameters);
        assertTrue(rcao2.responseCode() != 200);
    }

    @Test
    public void loginTestPositive(){
        serverFacade.register(registerParameters);
        ResponseCodeAndObject rcao = serverFacade.login(registerParameters);
        LoginResponse loginResponse = (LoginResponse) rcao.responseObject();
        assertEquals(loginResponse.username(), loginParameters[0]);
        assertEquals(rcao.responseCode(), 200);
    }

    @Test
    public void loginTestNegative(){
        ResponseCodeAndObject rcao = serverFacade.login(registerParameters);
        assertTrue(rcao.responseCode() != 200);
    }

    @Test
    public void logoutTestPositive(){
        generateValidAuthToken();
        ResponseCodeAndObject rcao = serverFacade.logout();
        assertEquals(rcao.responseCode(), 200);
    }

    @Test
    public void logoutTestNegative(){
        ResponseCodeAndObject rcao = serverFacade.logout();
        assertTrue(rcao.responseCode() != 200);
    }

    @Test
    public void createGamePositive(){
        generateValidAuthToken();
        ResponseCodeAndObject rcao = serverFacade.createGame(createGameParameters);

    }

    @Test
    public void createGameNegative(){

    }

    @Test
    public void joinGamePositive(){

    }

    @Test
    public void joinGameNegative(){

    }

    @Test
    public void listGamesPositive(){

    }

    @Test
    public void listGamesNegative(){

    }

    private static String generateValidAuthToken(){
        String[] parameters = {"validUsername", "p@ssword", "cail@mail.com"};
        ResponseCodeAndObject rcao = serverFacade.register(parameters);
        RegisterResponse registerResponse = (RegisterResponse) rcao.responseObject();
        return registerResponse.authToken();
    }


}
