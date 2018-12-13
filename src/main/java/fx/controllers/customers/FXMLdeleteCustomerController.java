/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.customers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import model.Customer;
import services.ServicesCustomers;
import services.ServicesPurchases;
import services.ServicesReviews;

/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLdeleteCustomerController implements Initializable {

    @FXML
    private ListView customerBox;

    public void loadCustomersList() {
        ServicesCustomers cs = new ServicesCustomers();
        ArrayList<Customer> customers = cs.getAllCustomers();
        if (customers != null) {
            customerBox.getItems().setAll(customers);
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not load the customers from the database");
            alert.showAndWait();
        }

    }

    public void deleteCustomer() {
        ServicesCustomers cs = new ServicesCustomers();
        ServicesReviews sr = new ServicesReviews();
        ServicesPurchases sp = new ServicesPurchases();

        Alert alert = new Alert(AlertType.INFORMATION);
        Customer customer = (Customer) customerBox.getSelectionModel().getSelectedItem();

        if (customer != null) {

            int result = cs.deleteCustomer(customer);

            switch (result) {
                case 0:
                    customerBox.getItems().clear();
                    loadCustomersList();

                    alert.setTitle("Purchase deleted");
                    alert.setHeaderText(null);
                    alert.setContentText("The customer was deleted successfully!");
                    alert.showAndWait();

                    break;
                case 1:
                    alert.setAlertType(AlertType.CONFIRMATION);
                    alert.setTitle("WARNING");
                    alert.setHeaderText("This customer has purchases registered. Would you like to delete"
                            + " the customer and ALL its data (purchases, reviews made and user account):");
                    Optional<ButtonType> option = alert.showAndWait();

                    if ((option.isPresent()) && (option.get() == ButtonType.OK)) {
                        int done = cs.deleteFullCustomer(customer);
                        if (done == 0) {
                            customerBox.getItems().clear();
                            loadCustomersList();
                        } else {
                            alert.setAlertType(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Could not execute the SQL statement.");
                            alert.setContentText("Could not execute the delete customer action. Error with the data or could not finish the action.");
                            alert.showAndWait();
                        }
                    }
                    break;
                case 2:
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Could not execute the SQL statement.");
                    alert.setContentText("Could not do the delete customer action.");
                    alert.showAndWait();
                    break;
            }

        } else {
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please, select a customer from the list.");
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
