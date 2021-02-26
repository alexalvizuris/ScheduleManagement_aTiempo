package controller;


import database.impl.AppointmentImpl;
import database.impl.ContactImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;

public class ReportController {

    @FXML
    private TableView<Appointment> contactTable;

    @FXML
    private TableColumn<Appointment, Integer> contactID;

    @FXML
    private TableColumn<Appointment, String> contactName;

    @FXML
    private TableColumn<Appointment, Integer> contactCustID;

    @FXML
    private TableColumn<Appointment, String> contactTitle;

    @FXML
    private TableColumn<Appointment, String> contactType;

    @FXML
    private TableColumn<Appointment, String> contactDescription;

    @FXML
    private TableColumn<Appointment, LocalDateTime> contactStart;

    @FXML
    private TableColumn<Appointment, LocalDateTime> contactEnd;

    @FXML
    private TextArea recordText;

    @FXML
    private RadioButton virtualRadio;

    @FXML
    private RadioButton personRadio;

    @FXML
    private Button returnButton;

    @FXML
    private TableView<Appointment> typeTable;

    @FXML
    private TableColumn<Appointment, Integer> typeID;

    @FXML
    private TableColumn<Appointment, Integer> typeCustID;

    @FXML
    private TableColumn<Appointment, String> typeTitle;

    @FXML
    private TableColumn<Appointment, String> typeDescription;

    @FXML
    private TableColumn<Appointment, LocalDateTime> typeStart;

    @FXML
    private TableColumn<Appointment, LocalDateTime> typeEnd;

    private User loggedIn;



    public void ReturnSelected(ActionEvent event) throws IOException {
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

    public void personSelected() {

        AppointmentImpl impl = new AppointmentImpl();

        if (personRadio.isSelected()) {
            typeTable.setItems(impl.ofType("In-Person"));
        }

    }

    public void virtualSelected() {

        AppointmentImpl impl = new AppointmentImpl();

        if (virtualRadio.isSelected()) {
            typeTable.setItems(impl.ofType("Virtual"));
        }
    }



    public void initReport(User user) {
        loggedIn = user;

        AppointmentImpl impl = new AppointmentImpl();
        ContactImpl contImpl = new ContactImpl();
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        ObservableList<Appointment> appts = FXCollections.observableArrayList();

        contacts = contImpl.getAllContacts();
        appts = impl.allFromUser(loggedIn.getUserId());

        for (int i = 0; i < contacts.size(); i++) {
            for (int j = 0; j < appts.size(); j++) {
                if (appts.get(j).getContactID() == contacts.get(i).getContactID()) {
                    appts.get(j).setContactName(contacts.get(i).getContactName());
                }
            }
        }


        // Utilize a lambda expression to compare each appointment by the appointment after it, and sort by the contact name.

        Comparator<Appointment> appointmentComparator = (appt1, appt2) -> appt1.getContactName().compareTo(appt2.getContactName());
        appts.sort(appointmentComparator);

        contactTable.setItems(appts);

        contactID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        contactName.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        contactCustID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        contactTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        contactType.setCellValueFactory(new PropertyValueFactory<>("type"));
        contactDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        contactStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        contactEnd.setCellValueFactory(new PropertyValueFactory<>("end"));

        typeTable.setItems(impl.ofType("Virtual"));
        typeID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        typeCustID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        typeTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        typeEnd.setCellValueFactory(new PropertyValueFactory<>("end"));




    }


}

