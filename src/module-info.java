module ScheduleManagement.aTiempo {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;

    opens controller;
    //opens database;
    opens main;
   // opens utility;
    opens view;

    opens database;

    opens model;


}