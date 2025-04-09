package server;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import dataaccess.memorydataaccess.MemoryAuthData;
import dataaccess.memorydataaccess.MemoryGamesData;
import dataaccess.memorydataaccess.MemoryUserData;
import dataaccess.sqldataaccess.SqlAuthData;
import dataaccess.sqldataaccess.SqlGamesData;
import dataaccess.sqldataaccess.SqlUserData;
import exception.ResponseException;
import server.websocket.WebSocketHandler;
import server.websocket.WebSocketSessions;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);
        AuthDAO authDAO;
        UserDAO userDAO;
        GameDAO gameDAO;
        WebSocketHandler webSocketHandler;
        webSocketHandler = new WebSocketHandler();
        try {
            authDAO = new SqlAuthData();
            userDAO = new SqlUserData();
            gameDAO = new SqlGamesData();
        }
        catch (Throwable Exception) {
            authDAO = new MemoryAuthData();
            userDAO = new MemoryUserData();
            gameDAO = new MemoryGamesData();
        }
        Handler handler = new Handler(authDAO,userDAO,gameDAO);
        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.webSocket("/ws", webSocketHandler);
        Spark.delete("/db", handler::clear);
        Spark.post("/user",handler::register);
        Spark.post("/session",handler::login);
        Spark.delete("/session",handler::logout);
        Spark.get("/game",handler::listGames);
        Spark.post("/game",handler::createGame);
        Spark.put("/game",handler::joinGame);
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
