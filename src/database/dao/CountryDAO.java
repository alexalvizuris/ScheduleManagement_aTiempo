package database.dao;

import javafx.collections.ObservableList;
import model.Country;

public abstract class CountryDAO {

    public CountryDAO() {
        super();
    }

    public abstract Country getCountry(Country country);
    public abstract ObservableList<Country> getAllCountries();



}
