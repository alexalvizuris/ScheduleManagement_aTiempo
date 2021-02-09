package database.interfaces;

import javafx.collections.ObservableList;
import model.User;

public interface UserDAO {


    User create(User user);
    User getUser(int userID);
    ObservableList<User> getAllUsers();
    void update(User user);
    void delete(int userID);

}
