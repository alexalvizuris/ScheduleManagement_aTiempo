package controller;

import database.impl.AppointmentImpl;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.User;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;


/**
 * Controller for the Main Screen.
 */
public class MainScreenController {

    @FXML
    private Button newAppointmentButton;

    @FXML
    private Button newCustomerButton;

    @FXML
    private Button reportsButton;

    @FXML
    private Button signOutButton;

    @FXML
    private TableView<Appointment> mainTableView;

    @FXML
    private TableColumn<Appointment, Integer> firstColumn;

    @FXML
    private TableColumn<Appointment, String> secondColumn;

    @FXML
    private TableColumn<Appointment, String> thirdColumn;

    @FXML
    private TableColumn<Appointment, String> fourthColumn;

    @FXML
    private TableColumn<Appointment, String> fifthColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> sixthColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> seventhColumn;

    @FXML
    private TableColumn<Appointment, Integer> eighthColumn;

    @FXML
    private TableColumn<Appointment, Integer> ninthColumn;

    @FXML
    private TableColumn<Appointment, Integer> tenthColumn;

    @FXML
    private MenuButton tableSelection;

    @FXML
    private MenuItem viewCustomers;

    @FXML
    private MenuItem viewAppointments;

    @FXML
    private Button deleteButton;

    @FXML
    private Button updateButton;

    @FXML
    private RadioButton monthRadioButton;

    @FXML
    private RadioButton weekRadioButton;

    @FXML
    private RadioButton viewAllRadioButton;

    @FXML
    private Label mainLabel;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> custID;

    @FXML
    private TableColumn<Customer, String> custName;

    @FXML
    private TableColumn<Customer, String> custAddress;

    @FXML
    private TableColumn<Customer, String> custPostal;

    @FXML
    private TableColumn<Customer, Integer> custDivision;

    @FXML
    private TableColumn<Customer, String> custPhone;

    @FXML
    private Button custUpdate;

    @FXML
    private Button custDelete;

    private User loggedIn;

    @FXML
    private Label noticeLabel;


    /**
     * Selecting this will send the User to the Add Appointment Screen.
     * @param event created to initialize the Add Appointment controller.
     * @throws IOException when criteria has not been met to successfully load the Add Appointment Screen.
     */
    public void newAppointmentSelected(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/addAppointment.fxml"));

        Parent addApptParent = loader.load();
        Scene addApptScene = new Scene(addApptParent);

        AddAppointmentController controller = loader.getController();
        controller.initialize(loggedIn);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(addApptScene);
        stage.centerOnScreen();
        stage.show();

    }

    /**
     * Selecting this will send the User to the Add Customer Screen.
     * @param event created to initialize the Add Customer controller.
     * @throws IOException when criteria has not been met to successfully load the Add Customer Screen.
     */
    public void newCustomerSelected(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/addCustomer.fxml"));

        Parent addCustParent = loader.load();
        Scene addCustScene = new Scene(addCustParent);

        AddCustomerController controller = loader.getController();
        controller.initialize(loggedIn);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(addCustScene);
        stage.centerOnScreen();
        stage.show();

    }


    /**
     * Selecting this will switch the tableview from Appointments to Customers, and disable the Appointment radio buttons.
     */
    public void viewCustomerTable() {

        tableSelection.setText("View Appointments");
        weekRadioButton.setVisible(false);
        weekRadioButton.setDisable(true);
        monthRadioButton.setVisible(false);
        monthRadioButton.setDisable(true);
        mainLabel.setText("Customers");
        mainTableView.setVisible(false);
        mainTableView.isDisabled();
        customerTable.setDisable(false);
        customerTable.setVisible(true);
        updateButton.setVisible(false);
        updateButton.setDisable(true);
        deleteButton.setVisible(false);
        deleteButton.setDisable(true);
        custUpdate.setVisible(true);
        custUpdate.setDisable(false);
        custDelete.setVisible(true);
        custDelete.setDisable(false);
        viewAllRadioButton.setVisible(false);
        viewAllRadioButton.setDisable(true);

    }

    /**
     * Selecting this will switch the tableview from Customers to Appointments and enable the Appointment radio buttons for week and month.
     */
    public void viewAppointmentTable() {

        tableSelection.setText("View Customers");
        weekRadioButton.setVisible(true);
        weekRadioButton.setDisable(false);
        monthRadioButton.setVisible(true);
        monthRadioButton.setDisable(false);
        mainLabel.setText("Appointments");
        mainTableView.setVisible(true);
        mainTableView.setDisable(false);
        customerTable.setDisable(true);
        customerTable.setVisible(false);
        custUpdate.setDisable(true);
        custUpdate.setVisible(false);
        custDelete.setVisible(false);
        custDelete.setDisable(true);
        updateButton.setDisable(false);
        updateButton.setVisible(true);
        deleteButton.setVisible(true);
        deleteButton.setDisable(false);
        viewAllRadioButton.setVisible(true);
        viewAllRadioButton.setDisable(false);

    }


    /**
     * Selecting this will take the User to the Update Appointment Screen.
     * @param event created to initialize the Update Appointment controller.
     * @throws IOException when criteria has not been met to successfully load the Update Appointment Screen.
     */
    public void updateSelected(ActionEvent event) throws IOException {

        if (mainTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error has occurred");
            alert.setContentText("Please select an Appointment to modify.");
            alert.showAndWait();
            return;
        }

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("/view/updateAppointment.fxml"));
            Parent updateParent = loader.load();
            Scene modifyScene = new Scene(updateParent);

            UpdateAppointmentController controller = loader.getController();
            controller.initApptData(mainTableView.getSelectionModel().getSelectedItem(), loggedIn);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(modifyScene);
            stage.show();
    }


    /**
     * Selecting this will take the User to the Update Customer Screen.
     * @param event created to initialize the Update Customer controller.
     * @throws IOException when criteria has not been met to successfully load the Update Customer Screen.
     */
    public void custUpdate(ActionEvent event) throws IOException {

        if (customerTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error has occurred");
            alert.setContentText("Please select a Customer to modify.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/view/updateCustomer.fxml"));
        Parent updateParent = loader.load();
        Scene modifyScene = new Scene(updateParent);

        UpdateCustomerController controller = loader.getController();
        controller.initCustData(customerTable.getSelectionModel().getSelectedItem(), loggedIn);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(modifyScene);
        stage.show();

    }


    /**
     * Selecting this will DELETE the selected Appointment from the database.
     */
    public void deleteAppointment() {

        if (mainTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error has occurred");
            alert.setContentText("Please select an Appointment to delete.");
            alert.showAndWait();
            return;
        }

        String typeDeleted = mainTableView.getSelectionModel().getSelectedItem().getType();
        int id = mainTableView.getSelectionModel().getSelectedItem().getAppointmentID();



        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are deleting a(n) " + typeDeleted + " appointment. Appointment ID: " + id + ". Continue?");
        Optional<ButtonType> selectedButton = alert.showAndWait();

        if (selectedButton.isPresent() && selectedButton.get() == ButtonType.OK) {
            AppointmentImpl appt = new AppointmentImpl();
            int apptID = mainTableView.getSelectionModel().getSelectedItem().getAppointmentID();
            appt.delete(apptID);
        }

    }


    /**
     * Selecting this will DELETE the selected Customer from the database.
     */
    public void deleteCustomer() {

        ObservableList<Appointment> allAppt = FXCollections.observableArrayList();
        AppointmentImpl impl = new AppointmentImpl();
        allAppt = impl.getAllAppt();

        if (customerTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error has occurred");
            alert.setContentText("Please select a Customer to delete.");
            alert.showAndWait();
            return;
        }

        for (int i = 0; i < allAppt.size(); i++) {
            if (customerTable.getSelectionModel().getSelectedItem().getCustomerID() == allAppt.get(i).getCustomerID()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("An Error has occurred");
                alert.setContentText("This customer is still associated to 1 or more appointments. Cannot delete.");
                alert.showAndWait();
                return;
            }
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are deleting a customer. Continue?");
        Optional<ButtonType> selectedButton = alert.showAndWait();

        if (selectedButton.isPresent() && selectedButton.get() == ButtonType.OK) {
            CustomerImpl cust = new CustomerImpl();
            int custID = customerTable.getSelectionModel().getSelectedItem().getCustomerID();
            cust.delete(custID);
        }

    }


    /**
     * Selecting this will display this weeks Appointments.
     */
    public void weekSelected() {
        AppointmentImpl impl = new AppointmentImpl();
        if (weekRadioButton.isSelected()) {
            mainTableView.setItems(impl.weekAppointments(loggedIn.getUserId()));
        }
    }


    /**
     * Selecting this will display this months Appointments.
     */
    public void monthSelected() {
        AppointmentImpl impl = new AppointmentImpl();
        if (monthRadioButton.isSelected()) {
            mainTableView.setItems(impl.monthAppointments(loggedIn.getUserId()));
        }
    }


    /**
     * Selecting this will return the tableview back to the original display of ALL appointments.
     */
    public void allSelected() {
        AppointmentImpl impl = new AppointmentImpl();
        if (viewAllRadioButton.isSelected()) {
            mainTableView.setItems(impl.allFromUser(loggedIn.getUserId()));
        }
    }


    /**
     * Selecting this will take the user to a screen of Reports generated with information from the database.
     * @param event created to initialize the Reports controller.
     * @throws IOException when criteria has not been met to successfully load the Reports Screen.
     */
    public void reportSelected(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/reports.fxml"));

        Parent reportParent = loader.load();
        Scene reportScene = new Scene(reportParent);

        ReportController controller = loader.getController();
        controller.initReport(loggedIn);


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(reportScene);
        stage.centerOnScreen();
        stage.show();

    }


    /**
     * Selecting this will sign the User out of the Application and exit the screen.
     */
    public void signOutSelected() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are now EXITING the program. Continue?");
        Optional<ButtonType> selectedButton = alert.showAndWait();

        if (selectedButton.isPresent() && selectedButton.get() == ButtonType.OK) {

            Stage stage = (Stage) signOutButton.getScene().getWindow();
            stage.close();
        }
    }


    /**
     * Initializes the Main Screen with data from the Appointments Table and Customer Table in the database associated to the User.
     * @param user references the User currently logged intot he application.
     */
    public void initialize(User user) {

        loggedIn = user;


        AppointmentImpl implement = new AppointmentImpl();
        mainTableView.setItems(implement.allFromUser(loggedIn.getUserId()));

        firstColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        secondColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        thirdColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        fourthColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        fifthColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        sixthColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        seventhColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        eighthColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        ninthColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        tenthColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));

        mainTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        CustomerImpl cusImplement = new CustomerImpl();
        customerTable.setItems(cusImplement.allCustomers());

        custID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        custName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        custAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        custPostal.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        custPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        custDivision.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

        customerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        UserImpl userImpl = new UserImpl();
        ObservableList<User> acceptable = FXCollections.observableArrayList();
        acceptable = userImpl.getAllUsers();
        AppointmentImpl apptImpl = new AppointmentImpl();
        ObservableList<Appointment> theseAppt = FXCollections.observableArrayList();

        int userId = loggedIn.getUserId();
        theseAppt = apptImpl.allFromUser(userId);
        String apptId = "";
        String appt = "";
        int hasAppt = 0;

        for (int j = 0; j < theseAppt.size(); j ++) {
            if (theseAppt.get(j).getStart().isAfter(LocalDateTime.now()) && theseAppt.get(j).getStart().isBefore(LocalDateTime.now().plusMinutes(15))) {
                apptId = String.valueOf(theseAppt.get(j).getAppointmentID());
                appt = theseAppt.get(j).getStart().toString();
                hasAppt += 1;
            }
            if (hasAppt > 0) {
                noticeLabel.setText("NOTICE: APPOINTMENT " + apptId + " BEGINS AT " + appt + "!");
            }
            if (hasAppt == 0) {
                noticeLabel.setText("NOTICE: NO UPCOMING APPOINTMENTS AT THIS MOMENT");
            }
        }
    }



}