package server;
import requestresponse.*;
import com.google.gson.Gson;
import dataaccess.AuthDAO;
import service.ClearService;
import service.GameServices;
import service.UserServices;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import exception.ResponseException;
import spark.*;

public class Handler {
    UserServices userServices;
    ClearService clearService;
    GameServices gameServices;
    GameDAO gameDAO;
    UserDAO userDAO;
    AuthDAO authDAO;

    public Handler(AuthDAO authDAO, UserDAO userDAO, GameDAO gameDAO){
        this.authDAO=authDAO;
        this.userDAO=userDAO;
        this.gameDAO=gameDAO;
        this.userServices=new UserServices(authDAO,userDAO);
        this.clearService=new ClearService(authDAO,userDAO,gameDAO);
        this.gameServices=new GameServices(authDAO,gameDAO);
    }

    public Object register(Request request, Response response) throws ResponseException {
        RegisterRequest registerRequest = new Gson().fromJson(request.body(), RegisterRequest.class);
        RegisterResult registerResult = userServices.register(registerRequest);
        response.status(200);
        return new Gson().toJson(registerResult);
    }

    public Object login(Request request, Response response) throws ResponseException {
        LoginRequest loginRequest = new Gson().fromJson(request.body(), LoginRequest.class);
        LoginResult loginResult = userServices.login(loginRequest);
        response.status(200);
        return new Gson().toJson(loginResult);
    }

    public Object logout(Request request, Response response) throws ResponseException {
        LogoutRequest logoutRequest = new LogoutRequest(request.headers("Authorization"));
        userServices.logout(logoutRequest);
        response.status(200);
        return "";
    }

    public Object listGames(Request request, Response response) throws ResponseException {
        ListGamesRequest listGamesRequest = new ListGamesRequest(request.headers("Authorization"));
        ListGamesResult listGamesResult = gameServices.listgames(listGamesRequest);
        response.status(200);
        return new Gson().toJson(listGamesResult);
    }

    public Object createGame(Request request, Response response) throws ResponseException{
        CreateGameRequestHelper createGameRequestHelper = new Gson().fromJson(request.body(),CreateGameRequestHelper.class);
        CreateGameRequest createGameRequest = new CreateGameRequest(request.headers("Authorization"),createGameRequestHelper.gameName());
        CreateGameResult createGameResult = gameServices.creategame(createGameRequest);
        response.status(200);
        return new Gson().toJson(createGameResult);
    }

    public Object clear(Request request, Response response) throws ResponseException{
        clearService.clear();
        response.status(200);
        return "";
    }

    public void exceptionHandler(ResponseException ex, Request req, Response res) {
        res.status(ex.thisStatusCode());
        res.body(ex.toJson());
    }
//    public Object clear(Request request, Response response) throws ResponseException{
//        ClearRequest
//    }

//    public Object exception(Reque)
}
