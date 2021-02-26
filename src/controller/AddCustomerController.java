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
import java.util.Optional;


/**
 * Controller for the Add Customer Screen.
 */
public class AddCustomerController {

    @FXML
    private ComboBox<Country> newCountry;

    @FXML
    private ComboBox<FirstLevelDivision> newState;

    @FXML
    private TextField newName;

    @FXML
    private TextField newAddress;

    @FXML
    private TextField newPostal;

    @FXML
    private TextField newPhone;

    @FXML
    private Button newCustSave;

    @FXML
    private Button newCustCancel;

    private User loggedIn;

    /**
     * Selecting this will NOT save any data from the screen, and return back to the Main Screen.
     * @param event created to initialize the CANCEL method on the Add Customer Screen.
     * @throws IOException when criteria is not met to successfully return back to the Main Screen.
     */
    public void newCustCancelSelected(ActionEvent event) throws IOException {


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No data will be saved. Continue?");
            Optional<ButtonType> confirm = alert.showAndWait();

            if (confirm.isPresent() && confirm.get() == ButtonType.OK) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/mainScreen.fxml"));

                Parent addCustParent = loader.load();
                Scene addCustScene = new Scene(addCustParent);

                MainScreenController controller = loader.getController();
                controller.initialize(loggedIn);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(addCustScene);
                stage.centerOnScreen();
                stage.show();

            }
        }



    /**
     * Selecting this will add the customer to the database, and return back to the Main Screen.
     * @param event created to initialize the SAVE method on the Add Customer Screen.
     * @throws IOException when criteria is not met to successfully return back to the Main Screen.
     */
    public void newCustSaveSelected(ActionEvent event) throws IOException {

        try {
            String name = newName.getText();
            String address = newAddress.getText();
            String postal = newPostal.getText();
            int tempCountry = newCountry.getSelectionModel().getSelectedItem().getCountryID();
            String country = newCountry.getSelectionModel().getSelectedItem().getCountryName();
            int division = newState.getSelectionModel().getSelectedItem().getDivisionID();
            String phone = newPhone.getText();
            String createdBy = loggedIn.getUserName();
            String updatedBy = loggedIn.getUserName();


            Customer customer = new Customer(name, address, postal, phone);
            customer.setDivisionID(division);
            customer.setCreatedBy(createdBy);
            customer.setLastUpdatedBy(updatedBy);

            CustomerImpl impl = new CustomerImpl();

            impl.create(customer);


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/mainScreen.fxml"));

            Parent addCustParent = loader.load();
            Scene addCustScene = new Scene(addCustParent);

            MainScreenController controller = loader.getController();
            controller.initialize(loggedIn);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(addCustScene);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An Error has occurred");
            alert.setContentText("Please enter valid input");
            alert.showAndWait();
            return;
        }

    }

    /**
     * When a country is selected from the ComboBox, it will then fill the States ComboBox with the Appropriate First Level Division.
     */
    public void countrySelected() {

        FirstLevelDivisionImpl divisionImpl = new FirstLevelDivisionImpl();
        if (!(newCountry.getSelectionModel().isEmpty())) {
            Country selectedCountry = newCountry.getSelectionModel().getSelectedItem();
            int ID = selectedCountry.getCountryID();
            ObservableList<FirstLevelDivision> allDivisions = divisionImpl.getAllDivisions();
            ObservableList<FirstLevelDivision> sortedDivisions = FXCollections.observableArrayList();

            for (int i = 0; i < allDivisions.size(); i++) {
                if (allDivisions.get(i).getCountryID() == ID) {
                    sortedDivisions.add(allDivisions.get(i));
                }
            }
            newState.setDisable(false);
            newState.setItems(sortedDivisions);
        }
    }


    /**
     * Initializes the Add Customer Screen with Observable Array Lists to pre fill the Country and State ComboBoxes.
     * @param user references the current user logged into the application.
     */
    public void initialize(User user) {

        loggedIn = user;

        CountryImpl country = new CountryImpl();
        FirstLevelDivisionImpl division = new FirstLevelDivisionImpl();

        ObservableList<Country> countryList = FXCollections.observableArrayList();
        ObservableList<FirstLevelDivision> divisionList = FXCollections.observableArrayList();

        countryList = country.getAllCountries();
        divisionList = division.getAllDivisions();
        newCountry.setItems(countryList);
        newState.setItems(divisionList);

    }

}