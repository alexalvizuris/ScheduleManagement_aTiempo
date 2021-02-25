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

import javax.xml.stream.Location;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.TimeZone;

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
    private ComboBox<User> updateUser;

    @FXML
    private ComboBox<Customer> updateCustomer;

    @FXML
    private ComboBox<Contact> updateContact;

    @FXML
    private Button updateSave;

    @FXML
    private Button updateCancel;

    private User loggedIn;



    public void updateSaveSelected(ActionEvent event) throws IOException {

        AppointmentImpl impl = new AppointmentImpl();

        String id = updateID.getText();
        String title = updateTitle.getText();
        String description = updateDescription.getText();
        String location = updateLocation.getText();
        String type = updateType.getText();

        LocalDate startDate = updateStartDate.getValue();
        String startString = updateStartTime.getText() + ":00";
        LocalTime startTime = LocalTime.parse(startString);
        LocalDateTime start = LocalDateTime.of(startDate, startTime);

        LocalDate endDate = updateEndDate.getValue();
        String endString = updateEndTime.getText() + ":00";
        LocalTime endTime = LocalTime.parse(endString);
        LocalDateTime end = LocalDateTime.of(endDate, endTime);
        int userID = updateUser.getSelectionModel().getSelectedItem().getUserId();
        int customerID = updateCustomer.getSelectionModel().getSelectedItem().getCustomerID();
        int contactID = updateContact.getSelectionModel().getSelectedItem().getContactID();
        Timestamp update = Timestamp.valueOf(LocalDateTime.now());
        String updatedBy = loggedIn.getUserName();

        if (start.isAfter(end)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error has occurred");
            alert.setContentText("Please ensure that the START of the appointment comes before the END of the appointment.");
            alert.showAndWait();
            return;
        }

        if (start.isBefore(LocalDateTime.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error has occurred");
            alert.setContentText("Appointments cannot be made in the past.");
            alert.showAndWait();
            return;
        }


        ArrayList<Appointment> custAppt = new ArrayList<>();
        custAppt = impl.allCustomerAppt(customerID);

        for (int i = 0; i < custAppt.size(); i++) {
            if ((custAppt.get(i).getStart().isEqual(start)) || (custAppt.get(i).getEnd().isEqual(end))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("An Error has occurred");
                alert.setContentText("This customer is currently booked at the entered hour. Please enter a separate time slot.");
                alert.showAndWait();
                return;
            }
        }

        Appointment newAppointment = new Appointment(title, description, location, type, start, end);
        newAppointment.setUserID(userID);
        newAppointment.setCustomerID(customerID);
        newAppointment.setContactID(contactID);
        newAppointment.setAppointmentID(Integer.valueOf(id));
        newAppointment.setLastUpdate(update);
        newAppointment.setLastUpdatedBy(updatedBy);

        ZoneId eastCoast = ZoneId.of("America/New_York");
        ZoneId local = ZoneId.of(TimeZone.getDefault().getID());

        ZonedDateTime localDateTime = ZonedDateTime.of(startDate, startTime, local);
        ZonedDateTime toEastCoastTime = localDateTime.withZoneSameInstant(eastCoast);

        if (toEastCoastTime.toLocalTime().isBefore(LocalTime.of(8, 0)) || toEastCoastTime.toLocalTime().isAfter(LocalTime.of(21, 59))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error has occurred");
            alert.setContentText("The time you entered is outside of normal business hours. Please select a time between 8AM and 10PM EST.");
            alert.showAndWait();
            return;
        }


        impl.update(newAppointment);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/mainScreen.fxml"));

        Parent updateApptParent = loader.load();
        Scene updateApptScene = new Scene(updateApptParent);

        MainScreenController controller = loader.getController();
        controller.initialize(loggedIn);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(updateApptScene);
        stage.centerOnScreen();
        stage.show();

    }


    public void updateCancelSelected(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No data will be saved. Continue?");
        Optional<ButtonType> confirm = alert.showAndWait();

        if (confirm.isPresent() && confirm.get() == ButtonType.OK) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/mainScreen.fxml"));

            Parent updateApptParent = loader.load();
            Scene updateApptScene = new Scene(updateApptParent);

            MainScreenController controller = loader.getController();
            controller.initialize(loggedIn);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(updateApptScene);
            stage.centerOnScreen();
            stage.show();
        }
    }


    public void initApptData(Appointment appointment, User user) {

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