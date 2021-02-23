package controller;

import database.impl.AppointmentImpl;
import database.impl.ContactImpl;
import database.impl.CustomerImpl;
import database.impl.UserImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

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
    public ComboBox<User> newUser;

    @FXML
    public ComboBox<Customer> newCustomer;

    @FXML
    public ComboBox<Contact> newContact;

    @FXML
    private Button addApptSave;

    @FXML
    private Button addApptCancel;

    private User loggedIn;




    public void addApptSaveSelected(ActionEvent event) throws IOException {
        AppointmentImpl impl = new AppointmentImpl();

        String title = newTitle.getText();
        String description = newDescription.getText();
        String location = newLocation.getText();
        String type = newType.getText();

        LocalDate startDate = newStartDate.getValue();
        String startString = newStartTime.getText() + ":00";
        LocalTime startTime = LocalTime.parse(startString);
        LocalDateTime start = LocalDateTime.of(startDate, startTime);

        LocalDate endDate = newEndDate.getValue();
        String endString = newEndTime.getText() + ":00";
        LocalTime endTime = LocalTime.parse(endString);
        LocalDateTime end = LocalDateTime.of(endDate, endTime);
        int userID = loggedIn.getUserId();
        int customerID = newCustomer.getSelectionModel().getSelectedItem().getCustomerID();
        int contactID = newContact.getSelectionModel().getSelectedItem().getContactID();
        String createdBy = loggedIn.getUserName();
        String updatedBy = loggedIn.getUserName();

        Appointment newAppointment = new Appointment(title, description, location, type, start, end);
        newAppointment.setUserID(userID);
        newAppointment.setCustomerID(customerID);
        newAppointment.setContactID(contactID);
        newAppointment.setCreatedBy(createdBy);
        newAppointment.setLastUpdatedBy(updatedBy);

        impl.create(newAppointment);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/mainScreen.fxml"));
        Parent addApptParent = loader.load();
        Scene addApptScene = new Scene(addApptParent);

        MainScreenController controller = loader.getController();
        controller.initialize(loggedIn);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(addApptScene);
        stage.centerOnScreen();
        stage.show();


    }

    public void addApptCancelSelected(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No data will be saved. Continue?");
        Optional<ButtonType> confirm = alert.showAndWait();

        if (confirm.isPresent() && confirm.get() == ButtonType.OK) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/mainScreen.fxml"));
            Parent addApptParent = loader.load();
            Scene addApptScene = new Scene(addApptParent);

            MainScreenController controller = loader.getController();
            controller.initialize(loggedIn);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(addApptScene);
            stage.centerOnScreen();
            stage.show();
        }

    }


    public void initialize(User user) {

        loggedIn = user;
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
        newUser.setValue(user);
        newContact.setItems(contactChoices);
        newCustomer.setItems(custChoices);


    }

}