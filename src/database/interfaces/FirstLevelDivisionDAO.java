package database.interfaces;

import javafx.collections.ObservableList;
import model.FirstLevelDivision;

public interface FirstLevelDivisionDAO {


    FirstLevelDivision getDivision(int firstLevelDivisionID);

    ObservableList<FirstLevelDivision> getAllDivisions();


}
