package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import exception.ResponseException;

public class ClearService {
    AuthDAO authDAO;
    UserDAO userDAO;
    GameDAO gameDAO;
    public ClearService(AuthDAO authDAO, UserDAO userDAO, GameDAO gameDAO){
        this.authDAO=authDAO;
        this.userDAO=userDAO;
        this.gameDAO=gameDAO;
    }
    public void clear() throws ResponseException {
        authDAO.clearAuthData();
        userDAO.clearUserData();
        gameDAO.clearGames();
    }
}
