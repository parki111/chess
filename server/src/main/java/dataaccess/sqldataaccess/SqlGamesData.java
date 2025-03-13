package dataaccess.sqldataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DatabaseManager;
import dataaccess.GameDAO;
import exception.ResponseException;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;



public class SqlGamesData implements GameDAO {
    public SqlGamesData() throws ResponseException {
        configureDatabase();
    };

    public int createGame(String gameName) throws ResponseException{
        var statement = "INSERT INTO gameData (whiteUsername,blackUsername,gameName,chessgame) VALUES (?, ?, ?, ?)";

        return executeUpdate(statement,"null","null",gameName,(new Gson()).toJson(new ChessGame()));
    };


    public Collection<GameData> listGames() throws ResponseException{
        var result = new ArrayList<GameData>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT id, whiteUsername, blackUsername, gameName, chessgame FROM gameData";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.add(readGameData(rs));
                        System.out.println("Number of games: " + result.size());
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(500, String.format("Unable to read data: %s", e.getMessage()));
        }
        return result;}

    public GameData findGame(int gameID) throws ResponseException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT id, whiteUsername, blackUsername,gameName,chessgame FROM gameData WHERE id=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGameData(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(500, String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    };

    public Boolean updateGame(String username, String playerColor, GameData gameData) throws ResponseException{
        GameData newGame;
        if (Objects.equals(playerColor, "BLACK")){
            if (!Objects.equals(gameData.blackUsername(), null)){
                throw new ResponseException(403,"Error: already taken");
            }
            newGame = new GameData(gameData.gameID(), gameData.whiteUsername(), username, gameData.gameName(), gameData.game());
            String sql = "UPDATE gameData SET whiteUsername = ?, blackUsername=?, gameName=?, chessgame=? WHERE id = ?";
            executeUpdate(sql,newGame.whiteUsername(),newGame.blackUsername(),newGame.gameName(),newGame.game(),gameData.gameID());
            return true;
        }
        else if (Objects.equals(playerColor, "WHITE")){
            if (!Objects.equals(gameData.whiteUsername(), null)){
                throw new ResponseException(403,"Error: already taken");
            }
            newGame = new GameData(gameData.gameID(), username, gameData.blackUsername(), gameData.gameName(), gameData.game());
            String sql = "UPDATE gameData SET whiteUsername = ?, blackUsername=?, gameName=?, chessgame=? WHERE id = ?";
            executeUpdate(sql,newGame.whiteUsername(),newGame.blackUsername(),newGame.gameName(),newGame.game(),gameData.gameID());
            return true;
        }
        else{
            throw new ResponseException(400,"Error: bad request");
        }
        //games.put(gameData.gameID(),newGame);
    }


    public void clearGames() throws ResponseException{
        var statement = "TRUNCATE gameData";
        executeUpdate(statement);
    };

    private GameData readGameData(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String whiteUsername = rs.getString("whiteUsername");
        String blackUsername = rs.getString("blackUsername");
        String gameName = rs.getString("gameName");
        String json = rs.getString("chessgame");
        ChessGame chessgame = new Gson().fromJson(json, ChessGame.class);
        return new GameData(id,whiteUsername,blackUsername,gameName,chessgame);
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  gameData (
              `id` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(256) NOT NULL,
              `blackUsername` varchar(256) NOT NULL,
              `gameName` varchar(256) NOT NULL,
              `chessgame` JSON NOT NULL,
              PRIMARY KEY (`id`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureDatabase() throws ResponseException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new ResponseException(500, String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }



    private int executeUpdate(String statement, Object... params) throws ResponseException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param instanceof ChessGame p) ps.setString(i + 1, p.toString());
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException e) {
            throw new ResponseException(500, String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }
}
