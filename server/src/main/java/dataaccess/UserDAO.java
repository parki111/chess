package dataaccess;

import Model.UserData;
import exception.ResponseException;

public interface UserDAO {
    Boolean createUser(UserData user) throws ResponseException;
    UserData getUserData(String username) throws ResponseException;
    void clearUserData() throws ResponseException;
}
