package database.dao;

import javafx.collections.ObservableList;
import model.Contact;

public abstract class ContactDAO {

    public ContactDAO() {
        super();

    }

    public abstract Contact create(Contact contact);
    public abstract Contact getContact(int contactID);
    public abstract ObservableList<Contact> getAllContacts();
    public abstract Contact update(Contact contact);
    public abstract void delete(int contactID);


}
