package dataaccess.sqldataaccess;

import dataaccess.DatabaseManager;
import dataaccess.UserDAO;
import exception.ResponseException;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Types.NULL;

public class SqlUserData implements UserDAO {

    public SqlUserData() throws ResponseException {
        configureDatabase();
    };

    public Boolean createUser(UserData user) throws ResponseException{
        var statement = "INSERT INTO userData (username, password, email) VALUES (?, ?, ?)";
        String encrypted_password = BCrypt.hashpw(user.password(), BCrypt.gensalt());
        executeUpdate(statement, user.username(), encrypted_password, user.email());
        return true;
    };
    public UserData getUserData(String username) throws ResponseException{

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password,email FROM userData WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readUserData(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(500, String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;

    };


    public void clearUserData() throws ResponseException{
        var statement = "TRUNCATE userData";
        executeUpdate(statement);
    };

    private UserData readUserData(ResultSet rs) throws SQLException {
        var username = rs.getString("username");
        var password = rs.getString("password");
        var email = rs.getString("email");
        return new UserData(username,password,email);
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  userData (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              PRIMARY KEY (`username`)
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
