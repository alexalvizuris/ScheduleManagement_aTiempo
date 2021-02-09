package database.impl;

import database.DBConnection;
import database.DBQuery;
import database.interfaces.CountryDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.*;
import java.time.LocalDateTime;

public class CountryImpl implements CountryDAO {

    // Initialize Read string
    private static final String GET_COUNTRY = "SELECT * FROM countries WHERE Country_ID = ?";

    // Initialize Read All string
    private static final String GET_ALL = "SELECT * FROM countries";




    public Country getCountry(int countryID) {
        Connection conn = DBConnection.startConnection();

        try (PreparedStatement statement = conn.prepareStatement(GET_COUNTRY)) {
            ResultSet resultSet = statement.executeQuery();
            statement.setInt(1, countryID);

            while (resultSet.next()) {
                int id = resultSet.getInt("Country_ID");
                String name = resultSet.getString("Country");
                LocalDateTime create = resultSet.getObject("Create_Date", LocalDateTime.class);
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
                String updatedBy = resultSet.getString("Last_Updated_By");

                Country country = new Country(name);
                country.setCountryID(id);
                country.setCreateDate(create);
                country.setCreatedBy(createdBy);
                country.setLastUpdate(lastUpdate);
                country.setLastUpdatedBy(updatedBy);

                return country;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return null;
    }




    public ObservableList<Country> getAllCountries() {
        Connection conn = DBConnection.startConnection();
        ObservableList<Country> countries = FXCollections.observableArrayList();
        try (PreparedStatement statement = conn.prepareStatement(GET_ALL)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("Country_ID");
                String name = resultSet.getString("Country");
                LocalDateTime create = resultSet.getObject("Create_Date", LocalDateTime.class);
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
                String updatedBy = resultSet.getString("Last_Updated_By");

                Country country = new Country(name);
                country.setCountryID(id);
                country.setCreateDate(create);
                country.setCreatedBy(createdBy);
                country.setLastUpdate(lastUpdate);
                country.setLastUpdatedBy(updatedBy);

                countries.add(country);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return countries;
    }
}
