package controller;



import database.impl.UserImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.sql.SQLException;

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

     try {
         UserImpl implement = new UserImpl();
         ObservableList<User> acceptableUsers = implement.getAllUsers();
         String usernameInput = username.getText();
         String passwordInput = password.getText();

         for (int i = 0; i < acceptableUsers.size(); i++) {
             if (acceptableUsers.get(i).getUserName().equals(usernameInput) && acceptableUsers.get(i).getPassword().equals(passwordInput)) {

                 Parent signInParent = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
                 Scene signInScene = new Scene(signInParent);

                 Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                 stage.setScene(signInScene);
                 stage.show();
             }

         }
     } catch (Exception e) {
         System.out.println(e.getMessage());
     }



    }

}

