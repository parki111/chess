package service;

import model.GameData;
import requestresponse.*;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
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

    public void joinGame(JoinGameRequest joinGameRequest) throws ResponseException{
        if (authDAO.getAuthData(joinGameRequest.authToken()) != null) {
            GameData game=gameDAO.findGame(joinGameRequest.gameID());
            if(game!=null){
                gameDAO.updateGame(authDAO.getAuthData(joinGameRequest.authToken()).username(), joinGameRequest.playerColor(),game);
            }
            else{
                throw new ResponseException(400,"Error: bad request");
            }

        } else {
            throw new ResponseException(401, "Error: unauthorized");
        }
    }
}
