package client;

import java.util.Arrays;
import java.util.Collection;

import com.google.gson.Gson;
import model.GameData;
import model.AuthData;
import model.UserData;
import exception.ResponseException;
import client.ServerFacade;
import requestresponse.*;

public class Client {
    private String visitorName = null;
    private String authToken = null;
    private final ServerFacade server;
    private final String serverUrl;
    private State state = State.SIGNEDOUT;

    public Client(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
//        this.notificationHandler = notificationHandler;
    }

    public String eval(String input) {
        try {
            var tokens = input.split(" ");
            var cmd = (tokens.length > 0) ? tokens[0].toLowerCase() : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "register" -> register(params);
                case "login" -> login(params);
                case "logout" -> logout();
                case "listgames" -> listGames();
                case "creategame" -> createGame(params);
                case "playgame" -> joinGame(params);
                case "observegame" -> observeGame(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String register(String... params) throws ResponseException {
        if (params.length >= 3) {
            state = State.SIGNEDIN;
            RegisterResult result = server.register(new RegisterRequest(params[0],params[1],params[2]));
            visitorName = result.username();
            authToken = result.authToken();
            return String.format("You registered as %s.", visitorName);
        }
        throw new ResponseException(400, "Expected: <username> <password> <email>");
    }

    public String login(String... params) throws ResponseException {
        if (params.length >= 2) {
            state = State.SIGNEDIN;
            LoginResult result = server.login(new LoginRequest(params[0],params[1]));
            visitorName = result.username();
            authToken = result.authToken();
            return String.format("You signed in as %s.", visitorName);
        }
        throw new ResponseException(400, "Expected: <username> <password>");
    }

    public String logout() throws ResponseException {
        assertSignedIn();
        if (authToken!=null && !authToken.isEmpty()){
            state = State.SIGNEDOUT;
            server.logout(new LogoutRequest(authToken));
            authToken=null;
            return String.format("%s logged out", visitorName);
        }
        throw new ResponseException(400, "Not logged in");
    }

    public String listGames() throws ResponseException {
        assertSignedIn();
        var result = server.listGames(new ListGamesRequest(authToken));
        Collection<GameData> games = result.games();
        var resultStr = new StringBuilder();
        var gson = new Gson();
        for (var game : games) {
            resultStr.append(gson.toJson(game)).append('\n');
        }
        return resultStr.toString();
    }

    public String createGame(String... params) throws ResponseException{
        assertSignedIn();
        if (authToken!=null && !authToken.isEmpty()) {
            if (params.length>=1){
                server.createGame(new CreateGameRequest(authToken,params[0]));
                return String.format("New game %s created", params[0]);
            }
            throw new ResponseException(400, "Expected: <gamename>");
        }
        throw new ResponseException(400, "unauthorized");
    }

    public String joinGame(String... params) throws ResponseException{
        assertSignedIn();
        if (authToken!=null && !authToken.isEmpty()) {
            if (params.length>=2){
                server.joinGame(new JoinGameRequest(authToken,params[0],Integer.parseInt(params[1])));
                return String.format("New game %s created", params[0]);
            }
            throw new ResponseException(400, "Expected: <gamename>");
        }
        throw new ResponseException(400, "unauthorized");
    }

    public String observeGame(String... params) throws ResponseException{
        assertSignedIn();
        if (authToken!=null && !authToken.isEmpty()) {return "";}
        throw new ResponseException(400, "unauthorized");
    }

    public String help() {
        if (state == State.SIGNEDOUT) {
            return """
                    - register <username> <password> <email>
                    - login <username> <password>
                    - quit
                    """;
        }
        return """
                - listgames
                - creategame <gamename>
                - playgame <gameid>
                - observegame <gameid>
                - logout
                - quit
                """;
    }

    private void assertSignedIn() throws ResponseException {
        if (state == State.SIGNEDOUT) {
            throw new ResponseException(400, "You must sign in");
        }
    }
}