package dataaccess.memorydataaccess;

import model.UserData;
import dataaccess.UserDAO;
import exception.ResponseException;

import java.util.HashMap;
import java.util.Objects;

public class MemoryUserData implements UserDAO {
    HashMap<String,UserData> users = new HashMap<>();
    public Boolean createUser(UserData user) throws ResponseException{
        if (!users.containsKey(user.username()) && !Objects.equals(user.username(), ""))
        {
            users.put(user.username(),user);
            return Boolean.TRUE;
        }
        else{
            throw new ResponseException(403,"Error: already taken");
        }

    }

    public UserData getUserData(String username) throws ResponseException{
        if (users.containsKey(username)){
            return users.get(username);
        }
        else{
            return null;
        }
    }

    public void clearUserData() throws ResponseException{
        users.clear();
    }
}
