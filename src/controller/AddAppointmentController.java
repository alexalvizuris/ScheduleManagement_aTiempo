package controller;

import database.impl.ContactImpl;
import database.impl.CustomerImpl;
import database.impl.UserImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Contact;
import model.Customer;
import model.User;

import java.time.LocalDateTime;

public class AddAppointmentController {

    @FXML
    private TextField newTitle;

    @FXML
    private TextArea newDescription;

    @FXML
    private TextField newLocation;

    @FXML
    private TextField newType;

    @FXML
    private DatePicker newStartDate;

    @FXML
    private DatePicker newEndDate;

    @FXML
    private TextField newStartTime;

    @FXML
    private TextField newEndTime;

    @FXML
    private ChoiceBox<User> newUser;

    @FXML
    private ChoiceBox<Customer> newCustomer;

    @FXML
    private ChoiceBox<Contact> newContact;

    @FXML
    private Button addApptSave;

    @FXML
    private Button addApptCancel;

    public void addApptSaveSelected(ActionEvent event) {

        String title = newTitle.getText();
        String description = newDescription.getText();
        String location = newLocation.getText();
        String type = newType.getText();
        String startDateTime = newStartDate.toString() + " " + newStartTime.toString();
        LocalDateTime start = LocalDateTime.parse(startDateTime);
        String endDateTime = newEndDate.toString() + " " + newEndTime.toString();
        LocalDateTime end = LocalDateTime.parse(endDateTime);



    }

    public void initAddAppt() {
        CustomerImpl custI = new CustomerImpl();
        UserImpl userI = new UserImpl();
        ContactImpl contactI = new ContactImpl();

        ObservableList<User> userChoices = FXCollections.observableArrayList();
        ObservableList<Customer> custChoices = FXCollections.observableArrayList();
        ObservableList<Contact> contactChoices = FXCollections.observableArrayList();

        userChoices = userI.getAllUsers();
        custChoices = custI.allCustomers();
        contactChoices = contactI.getAllContacts();

        newUser.setItems(userChoices);
        newContact.setItems(contactChoices);
        newCustomer.setItems(custChoices);

        //change the above code to display the objects in the selected choice boxes

    }

}