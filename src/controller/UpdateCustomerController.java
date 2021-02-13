package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;

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

    public void initCustdata(Customer customer) {

        updateID.setText(String.valueOf(customer.getCustomerID()));
        updateName.setText(customer.getCustomerName());
        updateAddress.setText(customer.getAddress());
        updatePostal.setText(customer.getPostalCode());
        updatePhone.setText(customer.getPhoneNumber());





    }

}

