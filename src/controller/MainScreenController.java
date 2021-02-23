package controller;

import database.impl.AppointmentImpl;

import database.impl.CustomerImpl;
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

    public void viewCustomerTable(ActionEvent event) {

        tableSelection.setText("View Appointments");
        weekRadioButton.setVisible(false);
        weekRadioButton.isDisabled();
        monthRadioButton.setVisible(false);
        monthRadioButton.isDisabled();
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


    }

    public void viewAppointmentTable(ActionEvent event) {

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


    }

    public void updateSelected(ActionEvent event) throws IOException {

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

    public void custUpdate(ActionEvent event) throws IOException {

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

    public void deleteAppointment(ActionEvent event) {

        if (mainTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error has occurred");
            alert.setContentText("Please select an Appointment to delete.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are deleting an appointment. Continue?");
        Optional<ButtonType> selectedButton = alert.showAndWait();

        if (selectedButton.isPresent() && selectedButton.get() == ButtonType.OK) {
            AppointmentImpl appt = new AppointmentImpl();
            int apptID = mainTableView.getSelectionModel().getSelectedItem().getAppointmentID();
            appt.delete(apptID);
        }

    }

    public void deleteCustomer(ActionEvent event) {

        if (customerTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error has occurred");
            alert.setContentText("Please select a Customer to delete.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are deleting a customer. Continue?");
        Optional<ButtonType> selectedButton = alert.showAndWait();

        if (selectedButton.isPresent() && selectedButton.get() == ButtonType.OK) {
            CustomerImpl cust = new CustomerImpl();
            int custID = customerTable.getSelectionModel().getSelectedItem().getCustomerID();
            cust.delete(custID);
        }

    }

    public void weekSelected() {
        if (weekRadioButton.isSelected()) {

        }
    }



    public void signOutSelected() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are now EXITING the program. Continue?");
        Optional<ButtonType> selectedButton = alert.showAndWait();

        if (selectedButton.isPresent() && selectedButton.get() == ButtonType.OK) {

            Stage stage = (Stage) signOutButton.getScene().getWindow();
            stage.close();
        }
    }


    public void initialize(User user) {

        loggedIn = user;

        AppointmentImpl implement = new AppointmentImpl();
        mainTableView.setItems(implement.getAllAppt());

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



    }




}