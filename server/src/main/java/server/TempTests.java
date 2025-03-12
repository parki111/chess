package server;

import dataaccess.sqldataaccess.SqlAuthData;
import exception.ResponseException;
import model.AuthData;

public class TempTests {
    public static void main(String args[]) throws ResponseException {
        SqlAuthData dataThing = new SqlAuthData();
        dataThing.addAuthData(new AuthData("THIS IS A TOKEN OF THINGS TO COME2", "YOUR_MOM"));
        dataThing.addAuthData(new AuthData("THIS COMPUTER IS MESSED UP", "YOUR_MOM"));
        //dataThing.clearAuthData();
    }
}
