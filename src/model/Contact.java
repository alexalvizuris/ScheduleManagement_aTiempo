package model;

public class Contact {

    private int contactID;
    private String contactName;
    private String contactEmail;

    public Contact(String contactName, String contactEmail) {

        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Override
    public String toString() {
        return contactName;
    }
}
