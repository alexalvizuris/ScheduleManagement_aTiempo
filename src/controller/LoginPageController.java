package controller;



import database.impl.AppointmentImpl;
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
import model.User;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Controller for the Login Screen.
 */
public class LoginPageController {

    @FXML
    private Label titleLabel;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button signInButton;

    @FXML
    private Label locationLabel;

    @FXML
    private Label locationText;


    /**
     * Selecting this will verify if a username and password combo are acceptable and takes the User to the Main Screen.
     * A pop up message will generate whether or not an Appointment is scheduled for the user within the next 15 minutes.
     * @param event created to initialize the Sign-In Method on the Login Screen.
     * @throws IOException when criteria is not met to successfully move onto the Main Screen.
     */
    public void signInSelected(ActionEvent event) throws IOException {

        ResourceBundle rb = ResourceBundle.getBundle("utility/Lan", Locale.getDefault());

         UserImpl implement = new UserImpl();
         AppointmentImpl apptImpl = new AppointmentImpl();
         ObservableList<User> acceptableUsers = implement.getAllUsers();
         int userID = 0;
         String apptId = "";
         String appt = "";

         String usernameInput = username.getText();
         String passwordInput = password.getText();
         int count = 0;
         int hasAppt = 0;


         for (int i = 0; i < acceptableUsers.size(); i++) {
             if (acceptableUsers.get(i).getUserName().equals(usernameInput) && acceptableUsers.get(i).getPassword().equals(passwordInput)) {
                 count += 1;

                 String fileEntry = "login_activity.txt", entry;
                 FileWriter writer = new FileWriter(fileEntry, true);
                 PrintWriter print = new PrintWriter(writer);

                 entry = String.valueOf(LocalDateTime.now()) + " SUCCESSFUL login attempt by: " + usernameInput + ".";
                 print.println(entry);
                 print.close();

                 userID = acceptableUsers.get(i).getUserId();
                 ObservableList<Appointment> userAppt = FXCollections.observableArrayList();
                 userAppt = apptImpl.allFromUser(userID);

                 for (int j = 0; j < userAppt.size(); j++) {

                     if (userAppt.get(j).getStart().isAfter(LocalDateTime.now()) && userAppt.get(j).getStart().isBefore(LocalDateTime.now().plusMinutes(15))) {
                         apptId = String.valueOf(userAppt.get(j).getAppointmentID());
                         appt = userAppt.get(j).getStart().toString();
                         hasAppt += 1;
                     }

                 }

                 if (hasAppt > 0) {
                     Alert alert = new Alert(Alert.AlertType.INFORMATION);
                     alert.setTitle("Upcoming Appointment");
                     alert.setContentText("You have an appointment within the next 15 minutes. Appointment ID: " + apptId + ". Date and Time: " + appt + ".");
                     alert.showAndWait();
                 }
                 if (hasAppt == 0) {
                     Alert alert = new Alert(Alert.AlertType.INFORMATION);
                     alert.setTitle("Appointment Information");
                     alert.setContentText("No upcoming appointments at this moment.");
                     alert.showAndWait();
                 }


                 FXMLLoader loader = new FXMLLoader();
                 loader.setLocation(getClass().getResource("/view/mainScreen.fxml"));
                 Parent mainParent = loader.load();
                 Scene signInScene = new Scene(mainParent);

                 MainScreenController controller = loader.getController();
                 controller.initialize(acceptableUsers.get(i));

                 Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                 stage.setScene(signInScene);
                 stage.centerOnScreen();
                 stage.show();
             }

         }

        if (count == 0) {

            String fileEntry = "login_activity.txt", entry;
            FileWriter writer = new FileWriter(fileEntry, true);
            PrintWriter print = new PrintWriter(writer);

            entry = String.valueOf(LocalDateTime.now()) + " UNSUCCESSFUL login attempt by: " + usernameInput + ".";
            print.println(entry);
            print.close();

            if (Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("fr")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("Error"));
                alert.setContentText(rb.getString("ErrorMessage"));
                alert.showAndWait();
            }
        }

    }


    /**
     * Initializes the Login Screen with information about the current Users Location. This will determine which language to display on the screen.
     */
    public void initialize() {
        ResourceBundle rb = ResourceBundle.getBundle("utility/Lan", Locale.getDefault());
        locationText.setText(String.valueOf(ZonedDateTime.now().getZone()));

        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
            username.setPromptText(rb.getString("Username"));
            password.setPromptText(rb.getString("Password"));
            signInButton.setText(rb.getString("SignIn"));
            locationLabel.setText(rb.getString("Location"));

        }
    }


}

