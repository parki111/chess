package dataaccess.memorydataaccess;

import model.AuthData;
import dataaccess.AuthDAO;
import exception.ResponseException;
import java.util.HashMap;


public class MemoryAuthData implements AuthDAO {
    HashMap<String,AuthData> auths = new HashMap();

    public AuthData addAuthData(AuthData authData) throws ResponseException{
        auths.put(authData.authToken(),authData);
        return authData;
    }
    public AuthData getAuthData(String authToken) throws ResponseException{
        return auths.getOrDefault(authToken, null);
    }
    public Boolean deleteAuthData(String authToken) throws ResponseException{
        auths.remove(authToken);
        return Boolean.TRUE;
    }
    public void clearAuthData() throws ResponseException{
        auths.clear();
    }
}