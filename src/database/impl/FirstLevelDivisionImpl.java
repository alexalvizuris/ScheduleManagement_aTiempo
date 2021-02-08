package database.impl;

import database.DBConnection;
import database.DBQuery;
import database.interfaces.FirstLevelDivisionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class FirstLevelDivisionImpl extends FirstLevelDivisionDAO {

    //Initiate Read string
    private static final String GET_DIVISION = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";


    //Initiate Read All string
    private static final String GET_ALL = "SELECT * FROM first_level_divisions";


    @Override
    public FirstLevelDivision getDivision(int firstLevelDivisionID) {
        Connection conn = DBConnection.startConnection();
        FirstLevelDivision division = null;
        try (PreparedStatement statement = conn.prepareStatement(GET_DIVISION)) {
            ResultSet resultSet = DBQuery.getPreparedStatement().getResultSet();
            statement.setInt(1, firstLevelDivisionID);

            division.setDivisionID(resultSet.getInt("Division_ID"));
            division.setDivision(resultSet.getString("Division"));
            division.setCreateDate(resultSet.getObject("Create_Date", LocalDateTime.class));
            division.setCreatedBy(resultSet.getString("Created_By"));
            division.setLastUpdate(resultSet.getTimestamp("Last_Update"));
            division.setLastUpdatedBy(resultSet.getString("Last_Updated_By"));
            division.setCountryID(resultSet.getInt("Country_ID"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return division;
    }

    @Override
    public ObservableList<FirstLevelDivision> getAllDivisions() {

        Connection conn = DBConnection.startConnection();
        ObservableList<FirstLevelDivision> tempList = FXCollections.observableArrayList();
        try {
            FirstLevelDivision division = null;
            DBQuery.setPreparedStatement(conn, GET_ALL);
            ResultSet resultSet = DBQuery.getPreparedStatement().getResultSet();

            while (resultSet.next()) {
                division.setDivisionID(resultSet.getInt("Division_ID"));
                division.setDivision(resultSet.getString("Division"));
                division.setCreateDate(resultSet.getObject("Create_Date", LocalDateTime.class));
                division.setCreatedBy(resultSet.getString("Created_By"));
                division.setLastUpdate(resultSet.getTimestamp("Last_Update"));
                division.setLastUpdatedBy(resultSet.getString("Last_Updated_By"));
                division.setCountryID(resultSet.getInt("Country_ID"));
                tempList.add(division);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return tempList;
    }
}
