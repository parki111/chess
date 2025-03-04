package server;
import exception.ResponseException;
import spark.*;
import java.util.Map;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");


        // Register your endpoints and handle exceptions here.

//        Spark.delete("/db",this::handlerClear);
//        Spark.post("/user",this::)
//        Spark.exception(ResponseException.class, this::handleException)
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
