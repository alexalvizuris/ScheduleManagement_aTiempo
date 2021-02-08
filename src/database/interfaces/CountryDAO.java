package database.interfaces;

import javafx.collections.ObservableList;
import model.Country;

public abstract class CountryDAO {

    public CountryDAO() {
        super();
    }

    public abstract Country getCountry(int countryID);
    public abstract ObservableList<Country> getAllCountries();



}
