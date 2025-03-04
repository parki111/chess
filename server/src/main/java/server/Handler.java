package server;
import Request_response.RegisterRequest;
import Request_response.RegisterResponse;
import com.google.gson.Gson;
import dataaccess.AuthDAO;
import service.UserServices;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import exception.ResponseException;
import spark.*;

public class Handler {
    UserServices userServices;
    GameDAO gameDAO;
    UserDAO userDAO;
    AuthDAO authDAO;

    public Handler(AuthDAO authDao, UserDAO userDAO, GameDAO gameDAO){
        this.authDAO=authDao;
        this.userDAO=userDAO;
        this.gameDAO=gameDAO;
        this.userServices=new UserServices(authDAO,userDAO);
    }

    public Object register(Request request, Response response) throws ResponseException {
        RegisterRequest registerRequest = new Gson().fromJson(request.body(), RegisterRequest.class);
        RegisterResponse registerResponse = userServices.register(registerRequest);
        response.status(200);
        return new Gson().toJson(registerResponse);
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
