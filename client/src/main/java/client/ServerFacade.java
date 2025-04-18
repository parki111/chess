package client;

import chess.ChessGame;
import com.google.gson.Gson;
import exception.ErrorResponse;
import exception.ResponseException;
import model.AuthData;
import model.GameData;
import model.UserData;
import requestresponse.*;

import java.io.*;
import java.net.*;
import java.util.Collection;
import java.util.Map;

public class ServerFacade {

    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }


    public RegisterResult register(RegisterRequest request) throws ResponseException {
        var path = "/user";
        return this.makeRequest("POST", path, request, null, RegisterResult.class);
    }

    public LoginResult login(LoginRequest request) throws ResponseException {
        var path = "/session";
        return this.makeRequest("POST", path, request, null, LoginResult.class);
    }

    public void logout(LogoutRequest request) throws ResponseException {
        var path = "/session";
        this.makeRequest("DELETE", path, null, request.authToken(),null);
    }

    public void clear() throws ResponseException {
        var path = "/db";
        this.makeRequest("DELETE", path, null,null,null);
    }

    public ListGamesResult listGames(ListGamesRequest request) throws ResponseException {
        var path = "/game";
        return this.makeRequest("GET", path, null, request.authToken(), ListGamesResult.class);
    }

    public CreateGameResult createGame(CreateGameRequest request) throws ResponseException {
        var path = "/game";

        return this.makeRequest("POST", path, request, request.authToken(), CreateGameResult.class);
    }

    public ChessGame joinGame(JoinGameRequest request) throws ResponseException {
        var path = "/game";
        return this.makeRequest("PUT", path, request, request.authToken(), null);
    }

    private <T> T makeRequest(String method, String path, Object body, String header, Class<T> responseClass) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            if (header !=null && !header.isEmpty()){
                http.setRequestProperty("Authorization", String.format("%s",header));
            }

            writeBody(body, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }


    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            try (InputStream respErr = http.getErrorStream()) {
                InputStreamReader reader = new InputStreamReader(respErr);
                if (respErr != null && reader!=null) {
                    ErrorMessage error = new Gson().fromJson(reader,ErrorMessage.class);
                    throw new ResponseException(status,error.message());
                }
            }
            throw new ResponseException(status, "other failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }


    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}