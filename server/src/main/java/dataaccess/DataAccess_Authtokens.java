package dataaccess;
import Model.AuthData;
import Model.UserData;
import exception.ResponseException;

import java.util.Collection;

public interface DataAccess_Authtokens {
    void createAuthToken(AuthData authData) throws ResponseException;
    AuthData getAuthData(String authToken) throws ResponseException;
    void deleteAuthData(String authToken) throws ResponseException;
    //         Pet addPet(Pet pet) throws ResponseException;

}
