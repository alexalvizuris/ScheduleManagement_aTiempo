package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class for connection statements to the database
 */
public class DBConnection {
    // JDBC URL PARTS
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/WJ061Y1";

    // JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress;

    // Driver interface reference
    private static final String MYSQLJDBCDRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;

    private static final String username = "U061Y1";
    private static final String password = "53688671873";

    /**
     * Creates a connection to the database
     * @return connection
     * @throws SQLException when issues occur with reading from the database
     */
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDRIVER);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection Successful!");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Closes the connection to the database
     * @throws SQLException when issues occur with reading from the database
     */
    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
