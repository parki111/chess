package client;

import java.io.IOException;
import java.util.*;

import chess.ChessGame;
import chess.ChessMove;
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
import static ui.EscapeSequences.RESET_TEXT_COLOR;
import static ui.EscapeSequences.SET_TEXT_COLOR_GREEN;


public class Client implements GameHandler{
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
    private WebsocketFacade websocketFacade;
    private Integer currGameid;

    public Client(String serverUrl) throws ResponseException {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.gameDatas = new HashMap<>();
        userint=1;
//        this.notificationHandler = notificationHandler;
    }

    public String eval(String input) throws IOException{
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
        if(currGame==null){
            throw new ResponseException(400, "Not in game");
        }
        if (params.length == 1)
        {
            updateGame(currGame,parseChessPosition(params[0]));
        }
        else{
            throw new ResponseException(400, "Expected: <Row(Letter)Column(Number)>   eg. a1");
        }
        return "";
    }

    public ChessPosition parseChessPosition(String unparsed) throws ResponseException {
        if(unparsed.length()!=2) {
            throw new ResponseException(400, "Expected: <Row(Letter)Column(Number)>   eg. a1");
        }
        try {
            int row;
            List<Character> rowList = new ArrayList<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'));
            row = rowList.indexOf(unparsed.charAt(0))+1;
            int column = Integer.parseInt(Character.toString(unparsed.charAt(1)));
            if (column<8 || column<1){
                throw new ResponseException(400, "Expected: <Row(Letter)Column(Number)>   eg. a1");
            }
            return new ChessPosition(row,column);
        }
        catch(Exception e){
            throw new ResponseException(400, "Expected: <Row(Letter)Column(Number)>   eg. a1");
        }
    }

    public String leave() throws ResponseException, IOException {
        assertSignedIn();
        if(currGame==null){
            throw new ResponseException(400, "Not in game");
        }
        websocketFacade.leaveGame(authToken,currGameid);
        websocketFacade = null;
        return "You left the game.";
    }

    public String resign() throws ResponseException, IOException {
        assertSignedIn();
        if(currGame==null){
            throw new ResponseException(400, "Not in game");
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you really want to resign? Yes/No");
        String line = scanner.nextLine();
        if (Objects.equals(line.toLowerCase(), "yes"))
        {
            websocketFacade.resignGame(authToken,currGameid);
            return "";//covered in WebSocketHandler
        }
        else{
            return "Resign cancelled";
        }

    }

    public String redrawBoard() throws ResponseException {
        assertSignedIn();
        if(currGame==null){
            throw new ResponseException(400, "Not in game");
        }
        updateGame(currGame,null);
        return "Board redrawn";
    }

    public String makeMove(String...params) throws ResponseException, IOException {
        assertSignedIn();
        if(currGame==null){
            throw new ResponseException(400, "Not in game");
        }
        if (params.length != 2){
            throw new ResponseException(400, "Expected: <Row(Letter)Column(Number)> <Row(Letter)Column(Number)>  eg. a7 a6");
        }
        ChessPosition startPosition = parseChessPosition(params[0]);
        ChessPosition endPosition = parseChessPosition(params[1]);
        if(currGame.validMoves(startPosition).contains(new ChessMove(startPosition,endPosition,null))){
            ChessPiece.PieceType promotionPiece = null;
            if(currGame.validMoves(startPosition).contains(new ChessMove(startPosition,endPosition, ChessPiece.PieceType.QUEEN))){

            }
            websocketFacade.makeMove(authToken,currGameid,new ChessMove(startPosition,endPosition,promotionPiece));
            return "";
        }
        else{
            throw new ResponseException(400, "Invalid move");
        }
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

    public String joinGame(String... params) throws ResponseException, IOException {
        assertSignedIn();
        if (authToken!=null && !authToken.isEmpty()) {
            if (params.length==2){
                try {
                    Integer.parseInt(params[1]);
                }
                catch(NumberFormatException error){
                    throw new ResponseException(400, "Expected: <playercolor> <gameid>");
                }
                if (params[0].toLowerCase().equals("white")){
                    joinedColor=WHITE;
                }
                else if(params[0].toLowerCase().equals("black")){
                    joinedColor=BLACK;
                }
                else{
                    throw new ResponseException(400, "Expected: <playercolor> <gameid>");
                }


                currGame= server.joinGame(new JoinGameRequest(authToken,params[0],gameDatas.get(Integer.parseInt(params[1]))));
                currGameid=gameDatas.get(Integer.parseInt(params[1]));
                websocketFacade = new WebsocketFacade(this);
                websocketFacade.connect(authToken,currGameid);

                return String.format("Joined game %s as %s", params[1],params[0]);
            }
            throw new ResponseException(400, "Expected: <playercolor> <gameid>");
        }
        throw new ResponseException(400, "unauthorized");
    }

    public void updateGame(ChessGame chessGame, ChessPosition validMoves) {
        if (getJoinedColor()==WHITE || getJoinedColor()==null){
            new ChessBoardUI(ChessGame.TeamColor.WHITE,chessGame,validMoves).chessBoardWhite();
        }
        else{
            new ChessBoardUI(ChessGame.TeamColor.BLACK,chessGame,validMoves).chessBoardBlack();
        }
        System.out.print("\n" + RESET_TEXT_COLOR + ">>> " + SET_TEXT_COLOR_GREEN);
    }

    public void printMessage(String message){
        System.out.println(message);
    }

    public String observeGame(String... params) throws ResponseException, IOException {
        assertSignedIn();
        if (gameDatas.containsKey(Integer.parseInt(params[0]))){
            currGameid=gameDatas.get(Integer.parseInt(params[0]));
            websocketFacade = new WebsocketFacade(this);
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
        if(currGame!=null){
            return """
                    -move <(Letter)(Number)> <(Letter)(Number)>
                    -redraw
                    -leave
                    -resign
                    -legalmoves <(Letter)(Number)>
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