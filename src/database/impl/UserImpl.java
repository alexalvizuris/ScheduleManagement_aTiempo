package database.impl;

import database.DBConnection;
import database.interfaces.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.*;
import java.time.LocalDateTime;

public class UserImpl implements UserDAO {

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




    public User getUser(int userID) {
        Connection conn = DBConnection.startConnection();
        try (PreparedStatement statement = conn.prepareStatement(GET_USER)) {
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("User_ID");
                String userName = resultSet.getString("User_Name");
                String password = resultSet.getString("Password");
                LocalDateTime created = resultSet.getObject("Create_Date", LocalDateTime.class);
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
                String updatedBy = resultSet.getString("Last_Updated_By");

                User user = new User(userName, password);
                user.setUserId(id);
                user.setCreateDate(created);
                user.setCreatedBy(createdBy);
                user.setLastUpdate(lastUpdate);
                user.setLastUpdatedBy(updatedBy);

                return user;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return null;
    }


    public ObservableList<User> getAllUsers() {
        Connection conn = DBConnection.startConnection();
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        try (PreparedStatement statement = conn.prepareStatement(GET_ALL)){
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("User_ID");
                String userName = resultSet.getString("User_Name");
                String password = resultSet.getString("Password");
                LocalDateTime created = resultSet.getObject("Create_Date", LocalDateTime.class);
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
                String updatedBy = resultSet.getString("Last_Updated_By");

                User user = new User(userName, password);
                user.setUserId(id);
                user.setCreateDate(created);
                user.setCreatedBy(createdBy);
                user.setLastUpdate(lastUpdate);
                user.setLastUpdatedBy(updatedBy);

                allUsers.add(user);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return allUsers;
    }


    public void update(User user) {
        Connection conn = DBConnection.startConnection();
        try (PreparedStatement statement = conn.prepareStatement(UPDATE)) {

            int id = user.getUserId();
            String userName = user.getUserName();
            String password = user.getPassword();
            LocalDateTime created = user.getCreateDate();
            String createdBy = user.getCreatedBy();
            Timestamp updated = java.sql.Timestamp.valueOf(LocalDateTime.now());
            String updatedBy = user.getLastUpdatedBy();

            statement.setString(1, userName);
            statement.setString(2, password);
            statement.setTimestamp(3, Timestamp.valueOf(created));
            statement.setString(4, createdBy);
            statement.setTimestamp(5, updated);
            statement.setString(6, updatedBy);
            statement.setInt(7, id);
            statement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
    }


    public void delete(int userID) {
        Connection conn = DBConnection.startConnection();
        try (PreparedStatement statement = conn.prepareStatement(DELETE)) {
            statement.setInt(1, userID);
            statement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
    }
}
