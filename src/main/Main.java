package main;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;


/**
 * Main Class for application
 */
public class Main extends Application {

    /**
     * Executes the initial log in screen
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/login_page.fxml"));
        primaryStage.setTitle("Schedule Management");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);

    }
}
