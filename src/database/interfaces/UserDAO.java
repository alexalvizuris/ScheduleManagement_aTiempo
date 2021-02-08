package database.interfaces;

import javafx.collections.ObservableList;
import model.User;

public abstract class UserDAO {

    public UserDAO() {
        super();
    }

    public abstract User create(User user);
    public abstract User getUser(int userID);
    public abstract ObservableList<User> getAllUsers();
    public abstract User update(User user);
    public abstract void delete(int userID);

}
