package database.interfaces;

import javafx.collections.ObservableList;
import model.Country;

/**
 * Country interface
 */
public interface CountryDAO {


    Country getCountry(int countryID);
    ObservableList<Country> getAllCountries();



}
