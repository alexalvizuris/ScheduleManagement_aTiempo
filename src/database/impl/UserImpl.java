package database.impl;

import database.DBConnection;
import database.interfaces.UserDAO;
import javafx.collections.ObservableList;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserImpl extends UserDAO {

    // Initializes Create string
    private static final String INSERT = "INSERT INTO users (User_Name, Password, Create_Date, " +
        "Created_By, Last_Update, Last_Updated_By) VALUES (?, ?, ?, ?, ?, ?)";

    // Initializes Read string
    private static final String GET_USER = "SELECT * FROM users WHERE User_ID = ?";

    // Initializes Read All string
    private static final String GET_ALL = "SELECT * FROM users";

    // Initializes Update string
    private static final String UPDATE = "UPDATE users SET User_Name = ?, Password = ?, Create_Date = ? " +
            "Created_By = ?, Last_Update = ?, Last_Updated_By = ? WHERE User_ID = ?";

    // Initializes Delete string
    private static final String DELETE = "DELETE FROM users WHERE User_ID = ?";

    @Override
    public User create(User user) {
        Connection conn = DBConnection.startConnection();
        try (PreparedStatement statement = conn.prepareStatement(INSERT)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(user.getCreateDate()));
            statement.setString(4, user.getCreatedBy());
            statement.setTimestamp(5, user.getLastUpdate());
            statement.setString(6, user.getLastUpdatedBy());
            statement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return user;
    }



    @Override
    public User getUser(int userID) {
        return null;
    }

    @Override
    public ObservableList<User> getAllUsers() {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(int userID) {

    }
}
