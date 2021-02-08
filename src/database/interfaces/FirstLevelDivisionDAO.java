package database.interfaces;

import javafx.collections.ObservableList;
import model.FirstLevelDivision;

public abstract class FirstLevelDivisionDAO {

    public FirstLevelDivisionDAO() {
        super();

    }

    public abstract FirstLevelDivision getDivision(int firstLevelDivisionID);

    public abstract ObservableList<FirstLevelDivision> getAllDivisions();


}
