package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQuery {

    // Statement reference
    private static Statement statement;

    // Create Statement object
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {

        statement = conn.prepareStatement(sqlStatement);

    }

    // Return Statement object
    public static Statement getPreparedStatement() {
        return statement;
    }
}
