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


/**
 * Controller for the Report Screen.
 */
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


    /**
     * Selecting this will return the User back to the Main Screen.
     * @param event created to initialize the Main Screen controller.
     * @throws IOException when criteria has not been met to successfully load the Main Screen.
     */
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

    /**
     * Selecting this will dislplay the tableview with Appointments of type "In-Person".
     * Utilized Lambda expression to sort by the date in each tableview so User would know which appointments are next.
     */
    public void personSelected() {

        AppointmentImpl impl = new AppointmentImpl();

        // Utilized a lambda expression to compare Appointments and sort by Date

        Comparator<Appointment> dateComparator = (date1, date2) -> date1.getStart().toLocalDate().compareTo(date2.getStart().toLocalDate());
        ObservableList<Appointment> reportbyDate = FXCollections.observableArrayList();
        reportbyDate = impl.ofType("In-Person");
        reportbyDate.sort(dateComparator);

        if (personRadio.isSelected()) {
            typeTable.setItems(reportbyDate);
        }

    }


    /**
     * Selecting this will display the tableview with Appointments of type "Virtual".
     * Utilized Lambda expression to sort by the date in each tableview so User would know which appointments are next.
     */
    public void virtualSelected() {

        AppointmentImpl impl = new AppointmentImpl();

        // Utilized a lambda expression to compare Appointments and sort by Date

        Comparator<Appointment> dateComparator = (date1, date2) -> date1.getStart().toLocalDate().compareTo(date2.getStart().toLocalDate());
        ObservableList<Appointment> reportbyDate = FXCollections.observableArrayList();
        reportbyDate = impl.ofType("Virtual");
        reportbyDate.sort(dateComparator);

        if (virtualRadio.isSelected()) {
            typeTable.setItems(reportbyDate);
        }
    }


    /**
     * Initializes the Report Screen with Data/tableviews populated by the database.
     * Utilized Lambda expression to sort the Contacts schedules by name.
     * @param user references the user currently logged into the application.
     */
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


        // Utilized a lambda expression to compare each appointment by the appointment after it, and sort by the contact name.

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

        // Utilized a lambda expression to compare Appointments and sort by Date

        Comparator<Appointment> dateComparator = (date1, date2) -> date1.getStart().toLocalDate().compareTo(date2.getStart().toLocalDate());
        ObservableList<Appointment> reportbyDate = FXCollections.observableArrayList();
        reportbyDate = impl.ofType("Virtual");
        reportbyDate.sort(dateComparator);

        typeTable.setItems(reportbyDate);
        typeID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        typeCustID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        typeTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        typeEnd.setCellValueFactory(new PropertyValueFactory<>("end"));

        int person = 0;
        int online = 0;
        int jan = 0;
        int feb = 0;
        int march = 0;
        int april = 0;
        int may = 0;
        int june = 0;
        int july = 0;
        int aug = 0;
        int sept = 0;
        int oct = 0;
        int nov = 0;
        int dec = 0;

        ObservableList<Appointment> reportList = FXCollections.observableArrayList();
        reportList = impl.allFromUser(loggedIn.getUserId());

        for (int i = 0; i < reportList.size(); i++) {
            if (reportList.get(i).getType().equals("In-Person")) {
                person += 1;
            }
            if (reportList.get(i).getType().equals("Virtual")) {
                online += 1;
            }
            if (reportList.get(i).getStart().getMonthValue() == 1) {
                jan += 1;
            }
            if (reportList.get(i).getStart().getMonthValue() == 2) {
                feb += 1;
            }
            if (reportList.get(i).getStart().getMonthValue() == 3) {
                march += 1;
            }
            if (reportList.get(i).getStart().getMonthValue() == 4) {
                april += 1;
            }
            if (reportList.get(i).getStart().getMonthValue() == 5) {
                may += 1;
            }
            if (reportList.get(i).getStart().getMonthValue() == 6) {
                june += 1;
            }
            if (reportList.get(i).getStart().getMonthValue() == 7) {
                july += 1;
            }
            if (reportList.get(i).getStart().getMonthValue() == 8) {
                aug += 1;
            }
            if (reportList.get(i).getStart().getMonthValue() == 9) {
                sept += 1;
            }
            if (reportList.get(i).getStart().getMonthValue() == 10) {
                oct += 1;
            }
            if (reportList.get(i).getStart().getMonthValue() == 11) {
                nov += 1;
            }
            if (reportList.get(i).getStart().getMonthValue() == 12) {
                dec += 1;
            }
        }

        recordText.setText("Total number of In-Person appointments: " + person + "\nTotal number of Virtual appointments: " + online + "\n\n\n" +
                            "\nTotal number of appointments in January: " + jan + "\nTotal number of appointments in February: " + feb +
                            "\nTotal number of appointments in March: " + march + "\nTotal number of appointments in April: " + april +
                            "\nTotal number of appointments in May: " + may + "\nTotal number of appointments in June: " + june +
                            "\nTotal number of appointments in July: " + july + "\nTotal number of appointments in August: " + aug +
                            "\nTotal number of appointments in September: " + sept + "\nTotal number of appointments in October: " + oct +
                            "\nTotal number of appointments in November: " + nov + "\nTotal number of appointments in December: " + dec);



    }


}

