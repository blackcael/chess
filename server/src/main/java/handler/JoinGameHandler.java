package handler;

import dataaccess.Database;
import intermediary.JoinGameRequest;
import service.JoinGameService;


public class JoinGameHandler extends BaseHandler{
    public JoinGameHandler(Database database) {
        super(database);
    }
    //2.
    @Override
    protected Object service(String authToken, Object joinGameRequest) throws Exception {
        JoinGameService joinGameService = new JoinGameService(database);
        joinGameService.joinGame(authToken, (JoinGameRequest) joinGameRequest);
        return null;
    }

}
