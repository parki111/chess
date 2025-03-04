package service;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import dataaccess.memorydataaccess.MemoryAuthData;
import dataaccess.memorydataaccess.MemoryGamesData;
import dataaccess.memorydataaccess.MemoryUserData;
import exception.ResponseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import requestresponse.*;

public class TestService {

    GameDAO gameDAO=new MemoryGamesData();
    UserDAO userDAO=new MemoryUserData();
    AuthDAO authDAO=new MemoryAuthData();
    UserServices userServices=new UserServices(authDAO,userDAO);
    ClearService clearService=new ClearService(authDAO,userDAO,gameDAO);
    GameServices gameServices=new GameServices(authDAO,gameDAO);
    @Test
    @DisplayName("Register Success")
    public void registerSuccess() throws ResponseException {
        RegisterRequest registerRequest= new RegisterRequest("Username","password","email");
        Assertions.assertNotNull(userServices.register(registerRequest).authToken());
    }
    @Test
    @DisplayName("ThrowErrorRegister")
    public void registerFaliure() throws ResponseException{
        RegisterRequest registerRequest= new RegisterRequest("Username","password","email");
        userServices.register(registerRequest);
        Assertions.assertThrows(ResponseException.class, () -> userServices.register(registerRequest));
    }

    @Test
    @DisplayName("Login Success")
    public void loginSuccess() throws ResponseException {
        RegisterRequest registerRequest= new RegisterRequest("Username","password","email");
        userServices.register(registerRequest);
        LoginRequest loginRequest= new LoginRequest("Username","password");
        Assertions.assertNotNull(userServices.login(loginRequest).authToken());
    }

    @Test
    @DisplayName("Login Faliure")
    public void loginFaliure() throws ResponseException {
        LoginRequest loginRequest= new LoginRequest("Username","password");
        Assertions.assertThrows(ResponseException.class, () -> userServices.login(loginRequest).authToken());
    }

    @Test
    @DisplayName("Logout Success")
    public void logoutSuccess() throws ResponseException {
        RegisterRequest registerRequest= new RegisterRequest("Username","password","email");

        LogoutRequest logoutRequest= new LogoutRequest(userServices.register(registerRequest).authToken());
        Assertions.assertDoesNotThrow(() -> userServices.logout(logoutRequest));
    }

    @Test
    @DisplayName("Logout Faliure")
    public void logoutFaliure() throws ResponseException {
        LogoutRequest logoutRequest= new LogoutRequest("4");
        Assertions.assertThrows(ResponseException.class,() -> userServices.logout(logoutRequest));
    }

    @Test
    @DisplayName("Create Game Success")
    public void createGameSuccess() throws ResponseException {
        RegisterRequest registerRequest= new RegisterRequest("Username","password","email");
        CreateGameRequest createGameRequest= new CreateGameRequest(userServices.register(registerRequest).authToken(),"new");
        Assertions.assertDoesNotThrow(()-> gameServices.creategame(createGameRequest));
    }

    @Test
    @DisplayName("Create Game Faliure")
    public void createGameFaliure() throws ResponseException {
        CreateGameRequest createGameRequest= new CreateGameRequest("4","new");
        Assertions.assertThrows(ResponseException.class,()-> gameServices.creategame(createGameRequest));
    }

    @Test
    @DisplayName("Join Game Success")
    public void joinGameSuccess() throws ResponseException {
        RegisterRequest registerRequest= new RegisterRequest("Username","password","email");
        String authToken=userServices.register(registerRequest).authToken();
        CreateGameRequest createGameRequest= new CreateGameRequest(authToken,"new");
        gameServices.creategame(createGameRequest);
        JoinGameRequest joinGameRequest = new JoinGameRequest(authToken,"BLACK",1);
        Assertions.assertDoesNotThrow(()-> gameServices.joinGame(joinGameRequest));
    }

    @Test
    @DisplayName("Join Game Faliure")
    public void joinGameFaliure() throws ResponseException {
        RegisterRequest registerRequest= new RegisterRequest("Username","password","email");
        String authToken=userServices.register(registerRequest).authToken();
        JoinGameRequest joinGameRequest = new JoinGameRequest(authToken,"BLACK",1);
        Assertions.assertThrows(ResponseException.class,()-> gameServices.joinGame(joinGameRequest));
    }

    @Test
    @DisplayName("List Games Success")
    public void listGamesSuccess() throws ResponseException {
        RegisterRequest registerRequest= new RegisterRequest("Username","password","email");
        String authToken=userServices.register(registerRequest).authToken();
        ListGamesRequest listGamesRequest = new ListGamesRequest(authToken);
        Assertions.assertDoesNotThrow(()-> gameServices.listgames(listGamesRequest));
    }

    @Test
    @DisplayName("List Games Faliure")
    public void listGamesFaliure() throws ResponseException {
        ListGamesRequest listGamesRequest = new ListGamesRequest("4");
        Assertions.assertThrows(ResponseException.class,()-> gameServices.listgames(listGamesRequest));
    }

    @Test
    @DisplayName("Clear All Success")
    public void clearAllSuccess() throws ResponseException {
        RegisterRequest registerRequest= new RegisterRequest("Username","password","email");
        CreateGameRequest createGameRequest= new CreateGameRequest(userServices.register(registerRequest).authToken(),"new");
        gameServices.creategame(createGameRequest);
        Assertions.assertDoesNotThrow(()-> clearService.clear());
    }

    @Test
    @DisplayName("Clear All Faliure")
    public void clearFaliure() throws ResponseException {
        ListGamesRequest listGamesRequest = new ListGamesRequest("4");
        Assertions.assertThrows(ResponseException.class,()-> gameServices.listgames(listGamesRequest));
    }
}
