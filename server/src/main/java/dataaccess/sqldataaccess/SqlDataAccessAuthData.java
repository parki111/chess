package dataaccess.sqldataaccess;

import exception.ResponseException;
import model.AuthData;

public class SqlDataAccessAuthData {
    public AuthData addAuthData(AuthData authData) throws ResponseException{return new AuthData("","");};
    public AuthData getAuthData(String authToken) throws ResponseException{return new AuthData("","");};
    public Boolean deleteAuthData(String authToken) throws ResponseException{return true;};
    public void clearAuthData() throws ResponseException{};
}
