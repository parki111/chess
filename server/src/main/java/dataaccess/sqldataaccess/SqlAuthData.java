package dataaccess.sqldataaccess;
import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DatabaseManager;
import exception.ResponseException;
import model.AuthData;

import static dataaccess.sqldataaccess.SqlGamesData.configureDatabase;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Types.NULL;

public class SqlAuthData implements AuthDAO {

    public SqlAuthData() throws ResponseException {
        System.out.println("HELLO WORLD");
        configureDatabase(createStatements);
    }

    public AuthData addAuthData(AuthData authData) throws ResponseException{
        var statement = "INSERT INTO authData (authToken, username) VALUES (?, ?)";
        executeUpdate(statement, authData.authToken(), authData.username());
        return new AuthData(authData.authToken(), authData.username());
    };

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

    private AuthData readAuthData(ResultSet rs) throws SQLException {
        var authToken = rs.getString("authToken");
        var username = rs.getString("username");
        return new AuthData(authToken,username);
    }

    public Boolean deleteAuthData(String authToken) throws ResponseException{
        var statement = "DELETE FROM authData WHERE authToken=?";
        executeUpdate(statement, authToken);
        return Boolean.TRUE;
    };

    public void clearAuthData() throws ResponseException{
        var statement = "TRUNCATE authData";
        executeUpdate(statement);
    };

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  authData (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (`authToken`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };




    private void executeUpdate(String statement, Object... params) throws ResponseException {
        try (var connect = DatabaseManager.getConnection()) {
            try (var ps = connect.prepareStatement(statement)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) {ps.setString(i + 1, p);}
                    else if (param == null) {ps.setString(i + 1, null);}
                }
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ResponseException(500, String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }
}
