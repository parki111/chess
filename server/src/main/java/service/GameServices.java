package service;

import Request_response.ListGamesRequest;
import Request_response.ListGamesResult;
import Request_response.LogoutRequest;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import exception.ResponseException;

public class GameServices {
    AuthDAO authDAO;
    GameDAO gameDAO;
    public GameServices(AuthDAO authDAO, GameDAO gameDAO){
        this.authDAO=authDAO;
        this.gameDAO=gameDAO;
    }

    public ListGamesResult listgames(ListGamesRequest listGamesRequest) throws ResponseException {
        if (authDAO.getAuthData(listGamesRequest.authToken()) != null) {
            return new ListGamesResult(gameDAO.listGames());
        } else {
            throw new ResponseException(401, "Error: unauthorized");
        }
    }
}
