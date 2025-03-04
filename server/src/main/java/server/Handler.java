package server;
import Request_response.RegisterRequest;
import Request_response.RegisterResponse;
import com.google.gson.Gson;
import dataaccess.AuthDAO;
import service.Services;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import exception.ResponseException;
import spark.*;

public class Handler {
    Services Services;
    GameDAO gameDAO;
    UserDAO userDAO;
    AuthDAO authDAO;
    public Handler(AuthDAO authDao, UserDAO userDAO, GameDAO gameDAO){
        this.authDAO=authDao;
        this.userDAO=userDAO;
        this.gameDAO=gameDAO;
    }

    public Object register(Request request, Response response) throws ResponseException {
        RegisterRequest registerRequest = new Gson().fromJson(request.body(), RegisterRequest.class);
        RegisterResponse registerResponse = Services.Register(registerRequest);
        response.status(200);
        return new Gson().toJson(registerResponse);
    }
}
