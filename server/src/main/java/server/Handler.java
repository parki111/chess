package server;
import Request_response.LoginRequest;
import Request_response.LoginResult;
import Request_response.RegisterRequest;
import Request_response.RegisterResult;
import com.google.gson.Gson;
import dataaccess.AuthDAO;
import service.ClearService;
import service.UserServices;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import exception.ResponseException;
import spark.*;

public class Handler {
    UserServices userServices;
    ClearService clearService;
    GameDAO gameDAO;
    UserDAO userDAO;
    AuthDAO authDAO;

    public Handler(AuthDAO authDao, UserDAO userDAO, GameDAO gameDAO){
        this.authDAO=authDao;
        this.userDAO=userDAO;
        this.gameDAO=gameDAO;
        this.userServices=new UserServices(authDAO,userDAO);
        this.clearService=new ClearService(authDao,userDAO,gameDAO);
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
        LoginRequest loginRequest = new Gson().fromJson(request.body(), LoginRequest.class);
        LoginResult loginResult = userServices.login(loginRequest);
        response.status(200);
        return new Gson().toJson(loginResult);
    }

    public Object clear(Request request, Response response) throws ResponseException{
        clearService.clear();
        response.status(200);
        return "";
    }

    public void exceptionHandler(ResponseException ex, Request req, Response res) {
        res.status(ex.StatusCode());
        res.body(ex.toJson());
    }
//    public Object clear(Request request, Response response) throws ResponseException{
//        ClearRequest
//    }

//    public Object exception(Reque)
}
