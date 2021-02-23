package controller;

import database.impl.CountryImpl;
import database.impl.CustomerImpl;
import database.impl.FirstLevelDivisionImpl;
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
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import model.User;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

public class UpdateCustomerController {

    @FXML
    private ComboBox<Country> updateCountry;

    @FXML
    private ComboBox<FirstLevelDivision> updateState;

    @FXML
    private TextField updateName;

    @FXML
    private TextField updateAddress;

    @FXML
    private TextField updatePostal;

    @FXML
    private TextField updatePhone;

    @FXML
    private Button updateSave;

    @FXML
    private Button updateCancel;

    @FXML
    private TextField updateID;

    private User loggedIn;

    public void updateSaveSelected(ActionEvent event) throws IOException {
        String id = String.valueOf(updateID.getText());
        String name = updateName.getText();
        String address = updateAddress.getText();
        String postal = updatePostal.getText();
        int tempCountry = updateCountry.getSelectionModel().getSelectedItem().getCountryID();
        String country = updateCountry.getSelectionModel().getSelectedItem().getCountryName();
        int division = updateState.getSelectionModel().getSelectedItem().getDivisionID();
        String phone = updatePhone.getText();
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        String updatedBy = loggedIn.getUserName();


        Customer customer = new Customer(name, address, postal, phone);
        customer.setDivisionID(division);
        customer.setLastUpdated(now);
        customer.setLastUpdatedBy(updatedBy);
        customer.setCustomerID(Integer.valueOf(id));

        CustomerImpl impl = new CustomerImpl();

        impl.update(customer);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/mainScreen.fxml"));
        Parent updateCustParent = loader.load();
        Scene updateCustScene = new Scene(updateCustParent);

        MainScreenController controller = loader.getController();
        controller.initialize(loggedIn);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(updateCustScene);
        stage.centerOnScreen();
        stage.show();

    }


    public void updateCancelButton(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No data will be saved. Continue?");
        Optional<ButtonType> confirm = alert.showAndWait();

        if (confirm.isPresent() && confirm.get() == ButtonType.OK) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/mainScreen.fxml"));
            Parent updateCustParent = loader.load();
            Scene updateCustScene = new Scene(updateCustParent);

            MainScreenController controller = loader.getController();
            controller.initialize(loggedIn);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(updateCustScene);
            stage.centerOnScreen();
            stage.show();
        }
    }

    public void initCustData(Customer customer, User user) {

        loggedIn = user;

        CountryImpl countryImpl = new CountryImpl();
        FirstLevelDivisionImpl divisionImpl = new FirstLevelDivisionImpl();

        ObservableList<Country> countryList = FXCollections.observableArrayList();
        ObservableList<FirstLevelDivision> divisionList = FXCollections.observableArrayList();

        countryList = countryImpl.getAllCountries();
        divisionList = divisionImpl.getAllDivisions();

        updateCountry.setItems(countryList);
        updateState.setItems(divisionList);

        updateID.setText(String.valueOf(customer.getCustomerID()));
        updateName.setText(customer.getCustomerName());
        updateAddress.setText(customer.getAddress());
        updatePostal.setText(customer.getPostalCode());
        updatePhone.setText(customer.getPhoneNumber());

        FirstLevelDivision tempDivision = divisionImpl.getDivision(customer.getDivisionID());
        updateCountry.setValue(countryImpl.getCountry(tempDivision.getCountryID()));
        updateState.setValue(divisionImpl.getDivision(customer.getDivisionID()));

    }

}

