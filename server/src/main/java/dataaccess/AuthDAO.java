package dataaccess;
import Model.AuthData;
import exception.ResponseException;

public interface AuthDAO {
    AuthData createAuthToken(AuthData authData) throws ResponseException;
    AuthData getAuthData(String authToken) throws ResponseException;
    Boolean deleteAuthData(String authToken) throws ResponseException;
    void clearAuthData() throws ResponseException;
    //         Pet addPet(Pet pet) throws ResponseException;

}
