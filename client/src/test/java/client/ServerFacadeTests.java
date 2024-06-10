package client;

import intermediary.RegisterRequest;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;


public class ServerFacadeTests {

    private static ServerFacade serverFacade;
    private static Server server;
    private final String[] registerParameters = {"cblack2", "p@ssword", "cail@mail.com"};

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new ServerFacade(port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void registerTestPositive() {
        System.out.println(serverFacade.register(registerParameters));
    }

}
