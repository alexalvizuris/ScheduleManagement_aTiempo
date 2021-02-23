package controller;



import database.impl.UserImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;


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
         ObservableList<User> acceptableUsers = implement.getAllUsers();
         String usernameInput = username.getText();
         String passwordInput = password.getText();
        int count = 0;
         for (int i = 0; i < acceptableUsers.size(); i++) {
             if (acceptableUsers.get(i).getUserName().equals(usernameInput) && acceptableUsers.get(i).getPassword().equals(passwordInput)) {
                 count += 1;



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

