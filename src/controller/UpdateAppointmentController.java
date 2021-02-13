package controller;

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
import java.time.LocalTime;
import java.util.Optional;

public class UpdateAppointmentController {

    @FXML
    private TextField updateID;

    @FXML
    private TextField updateTitle;

    @FXML
    private TextArea updateDescription;

    @FXML
    private TextField updateLocation;

    @FXML
    private TextField updateType;

    @FXML
    private DatePicker updateStartDate;

    @FXML
    private DatePicker updateEndDate;

    @FXML
    private TextField updateStartTime;

    @FXML
    private TextField updateEndTime;

    @FXML
    private ChoiceBox<User> updateUser;

    @FXML
    private ChoiceBox<Customer> updateCustomer;

    @FXML
    private ChoiceBox<Contact> updateContact;

    @FXML
    private Button updateSave;

    @FXML
    private Button updateCancel;






    public void updateCancelSelected(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No data will be saved. Continue?");
        Optional<ButtonType> confirm = alert.showAndWait();

        if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
            Parent updateApptParent = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
            Scene updateApptScene = new Scene(updateApptParent);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(updateApptScene);
            stage.show();
        }
    }


    public void initApptData(Appointment appointment) {


        CustomerImpl custI = new CustomerImpl();
        UserImpl userI = new UserImpl();
        ContactImpl contactI = new ContactImpl();
        ObservableList<User> userChoices = FXCollections.observableArrayList();
        ObservableList<Customer> custChoices = FXCollections.observableArrayList();
        ObservableList<Contact> contactChoices = FXCollections.observableArrayList();
        userChoices = userI.getAllUsers();

        custChoices = custI.allCustomers();
        contactChoices = contactI.getAllContacts();

        updateUser.setItems(userChoices);
        updateContact.setItems(contactChoices);
        updateCustomer.setItems(custChoices);

        updateID.setText(String.valueOf(appointment.getAppointmentID()));
        updateTitle.setText(appointment.getTitle());
        updateDescription.setText(appointment.getDescription());
        updateLocation.setText(appointment.getLocation());
        updateType.setText(appointment.getType());
        LocalTime startTime = appointment.getStart().toLocalTime();
        updateStartDate.setValue(appointment.getStart().toLocalDate());
        updateStartTime.setText(startTime.toString());
        LocalTime endTime = appointment.getEnd().toLocalTime();
        updateEndDate.setValue(appointment.getStart().toLocalDate());
        updateEndTime.setText(endTime.toString());

        updateUser.setValue(userI.getUser(appointment.getUserID()));
        updateCustomer.setValue(custI.getCustomer(appointment.getCustomerID()));
        updateContact.setValue(contactI.getContact(appointment.getContactID()));



    }



}