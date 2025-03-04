package service;

import Request_response.*;
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

    public CreateGameResult creategame(CreateGameRequest createGameRequest) throws ResponseException {
        if (authDAO.getAuthData(createGameRequest.authToken()) != null) {
            return new CreateGameResult(gameDAO.createGame(createGameRequest.gameName()));
        } else {
            throw new ResponseException(401, "Error: unauthorized");
        }
    }
}
