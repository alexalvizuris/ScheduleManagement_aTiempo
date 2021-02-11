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

import java.io.IOException;
import java.time.LocalDateTime;

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


    public void newAppointmentSelected(ActionEvent event) throws IOException {

        Parent addApptParent = FXMLLoader.load(getClass().getResource("/view/addAppointment.fxml"));
        Scene addApptScene = new Scene(addApptParent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(addApptScene);
        stage.show();

    }

    public void newCustomerSelected(ActionEvent event) throws IOException
    {
        Parent addCustParent = FXMLLoader.load(getClass().getResource("/view/addCustomer.fxml"));
        Scene addCustScene = new Scene(addCustParent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(addCustScene);
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


    }

    public void initialize() {
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