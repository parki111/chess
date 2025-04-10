package client;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import client.websocket.GameHandler;
import client.websocket.WebsocketFacade;
import com.google.gson.Gson;
import model.GameData;
import model.AuthData;
import model.UserData;
import exception.ResponseException;
import client.ServerFacade;
import requestresponse.*;
import ui.ChessBoardUI;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;


public class Client {
    public Boolean printBoard=false;
    private int userint;
    private String visitorName = null;
    private String authToken = null;
    private HashMap<Integer,Integer> gameDatas;
    private final ServerFacade server;
    private ChessGame.TeamColor joinedColor = null;
    private final String serverUrl;
    private State state = State.SIGNEDOUT;
    private ChessGame currGame = null;
    private WebsocketFacade websocketFacade = new WebsocketFacade();
    private Integer currGameid;

    public Client(String serverUrl) throws ResponseException {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.gameDatas = new HashMap<>();
        userint=1;
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
                case "redraw" -> redrawBoard();
                case "leave" -> leave();
                case "resign" -> resign();
                case "move" -> makeMove(params);
                case "legalmoves" -> printLegalMoves(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String printLegalMoves(String ... params) throws ResponseException {
        assertSignedIn();

    }

    public void leave() throws ResponseException, IOException {
        assertSignedIn();
        if(currGame==null){
            throw new ResponseException(400, "Not in game");
        }
        websocketFacade.leaveGame(authToken,currGameid);

    }

    public String resign() throws ResponseException {
        assertSignedIn();
        if(currGame==null){
            throw new ResponseException(400, "Not in game");
        }
    }

    public String redrawBoard() throws ResponseException {
        assertSignedIn();

    }

    public String makeMove(String...params) throws ResponseException {
        if (params.length ==2){

        }
        throw new ResponseException(400, "Expected: <PieceType> <Location>");
    }

    public String register(String... params) throws ResponseException {
        if (params.length == 3) {

            RegisterResult result = server.register(new RegisterRequest(params[0],params[1],params[2]));
            visitorName = result.username();
            authToken = result.authToken();
            state = State.SIGNEDIN;
            server.listGames(new ListGamesRequest(authToken));

            return String.format("You registered as %s.", visitorName);
        }
        throw new ResponseException(400, "Expected: <username> <password> <email>");
    }

    public String login(String... params) throws ResponseException {
        if (params.length == 2) {
            LoginResult result = server.login(new LoginRequest(params[0],params[1]));
            visitorName = result.username();
            authToken = result.authToken();
            state = State.SIGNEDIN;
            server.listGames(new ListGamesRequest(authToken));
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
        userint=1;
        for (var game : games) {
            gameDatas.put(userint,game.gameID());

            resultStr.append(String.format("Gameid: %s   Gamename: %s   WhiteUser: %s   BlackUser: %s\n"
                    ,userint,game.gameName(),game.whiteUsername(),game.blackUsername()));
            userint++;
        }
        return resultStr.toString();
    }

    public String createGame(String... params) throws ResponseException{
        assertSignedIn();

        if (authToken!=null && !authToken.isEmpty()) {

            if (params.length==1){
                CreateGameResult result = server.createGame(new CreateGameRequest(authToken,params[0]));
                userint++;
                gameDatas.put(userint,result.gameID());
                return String.format("New game %s created", params[0]);
            }
            throw new ResponseException(400, "Expected: <gamename>");
        }
        throw new ResponseException(400, "unauthorized");
    }

    public String joinGame(String... params) throws ResponseException{
        assertSignedIn();
        if (authToken!=null && !authToken.isEmpty()) {
            if (params.length==2){
                try {
                    Integer.parseInt(params[1]);
                }
                catch(NumberFormatException error){
                    throw new ResponseException(400, "Expected: <playercolor> <gameid>");
                }
                if (params[0].equalsIgnoreCase("WHITE")){
                    joinedColor=WHITE;
                }
                else if(params[0].equalsIgnoreCase("BLACK")){
                    joinedColor=BLACK;
                }
                else{
                    throw new ResponseException(400, "Expected: <playercolor> <gameid>");
                }
                currGame= server.joinGame(new JoinGameRequest(authToken,params[0],gameDatas.get(Integer.parseInt(params[1]))));
                currGameid=gameDatas.get(Integer.parseInt(params[1]));
                printBoard=true;
                return String.format("Joined game %s as %s", params[1],params[0]);
            }
            throw new ResponseException(400, "Expected: <playercolor> <gameid>");
        }
        throw new ResponseException(400, "unauthorized");
    }




    public String observeGame(String... params) throws ResponseException, IOException {
        assertSignedIn();

        if (gameDatas.containsKey(Integer.parseInt(params[0]))){
            websocketFacade.connect(authToken,Integer.parseInt(params[0]));
            printBoard=true;
        }
        else{
            throw new ResponseException(400, "Game does not exist");
        }
        return "";

    }

    public String help() {
        if (state == State.SIGNEDOUT) {
            return """
                    - register <username> <password> <email>
                    - login <username> <password>
                    - help
                    - quit
                    """;
        }
        return """
                - listgames
                - creategame <gamename>
                - playgame <playercolor> <gameid>
                - observegame <gameid>
                - logout
                - help
                - quit
                """;
    }

    public ChessGame.TeamColor getJoinedColor(){
        return joinedColor;
    }

    private void assertSignedIn() throws ResponseException {
        if (state == State.SIGNEDOUT) {
            throw new ResponseException(400, "You must sign in");
        }
    }
}