package service;
import model.AuthData;
import model.UserData;
import requestresponse.*;
import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import exception.ResponseException;

import java.util.Objects;
import java.util.UUID;

public class UserServices {
    AuthDAO authDAO;
    UserDAO userDAO;
    public UserServices(AuthDAO authDAO, UserDAO userDAO){
        this.authDAO=authDAO;
        this.userDAO=userDAO;
    }
    public RegisterResult register(RegisterRequest registerRequest) throws ResponseException {
        if (userDAO.getUserData(registerRequest.username())==null && !Objects.equals(registerRequest.username(), "")){
            userDAO.createUser(new UserData(registerRequest.username(),registerRequest.password(),registerRequest.email()));
            String authToken=UUID.randomUUID().toString();
            authDAO.addAuthData(new AuthData(authToken, registerRequest.username()));
            return new RegisterResult(registerRequest.username(),authToken);
        }
        else{
            throw new ResponseException(403,"Error: already taken");
        }

    }
    public LoginResult login(LoginRequest loginRequest) throws ResponseException {
        UserData userData = userDAO.getUserData(loginRequest.username());
        if (userData!=null){
            if (loginRequest.password().equals(userData.password())){
                String authToken=UUID.randomUUID().toString();
                authDAO.addAuthData(new AuthData(authToken, loginRequest.username()));
                return new LoginResult(loginRequest.username(), authToken);
            }
            else{
                throw new ResponseException(401,"Error: unauthorized");
            }
        }
        else{
            throw new ResponseException(401,"Error: no userdata with given username");
        }
    }
    public void logout(LogoutRequest logoutRequest) throws ResponseException {
        if (authDAO.getAuthData(logoutRequest.authToken())!=null){
            authDAO.deleteAuthData(logoutRequest.authToken());
        }
        else{
            throw new ResponseException(401,"Error: unauthorized");
        }
    }
}
