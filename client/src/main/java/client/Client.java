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

    public Client(String serverUrl, NotificationHandler notificationHandler) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.notificationHandler = notificationHandler;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "Login" -> login(params);
                case "rescue" -> rescuePet(params);
                case "list" -> listPets();
                case "signout" -> signOut();
                case "adopt" -> adoptPet(params);
                case "adoptall" -> adoptAllPets();
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
        assertSignedIn();
        var result = server.listGames(new ListGamesRequest(authToken));
        Collection<GameData> games = result.games();
        var resultStr = new StringBuilder();
        var gson = new Gson();
        for (var game : games) {
            resultStr.append(gson.toJson(game)).append('\n');
        }
        return result.toString();
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

    public String adoptPet(String... params) throws ResponseException {
        assertSignedIn();
        if (params.length == 1) {
            try {
                var id = Integer.parseInt(params[0]);
                var pet = getPet(id);
                if (pet != null) {
                    server.deletePet(id);
                    return String.format("%s says %s", pet.name(), pet.sound());
                }
            } catch (NumberFormatException ignored) {
            }
        }
        throw new ResponseException(400, "Expected: <pet id>");
    }

    public String adoptAllPets() throws ResponseException {
        assertSignedIn();
        var buffer = new StringBuilder();
        for (var pet : server.listPets()) {
            buffer.append(String.format("%s says %s%n", pet.name(), pet.sound()));
        }

        server.deleteAllPets();
        return buffer.toString();
    }

    public String signOut() throws ResponseException {
        assertSignedIn();
        state = State.SIGNEDOUT;
        return String.format("%s left the shop", visitorName);
    }

    private Pet getPet(int id) throws ResponseException {
        for (var pet : server.listPets()) {
            if (pet.id() == id) {
                return pet;
            }
        }
        return null;
    }

    public String help() {
        if (state == State.SIGNEDOUT) {
            return """
                    - signIn <yourname>
                    - quit
                    """;
        }
        return """
                - list
                - adopt <pet id>
                - rescue <name> <CAT|DOG|FROG|FISH>
                - adoptAll
                - signOut
                - quit
                """;
    }

    private void assertSignedIn() throws ResponseException {
        if (state == State.SIGNEDOUT) {
            throw new ResponseException(400, "You must sign in");
        }
    }
}