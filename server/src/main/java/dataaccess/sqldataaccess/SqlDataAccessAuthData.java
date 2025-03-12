package dataaccess.sqldataaccess;
import com.google.gson.Gson;
import dataaccess.DatabaseManager;
import exception.ResponseException;
import model.AuthData;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlDataAccessAuthData {

    public void MySqlDataAccess() throws ResponseException {
        configureDatabase();
    }
    public AuthData addAuthData(AuthData authData) throws ResponseException{return new AuthData("","");};
    public AuthData getAuthData(String authToken) throws ResponseException{
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT id, json FROM pet WHERE id=?";
            try (var ps = conn.prepareStatement(statement)) {
                //ps.setInt(1, id);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuthData(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(500, String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
        //return new AuthData("","");
    };
    public Boolean deleteAuthData(String authToken) throws ResponseException{return true;};
    public void clearAuthData() throws ResponseException{};

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  pet (
              `id` int NOT NULL AUTO_INCREMENT,
              `name` varchar(256) NOT NULL,
              `type` ENUM('CAT', 'DOG', 'FISH', 'FROG', 'ROCK') DEFAULT 'CAT',
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`id`),
              INDEX(type),
              INDEX(name)
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

    private AuthData readAuthData(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        var json = rs.getString("json");
        var authData = new Gson().fromJson(json, AuthData.class);
        return authData;
    }
}
