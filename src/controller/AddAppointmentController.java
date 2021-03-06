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
import java.time.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.TimeZone;


/**
 * Controller for the Add New Appointment Screen
 */

public class AddAppointmentController {

    @FXML
    private TextField newTitle;

    @FXML
    private TextArea newDescription;

    @FXML
    private TextField newLocation;

    @FXML
    private ComboBox<String> newType;

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


    /**
     * Selecting this will add the appointment to the database, then switch back to the Main Screen.
     * @param event created to initiate the SAVE method on the Add Appointment Screen.
     * @throws IOException when criteria has not been met to allow the appointment to save successfully.
     */
    public void addApptSaveSelected(ActionEvent event) throws IOException {


       try {
           String title = newTitle.getText();
           String description = newDescription.getText();
           String location = newLocation.getText();
           String type = newType.getSelectionModel().getSelectedItem();

           LocalDate startDate = newStartDate.getValue();
           String startTimeString = newStartTime.getText() + ":00";
           LocalTime startTime = LocalTime.parse(startTimeString);
           LocalDateTime start = LocalDateTime.of(startDate, startTime);

           LocalDate endDate = newEndDate.getValue();
           String endTimeString = newEndTime.getText() + ":00";
           LocalTime endTime = LocalTime.parse(endTimeString);
           LocalDateTime end = LocalDateTime.of(endDate, endTime);
           int userID = loggedIn.getUserId();
           int customerID = newCustomer.getSelectionModel().getSelectedItem().getCustomerID();
           int contactID = newContact.getSelectionModel().getSelectedItem().getContactID();
           String createdBy = loggedIn.getUserName();
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

           AppointmentImpl impl = new AppointmentImpl();
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
           newAppointment.setCreatedBy(createdBy);
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
       } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error has occurred");
            alert.setContentText("Please enter valid input");
            alert.showAndWait();
            return;
        }


    }

    /**
     * Selecting this will NOT save any data from the screen, and return back to the Main Screen.
     * @param event created to initiate the CANCEL method on the Add Appointment Screen.
     * @throws IOException when criteria has not been met to successfully move back to the Main Screen.
     */
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

    /**
     * Initializes the Add Appointment Screen with ComboBoxes prefilled with data from Observable Array Lists.
     * @param user references the User who is logged in at time of initializing.
     */
    public void initialize(User user) {

        loggedIn = user;
        CustomerImpl custI = new CustomerImpl();
        UserImpl userI = new UserImpl();
        ContactImpl contactI = new ContactImpl();
        ObservableList<User> userChoices = FXCollections.observableArrayList();
        ObservableList<Customer> custChoices = FXCollections.observableArrayList();
        ObservableList<Contact> contactChoices = FXCollections.observableArrayList();
        ObservableList<String> types = FXCollections.observableArrayList();
        types.add("Virtual");
        types.add("In-Person");
        userChoices = userI.getAllUsers();

        custChoices = custI.allCustomers();
        contactChoices = contactI.getAllContacts();

        newUser.setItems(userChoices);
        newUser.setValue(user);
        newContact.setItems(contactChoices);
        newCustomer.setItems(custChoices);

        newType.setItems(types);

    }

}