package database.interfaces;

import javafx.collections.ObservableList;
import model.Contact;

public interface ContactDAO {


    Contact create(Contact contact);
    Contact getContact(int contactID);
    ObservableList<Contact> getAllContacts();
    void update(Contact contact);
    void delete(int contactID);


}
