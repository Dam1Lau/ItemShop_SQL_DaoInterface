/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.customers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Customer;
import services.ServicesCustomers;

/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLAddCustomerController implements Initializable {

    @FXML
    private TextField nameBox;
    @FXML
    private TextField phoneBox;
    @FXML
    private TextField addressBox;
    @FXML
    private ListView customerList;

    @FXML
    private TextField usernameBox;
    @FXML
    private TextField passBox;

    public void loadCustomersList() {
        ServicesCustomers cs = new ServicesCustomers();
        ArrayList<Customer> customers = cs.getAllCustomers();
        customerList.getItems().clear();
        if (customers != null) {
            customerList.getItems().setAll(cs.getAllCustomers());
        } else {
            customerList.getItems().setAll("Could not retrieve the customers information.");
        }

    }

    public void addCustomer() {
        Alert alert = new Alert(AlertType.INFORMATION);
        ServicesCustomers cs = new ServicesCustomers();

        if (!usernameBox.getText().equals("")
                && !passBox.getText().equals("")
                && !nameBox.getText().equals("")
                && !phoneBox.getText().equals("")
                && !usernameBox.getText().equals("")
                && !passBox.getText().equals("")) {
            int result = cs.addCustomer(usernameBox.getText(), passBox.getText(), nameBox.getText(), phoneBox.getText(), addressBox.getText());
            loadCustomersList();

            switch (result) {
                case 0:
                    nameBox.clear();
                    phoneBox.clear();
                    addressBox.clear();
                    usernameBox.clear();
                    passBox.clear();

                    alert.setTitle("Success!");
                    alert.setHeaderText("New customer added.");
                    alert.setContentText("The new customer was added to the database.");
                    alert.showAndWait();
                    break;

                case 1:
                    alert.setAlertType(AlertType.ERROR);
                    alert.setTitle("ERROR!");
                    alert.setHeaderText("Could not add a new customer and/or its user account.");
                    alert.setContentText("The username already exists.");
                    alert.showAndWait();
                    break;
                default:
                    alert.setAlertType(AlertType.ERROR);
                    alert.setTitle("ERROR!");
                    alert.setHeaderText("Could not add a new customer and/or its user account.");
                    alert.setContentText("Database error.");
                    alert.showAndWait();
            }

        } else {

            alert.setTitle("Empty Fields");
            alert.setHeaderText("Could not add the new customer:");
            alert.setContentText("Username, password, name or telephone fields are empty. Please fill the fields with valid ifnromation.");
            alert.showAndWait();
        }

    }

    public void loadDataInBox() {
        Customer cust = (Customer) customerList.getSelectionModel().getSelectedItem();
        if (cust != null) {
            nameBox.setText(cust.getName());
            phoneBox.setText(cust.getPhone());
            addressBox.setText(cust.getAddress());
        }
    }

    public void updateCustomer() {
        Alert alert = new Alert(AlertType.INFORMATION);
        ServicesCustomers cs = new ServicesCustomers();

        Customer cust = (Customer) customerList.getSelectionModel().getSelectedItem();
        if (cust != null) {
            if (!nameBox.getText().equals("")
                    && !phoneBox.getText().equals("")) {

                cs.updateCustomer(cust, nameBox.getText(), phoneBox.getText(), addressBox.getText());
                nameBox.clear();
                phoneBox.clear();
                addressBox.clear();
                loadCustomersList();

                alert.setTitle("Success!");
                alert.setHeaderText("Customer's information was updated!");
                alert.setContentText("The selected customer's information was updated in all its fields.");
                alert.showAndWait();

            } else {

                alert.setTitle("Empty Fields");
                alert.setHeaderText("Could not update the selected customer:");
                alert.setContentText("Name or Telephone fields are empty. Please fill the fields with valid ifnromation.");
                alert.showAndWait();
            }

        } else {
            alert.setTitle("Empty Selection");
            alert.setHeaderText("Could not udate the customer:");
            alert.setContentText("Please, select a customer from the list to update it.");
            alert.showAndWait();
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
