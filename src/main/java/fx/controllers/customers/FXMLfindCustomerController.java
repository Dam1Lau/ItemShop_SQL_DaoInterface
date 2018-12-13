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
import model.Purchase;
import services.ServicesCustomers;
import services.ServicesPurchases;

/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLfindCustomerController implements Initializable {

    @FXML
    private TextField dniBox;
    @FXML
    private ListView customerList;

    public void searchById() {
        customerList.getItems().clear();
        ServicesCustomers cs = new ServicesCustomers();
        ServicesPurchases sp = new ServicesPurchases();
        ArrayList<Customer> customers = cs.getAllCustomers();

        if (customers != null) {
            if (dniBox.getText().equalsIgnoreCase("")) {
                customerList.getItems().setAll(customers);
            } else {
                int id = Integer.parseInt(dniBox.getText());
                Customer customer = cs.searchById(id);
                ArrayList<Purchase> purchases = sp.getPurchaseByCustomerId(id);
                if (customer != null && purchases != null) {
                    customerList.getItems().setAll(customer);
                    customerList.getItems().add("\n=== Purchases made by this customer ===");
                    customerList.getItems().addAll(purchases);
                } else {
                    customerList.getItems().setAll("Customer not found.");
                }
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Did not find customers in the database.");
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
