package client;

import exception.ResponseException;
import model.GameData;
import org.junit.jupiter.api.*;
import requestresponse.*;
import server.Server;

import java.util.ArrayList;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;
    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverFacade= new ServerFacade("http://localhost:"+port);
    }


    @BeforeEach
    public void clear()throws ResponseException{
        serverFacade.clear();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    @DisplayName("Register Success")
    public void registerSuccess() throws ResponseException {

        RegisterRequest request = new RegisterRequest("username","password","email");
        Assertions.assertDoesNotThrow(() -> serverFacade.register(request));
    }

    @Test
    @DisplayName("Register Faliure")
    public void registerFaliure() throws ResponseException {

        RegisterRequest request = new RegisterRequest("username","password","email");
        RegisterRequest request1 = new RegisterRequest("username","pass","email");
        serverFacade.register(request);
        Assertions.assertThrows(ResponseException.class,() -> serverFacade.register(request1));
    }

    @Test
    @DisplayName("Login Success")
    public void loginSuccess() throws ResponseException {
        RegisterRequest request1 = new RegisterRequest("username","pass","email");
        LoginRequest request = new LoginRequest("username","pass");
        serverFacade.register(request1);
        Assertions.assertDoesNotThrow(() -> serverFacade.login(request));
    }

    @Test
    @DisplayName("Login Faliure")
    public void loginFaliure() throws ResponseException {
        RegisterRequest request1 = new RegisterRequest("username","pass","email");
        LoginRequest request = new LoginRequest("name","password");

        serverFacade.register(request1);
        Assertions.assertThrows(ResponseException.class,() -> serverFacade.login(request));
    }

    @Test
    @DisplayName("Logout Success")
    public void logoutSuccess() throws ResponseException {
        RegisterRequest request1 = new RegisterRequest("username","pass","email");

        RegisterResult result= serverFacade.register(request1);
        LogoutRequest request = new LogoutRequest(result.authToken());
        Assertions.assertDoesNotThrow(() -> serverFacade.logout(request));
    }

    @Test
    @DisplayName("Logout Faliure")
    public void logoutFaliure() throws ResponseException {
        RegisterRequest request1 = new RegisterRequest("username","pass","email");

        RegisterResult result= serverFacade.register(request1);
        LogoutRequest request = new LogoutRequest(result.authToken()+"1");
        Assertions.assertThrows(ResponseException.class,() -> serverFacade.logout(request));
    }

    @Test
    @DisplayName("ListGames Success")
    public void listGamesSuccess() throws ResponseException {
        RegisterRequest request1 = new RegisterRequest("username","pass","email");

        RegisterResult result= serverFacade.register(request1);
        ListGamesRequest request = new ListGamesRequest(result.authToken());
        Assertions.assertDoesNotThrow(() -> serverFacade.listGames(request));
    }

    @Test
    @DisplayName("listGames Faliure")
    public void listGamesFaliure() throws ResponseException {
        RegisterRequest request1 = new RegisterRequest("username","pass","email");

        RegisterResult result= serverFacade.register(request1);
        ListGamesRequest request = new ListGamesRequest(result.authToken()+"1");
        Assertions.assertThrows(ResponseException.class,() -> serverFacade.listGames(request));
    }

    @Test
    @DisplayName("CreateGame Success")
    public void createGameSuccess() throws ResponseException {
        RegisterRequest request1 = new RegisterRequest("username","pass","email");

        RegisterResult result= serverFacade.register(request1);
        CreateGameRequest request = new CreateGameRequest(result.authToken(),"name");
        Assertions.assertDoesNotThrow(() -> serverFacade.createGame(request));
    }

    @Test
    @DisplayName("createGame Faliure")
    public void createGameFaliure() throws ResponseException {
        RegisterRequest request1 = new RegisterRequest("username","pass","email");

        RegisterResult result= serverFacade.register(request1);
        CreateGameRequest request = new CreateGameRequest(result.authToken()+"1","name");
        Assertions.assertThrows(ResponseException.class,() -> serverFacade.createGame(request));
    }

    @Test
    @DisplayName("JoinGame Success")
    public void joinGameSuccess() throws ResponseException {
        RegisterRequest request1 = new RegisterRequest("username","pass","email");

        RegisterResult result= serverFacade.register(request1);
        CreateGameRequest request = new CreateGameRequest(result.authToken(),"name");
        serverFacade.createGame(request);
        JoinGameRequest request2 = new JoinGameRequest(result.authToken(),"WHITE",1);
        Assertions.assertDoesNotThrow(() -> serverFacade.joinGame(request2));
    }

    @Test
    @DisplayName("joinGame Faliure")
    public void joinGameFaliure() throws ResponseException {
        RegisterRequest request1 = new RegisterRequest("username","pass","email");

        RegisterResult result= serverFacade.register(request1);
        CreateGameRequest request = new CreateGameRequest(result.authToken(),"name");
        serverFacade.createGame(request);
        JoinGameRequest request2 = new JoinGameRequest(result.authToken(),"WHITE",2);
        Assertions.assertThrows(ResponseException.class,() -> serverFacade.joinGame(request2));
    }

    @Test
    @DisplayName("Clear Success")
    public void ClearSuccess() throws ResponseException {
        RegisterRequest request1 = new RegisterRequest("username","pass","email");
        serverFacade.register(request1);
        serverFacade.clear();

        Assertions.assertDoesNotThrow(() -> serverFacade.register(request1));
    }

}
