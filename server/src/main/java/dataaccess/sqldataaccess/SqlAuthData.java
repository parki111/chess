package dataaccess.sqldataaccess;
import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DatabaseManager;
import exception.ResponseException;
import model.AuthData;

import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Types.NULL;

public class SqlAuthData implements AuthDAO {

    public SqlAuthData() throws ResponseException {
        configureDatabase();
    }

    public AuthData addAuthData(AuthData authData) throws ResponseException{return new AuthData("","");};
    public AuthData getAuthData(String authToken) throws ResponseException{
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT authToken, username FROM authData WHERE authToken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
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
            CREATE TABLE IF NOT EXISTS  authData (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (`authToken`),
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

    private int executeUpdate(String statement, Object... params) throws ResponseException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
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
