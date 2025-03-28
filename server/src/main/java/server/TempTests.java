package server;

import dataaccess.sqldataaccess.SqlAuthData;
import dataaccess.sqldataaccess.SqlUserData;
import dataaccess.sqldataaccess.SqlGamesData;
import exception.ResponseException;
import model.AuthData;
import model.UserData;
import model.GameData;

public class TempTests {
    public static void main(String args[]) throws ResponseException {
        SqlAuthData dataThing = new SqlAuthData();
        SqlUserData userData = new SqlUserData();
        SqlGamesData gamesData = new SqlGamesData();
        dataThing.clearAuthData();
        dataThing.addAuthData(new AuthData("THIS IS A TOKEN OF THINGS TO COME2", "YOUR_MOM"));
        dataThing.addAuthData(new AuthData("THIS COMPUTER IS MESSED UP", "YOUR_MOM"));
        dataThing.clearAuthData();
        AuthData authData = new AuthData("THIS COMPUTER IS MESSED UP", "YOUR_MOM");
        dataThing.addAuthData(authData);
        AuthData newAuthData = dataThing.getAuthData("THIS COMPUTER IS MESSED UP");
        System.out.println(newAuthData.username());
        dataThing.deleteAuthData(newAuthData.authToken());
        userData.clearUserData();
        userData.createUser(new UserData("parki11","fjdk","sedf@gmail.com"));
        UserData newUserData=userData.getUserData("parki11");
        System.out.println(newUserData.email());
        userData.clearUserData();
        gamesData.createGame("game1");
        GameData game=gamesData.findGame(1);
        System.out.println(game.gameName());


        //dataThing.clearAuthData();
    }
    };

