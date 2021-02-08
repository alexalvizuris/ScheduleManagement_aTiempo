package database.dao;

import javafx.collections.ObservableList;
import model.FirstLevelDivision;

public abstract class FirstLevelDivisionDAO {

    public FirstLevelDivisionDAO() {
        super();

    }

    public abstract FirstLevelDivision getDivision(FirstLevelDivision firstLevelDivision);

    public abstract ObservableList<FirstLevelDivision> getAllDivisions();


}
