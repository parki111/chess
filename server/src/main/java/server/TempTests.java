package server;

import dataaccess.sqldataaccess.SqlAuthData;
import dataaccess.sqldataaccess.SqlUserData;
import exception.ResponseException;
import model.AuthData;
import model.UserData;

public class TempTests {
    public static void main(String args[]) throws ResponseException {
        SqlAuthData dataThing = new SqlAuthData();
        SqlUserData userData = new SqlUserData();
        dataThing.clearAuthData();
        dataThing.addAuthData(new AuthData("THIS IS A TOKEN OF THINGS TO COME2", "YOUR_MOM"));
        dataThing.addAuthData(new AuthData("THIS COMPUTER IS MESSED UP", "YOUR_MOM"));
        dataThing.clearAuthData();
        AuthData authData = new AuthData("THIS COMPUTER IS MESSED UP", "YOUR_MOM");
        dataThing.addAuthData(authData);
        AuthData new_authData = dataThing.getAuthData("THIS COMPUTER IS MESSED UP");
        System.out.println(new_authData.username());
        dataThing.deleteAuthData(new_authData.authToken());
        userData.clearUserData();
        userData.createUser(new UserData("parki11","fjdk","sedf@gmail.com"));
        UserData new_userData=userData.getUserData("parki11");
        System.out.println(new_userData.email());
        userData.clearUserData();


        //dataThing.clearAuthData();
    }
    };

