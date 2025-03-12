package dataaccess.sqldataaccess;

import dataaccess.UserDAO;
import exception.ResponseException;
import model.UserData;

public class SqlDataAccessUserData implements UserDAO {
    public Boolean createUser(UserData user) throws ResponseException{return Boolean.TRUE;};
    public UserData getUserData(String username) throws ResponseException{return new UserData("","","");};
    public void clearUserData() throws ResponseException{};
}
