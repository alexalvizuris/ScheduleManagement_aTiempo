package database.impl;

import database.DBConnection;
import database.interfaces.FirstLevelDivisionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;
import java.sql.*;
import java.time.LocalDateTime;


/**
 * First Level Division implementation class for FLD DAO
 */
public class FirstLevelDivisionImpl implements FirstLevelDivisionDAO {

    //Initiate Read string
    private static String GET_DIVISION = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";


    //Initiate Read All string
    private static final String GET_ALL = "SELECT * FROM first_level_divisions";


    /**
     * Read statement from first level division table
     * @param firstLevelDivisionID references division being read
     * @return first level division selected
     * @throws SQLException when issues occur with reading from the database
     */
    public FirstLevelDivision getDivision(int firstLevelDivisionID) {
        Connection conn = DBConnection.startConnection();

        try (PreparedStatement statement = conn.prepareStatement(GET_DIVISION)) {
            statement.setInt(1, firstLevelDivisionID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("Division_ID");
                String name = resultSet.getString("Division");
                LocalDateTime created = resultSet.getObject("Create_Date", LocalDateTime.class);
                String createdBy = resultSet.getString("Created_By");
                Timestamp updated = resultSet.getTimestamp("Last_Update");
                String updatedBy = resultSet.getString("Last_Updated_By");
                int countryID = resultSet.getInt("Country_ID");

                FirstLevelDivision division = new FirstLevelDivision(name);
                division.setDivisionID(id);
                division.setCreateDate(created);
                division.setCreatedBy(createdBy);
                division.setLastUpdate(updated);
                division.setLastUpdatedBy(updatedBy);
                division.setCountryID(countryID);

                return division;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return null;
    }


    /**
     * Read all statement from first level division table in the database
     * @return selected division
     * @throws SQLException when issues occur with reading from the database
     */
    public ObservableList<FirstLevelDivision> getAllDivisions() {

        Connection conn = DBConnection.startConnection();
        ObservableList<FirstLevelDivision> tempList = FXCollections.observableArrayList();
        try (PreparedStatement statement = conn.prepareStatement(GET_ALL)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("Division_ID");
                String name = resultSet.getString("Division");
                LocalDateTime created = resultSet.getObject("Create_Date", LocalDateTime.class);
                String createdBy = resultSet.getString("Created_By");
                Timestamp updated = resultSet.getTimestamp("Last_Update");
                String updatedBy = resultSet.getString("Last_Updated_By");
                int countryID = resultSet.getInt("Country_ID");

                FirstLevelDivision division = new FirstLevelDivision(name);
                division.setDivisionID(id);
                division.setCreateDate(created);
                division.setCreatedBy(createdBy);
                division.setLastUpdate(updated);
                division.setLastUpdatedBy(updatedBy);
                division.setCountryID(countryID);


                tempList.add(division);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return tempList;
    }
}
