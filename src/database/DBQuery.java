package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for executing statements to the database
 */
public class DBQuery {

    // Statement reference
    private static Statement statement;

    /**
     * Creates prepared statement for database
     * @param conn references instance of connection
     * @param sqlStatement references SQL variable for the database
     * @throws SQLException when issues occur with reading from the database
     */
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {

        statement = conn.prepareStatement(sqlStatement);

    }

    /**
     * Gets prepared statement when called
     * @return prepared statement instance
     */
    public static Statement getPreparedStatement() {
        return statement;
    }
}
