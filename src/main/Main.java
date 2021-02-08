package main;


import database.impl.AppointmentImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Appointment;

import java.time.LocalDateTime;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/login_page.fxml"));
        primaryStage.setTitle("Schedule Management");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {

//        Appointment appointment = new Appointment("Date", "Movie night together", "My house", "Romance", LocalDateTime.now(), LocalDateTime.now() );
//        appointment.setContactID(11111);
//        appointment.setCustomerID(11111);
//        appointment.setUserID(111111);
//        appointment.setCreatedBy(null);
//        appointment.setLastUpdatedBy(null);
//        appointment.setCreateDate(null);
//        appointment.setLastUpdate(null);


        launch(args);

    }
}
