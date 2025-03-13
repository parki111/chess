package dataaccess.sqldataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DatabaseManager;
import dataaccess.GameDAO;
import exception.ResponseException;
import model.GameData;
import model.UserData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

import static java.sql.Types.NULL;

public class SqlGamesData implements GameDAO {
    public int createGame(String gameName) throws ResponseException{return 1;};
    public Collection<GameData> listGames() throws ResponseException{return new HashSet<GameData>();};
    public GameData findGame(int gameID) throws ResponseException {return new GameData(5,"","","",new ChessGame());};
    public Boolean updateGame(String username, String playerColor, GameData gameData) throws ResponseException{return true;};
    //    Boolean deleteGame(String username, String playerColor, GameData gameData) throws ResponseException;
    public void clearGames() throws ResponseException{

    };

    private GameData readGameData(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String whiteUsername = rs.getString("whiteUsername");
        String blackUsername = rs.getString("blackUsername");
        String gameName = rs.getString("gameName");
        String json = rs.getString("json");
        ChessGame chessgame = new Gson().fromJson(json, ChessGame.class);
        return new GameData(id,whiteUsername,blackUsername,gameName,chessgame);
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  userData (
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



    private void executeUpdate(String statement, Object... params) throws ResponseException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ResponseException(500, String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }
}
