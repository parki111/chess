package dataaccess;

import chess.ChessGame;

import dataaccess.sqldataaccess.SqlAuthData;
import dataaccess.sqldataaccess.SqlGamesData;
import dataaccess.sqldataaccess.SqlUserData;
import exception.ResponseException;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestDataAccess {

    GameDAO gameDAO=new SqlGamesData();
    UserDAO userDAO=new SqlUserData();
    AuthDAO authDAO=new SqlAuthData();

    public TestDataAccess() throws ResponseException {
    }
    @Test
    @DisplayName("AddAuthData Success")
    public void addAuthDataSuccess() throws ResponseException {
        authDAO.clearAuthData();
        Assertions.assertDoesNotThrow(() -> authDAO.addAuthData(new AuthData("asdf","lkjkj")));
    }

    @Test
    @DisplayName("AddAuthData Faliure")
    public void addAuthDataFaliure() throws ResponseException {
        authDAO.clearAuthData();
        authDAO.addAuthData(new AuthData("asdf","lkjkj"));
        Assertions.assertThrows(ResponseException.class,() -> authDAO.addAuthData(new AuthData("asdf","lkjkj")));
    }

    @Test
    @DisplayName("GetAuthData Success")
    public void getAuthDataSuccess() throws ResponseException {
        authDAO.clearAuthData();
        authDAO.addAuthData(new AuthData("asdf","lkjkj"));
        Assertions.assertDoesNotThrow(() -> authDAO.getAuthData("asdf"));
    }

    @Test
    @DisplayName("Register Faliure")
    public void getAuthDataFaliure() throws ResponseException {
        authDAO.clearAuthData();
        authDAO.addAuthData(new AuthData("asdf","lkjkj"));
        Assertions.assertNull(authDAO.getAuthData("j"));
    }

    @Test
    @DisplayName("Delete Success")
    public void deleteAuthDataSuccess() throws ResponseException {
        authDAO.clearAuthData();
        authDAO.addAuthData(new AuthData("asdf","lkjkj"));

        Assertions.assertTrue(authDAO.deleteAuthData("asdf"));
    }

    @Test
    @DisplayName("Delete Faliure")
    public void deleteAuthDataFaliure() throws ResponseException {
        authDAO.clearAuthData();
        authDAO.addAuthData(new AuthData("asdf","lkjkj"));
        Assertions.assertDoesNotThrow(() -> authDAO.deleteAuthData(null));
    }

    @Test
    @DisplayName("ClearAuthData Success")
    public void clearAuthDataSuccess() throws ResponseException {
        authDAO.clearAuthData();
        authDAO.addAuthData(new AuthData("asdf","lkjkj"));
        Assertions.assertDoesNotThrow(() -> authDAO.clearAuthData());
        Assertions.assertNull(authDAO.getAuthData("asdf"));
    }

    @Test
    @DisplayName("Add User Success")
    public void createUserDataSuccess() throws ResponseException {
        userDAO.clearUserData();
        Assertions.assertDoesNotThrow(() -> userDAO.createUser(new UserData("park","ija","3jk@gmail.com")));
    }

    @Test
    @DisplayName("Add User Faliure")
    public void createUserDataFaliure() throws ResponseException {
        userDAO.clearUserData();
        userDAO.createUser(new UserData("park","ija","3jk@gmail.com"));
        Assertions.assertThrows(ResponseException.class,() -> userDAO.createUser(new UserData("park","ija","3jk@gmail.com")));
    }

    @Test
    @DisplayName("Get User Success")
    public void getUserDataSuccess() throws ResponseException {
        userDAO.clearUserData();
        userDAO.createUser(new UserData("park","ija","3jk@gmail.com"));
        Assertions.assertNotNull(userDAO.getUserData("park"));
    }

    @Test
    @DisplayName("Get User Faliure")
    public void getUserDataFaliure() throws ResponseException {
        userDAO.clearUserData();
        userDAO.createUser(new UserData("park","ija","3jk@gmail.com"));
        Assertions.assertNull(userDAO.getUserData("park1"));
    }

    @Test
    @DisplayName("ClearUserData Success")
    public void clearUserDataSuccess() throws ResponseException {
        userDAO.clearUserData();
        userDAO.createUser(new UserData("park","ija","3jk@gmail.com"));
        Assertions.assertDoesNotThrow(() -> userDAO.clearUserData());
        Assertions.assertNull(userDAO.getUserData("park"));
    }

    @Test
    @DisplayName("Add Game Success")
    public void createGameDataSuccess() throws ResponseException {
        gameDAO.clearGames();
        Assertions.assertDoesNotThrow(() -> gameDAO.createGame("game1"));
    }

    @Test
    @DisplayName("Add Game Same Name")
    public void createGameDataFaliure() throws ResponseException {
        gameDAO.clearGames();
        gameDAO.createGame("game1");
        Assertions.assertEquals(2,gameDAO.createGame("game1"));
    }

    @Test
    @DisplayName("Find Game Success")
    public void getGameDataSuccess() throws ResponseException {
        gameDAO.clearGames();
        gameDAO.createGame("game1");
        Assertions.assertNotNull(gameDAO.findGame(1));
    }

    @Test
    @DisplayName("Get Game Faliure")
    public void getGameDataFaliure() throws ResponseException {
        gameDAO.clearGames();
        gameDAO.createGame("game1");
        Assertions.assertNull(gameDAO.findGame(2));
    }

    @Test
    @DisplayName("Update Game Success")
    public void updateGameDataSuccess() throws ResponseException {
        gameDAO.clearGames();
        gameDAO.createGame("game1");
        Assertions.assertTrue(gameDAO.updateGame("park","BLACK",new GameData(1,"null",null,"hey",new ChessGame())));
    }

    @Test
    @DisplayName("Update User Faliure")
    public void updateGameDataFaliure() throws ResponseException {
        gameDAO.clearGames();
        gameDAO.createGame("game1");
        Assertions.assertThrows(ResponseException.class,() ->gameDAO.updateGame("park","BLACK",new GameData(1,"hey","hey","hey",new ChessGame())));
    }

    @Test
    @DisplayName("List Games Success")
    public void ListGamesDataSuccess() throws ResponseException {
        gameDAO.clearGames();
        gameDAO.createGame("game1");
        Assertions.assertNotEquals(new ArrayList<GameData>(),gameDAO.listGames());
    }

    @Test
    @DisplayName("List Games Faliure")
    public void ListGamesDataFaliure() throws ResponseException {
        gameDAO.clearGames();
        Assertions.assertEquals(new ArrayList<GameData>(),gameDAO.listGames());
    }

    @Test
    @DisplayName("ClearGameData Success")
    public void clearGameDataSuccess() throws ResponseException {
        gameDAO.clearGames();
        gameDAO.createGame("game1");
        Assertions.assertDoesNotThrow(() -> gameDAO.clearGames());
        Assertions.assertNull(gameDAO.findGame(1));
    }


}
