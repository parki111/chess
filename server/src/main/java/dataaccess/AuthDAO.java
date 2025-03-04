package dataaccess;
import model.AuthData;
import exception.ResponseException;

public interface AuthDAO {
    AuthData addAuthData(AuthData authData) throws ResponseException;
    AuthData getAuthData(String authToken) throws ResponseException;
    Boolean deleteAuthData(String authToken) throws ResponseException;
    void clearAuthData() throws ResponseException;
    //         Pet addPet(Pet pet) throws ResponseException;

}
