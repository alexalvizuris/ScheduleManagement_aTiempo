package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;

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
    private TableColumn<?, ?> thirdColumn;

    @FXML
    private TableColumn<?, ?> fourthColumn;

    @FXML
    private TableColumn<?, ?> fifthColumn;

    @FXML
    private TableColumn<?, ?> sixthColumn;

    @FXML
    private TableColumn<?, ?> seventhColumn;

    @FXML
    private TableColumn<?, ?> eighthColumn;

    @FXML
    private TableColumn<?, ?> ninthColumn;

    @FXML
    private TableColumn<?, ?> tenthColumn;

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


}