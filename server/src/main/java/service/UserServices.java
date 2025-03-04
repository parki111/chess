package service;
import Model.AuthData;
import Model.UserData;
import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import Request_response.RegisterRequest;
import Request_response.RegisterResponse;
import exception.ResponseException;
import org.eclipse.jetty.server.Authentication;

import java.util.UUID;

public class UserServices {
    AuthDAO authDAO;
    UserDAO userDAO;
    public UserServices(AuthDAO authDAO, UserDAO userDAO){
        this.authDAO=authDAO;
        this.userDAO=userDAO;
    }
    public RegisterResponse register(RegisterRequest registerRequest) throws ResponseException {
        if (userDAO.getUserData(registerRequest.username())==null){
            userDAO.createUser(new UserData(registerRequest.username(),registerRequest.password(),registerRequest.email()));
            String authToken=UUID.randomUUID().toString();
            authDAO.addAuthData(new AuthData(authToken, registerRequest.username()));
            return new RegisterResponse(registerRequest.username(),authToken);
        }
        else{
            throw new ResponseException(403,"Error: already taken");
        }

    }
    public LoginResult login(LoginRequest loginRequest) {

    }
    public void logout(LogoutRequest logoutRequest) {

    }
}
