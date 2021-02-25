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

import java.io.IOException;
import java.time.LocalDateTime;


public class LoginPageController {

    @FXML
    private Label titleLabel;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button signInButton;


    public void signInSelected(ActionEvent event) throws IOException {

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

                 userID = acceptableUsers.get(i).getUserId();
                 ObservableList<Appointment> userAppt = FXCollections.observableArrayList();
                 userAppt = apptImpl.allFromUser(userID);

                 for (int j = 0; j < userAppt.size(); j++) {
                     if (userAppt.get(j).getStart().isAfter(LocalDateTime.now()) && userAppt.get(i).getStart().isBefore(LocalDateTime.now().plusMinutes(15))) {
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setContentText("The username or password was incorrect. Please try again.");
            alert.showAndWait();
        }

    }


}

