package database.interfaces;

import javafx.collections.ObservableList;
import model.FirstLevelDivision;

/**
 * First Level Division interface
 */
public interface FirstLevelDivisionDAO {


    FirstLevelDivision getDivision(int firstLevelDivisionID);

    ObservableList<FirstLevelDivision> getAllDivisions();


}
