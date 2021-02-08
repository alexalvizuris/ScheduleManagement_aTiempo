package database.interfaces;

import javafx.collections.ObservableList;
import model.Contact;

public interface ContactDAO {


    public Contact create(Contact contact);
    public Contact getContact(int contactID);
    public ObservableList<Contact> getAllContacts();
    public void update(Contact contact);
    public void delete(int contactID);


}
