package database.impl;

import database.DBConnection;
import database.DBQuery;
import database.dao.CountryDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CountryImpl extends CountryDAO {

    // Initialize Read string
    private static final String GET_COUNTRY = "SELECT * FROM countries WHERE country_IC = ?";

    // Initialize Read All string
    private static final String GET_ALL = "SELECT * FROM countries";

    @Override
    public Country getCountry(Country country) {
        Connection conn = DBConnection.startConnection();
        Country tempCountry = null;
        try {
            DBQuery.setPreparedStatement(conn, GET_COUNTRY);
            ResultSet resultSet = DBQuery.getPreparedStatement().getResultSet();

            country.setCountryID(resultSet.getInt( "Country_ID"));
            country.setCountryName(resultSet.getString("Country"));
            country.setCreateDate(resultSet.getObject("Create_Date", LocalDateTime.class));
            country.setCreatedBy(resultSet.getString("Created_By"));
            country.setLastUpdate(resultSet.getTimestamp("Last_Update"));
            country.setLastUpdatedBy(resultSet.getString("Last_Updated_By"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return country;
    }



    @Override
    public ObservableList<Country> getAllCountries() {
        Connection conn = DBConnection.startConnection();
        ObservableList<Country> countries = FXCollections.observableArrayList();
        try {
            Country country = null;
            DBQuery.setPreparedStatement(conn, GET_ALL);
            ResultSet resultSet = DBQuery.getPreparedStatement().getResultSet();

            while (resultSet.next()) {
                country.setCountryID(resultSet.getInt("Country_ID"));
                country.setCountryName(resultSet.getString("Country"));
                country.setCreateDate(resultSet.getObject("Create_Date", LocalDateTime.class));
                country.setCreatedBy(resultSet.getString("Created_By"));
                country.setLastUpdate(resultSet.getTimestamp("Last_Update"));
                country.setLastUpdatedBy(resultSet.getString("Last_Updated_By"));
                countries.add(country);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return countries;
    }
}
