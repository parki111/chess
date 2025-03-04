package server;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import dataaccess.memorydataaccess.MemoryAuthData;
import dataaccess.memorydataaccess.MemoryGamesData;
import dataaccess.memorydataaccess.MemoryUserData;
import exception.ResponseException;
import spark.*;

import java.util.Map;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);
        AuthDAO authDAO = new MemoryAuthData();
        UserDAO userDAO = new MemoryUserData();
        GameDAO gameDAO = new MemoryGamesData();
        Handler handler = new Handler(authDAO,userDAO,gameDAO);

        Spark.staticFiles.location("web");


        // Register your endpoints and handle exceptions here.

        Spark.delete("/db", handler::clear);
        Spark.post("/user",handler::register);
        Spark.post("/session",handler::login);
        Spark.delete("/session",handler::logout);
        Spark.get("/game",handler::listgames);
//        Spark.post("/game")
        Spark.exception(ResponseException.class, handler::exceptionHandler);
        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
//    private Object handleClearAll
}
