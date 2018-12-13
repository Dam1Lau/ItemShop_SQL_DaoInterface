/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.purchases;

import dao.ItemsDao;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import model.Customer;
import model.Item;
import services.ServicesCustomers;
import services.ServicesPurchases;

/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLPurchasesController implements Initializable {

    @FXML
    private ComboBox customerBox;
    @FXML
    private ComboBox itemBox;
    @FXML
    private ListView purchaseList;
    @FXML
    private DatePicker dateBox;

    public void loadPurchasesList() {
        ServicesPurchases ps = new ServicesPurchases();
        purchaseList.getItems().setAll(ps.getAllPurchases());
    }

    public void loadItemsList() {

        ItemsDao idao = new ItemsDao();
        itemBox.getItems().setAll(idao.getAll());

    }

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

    public void addPurchase() {
        Alert alert = new Alert(AlertType.INFORMATION);
        ServicesPurchases ps = new ServicesPurchases();
        Customer cu = (Customer)customerBox.getSelectionModel().getSelectedItem();
        Item it = (Item) itemBox.getSelectionModel().getSelectedItem();

        if (it != null
                && cu != null
                && dateBox.getValue() != null) {
            purchaseList.getItems().add(ps.addPurchase(cu,it, dateBox.getValue().toString()));
            alert.setTitle("Sucess!");
            alert.setHeaderText("New purchase added.");
            alert.setContentText("The new purchase was added to the database.");
            alert.showAndWait();

        } else {

            alert.setTitle("Empty Fields");
            alert.setHeaderText("Could not add the new purchase:");
            alert.setContentText("One ore more fields are empty. Please fill all the fields before adding a purchase.");
            alert.showAndWait();
        }

    }
    
    public void loadForCustomer(int idCustomer){
        ServicesPurchases ps = new ServicesPurchases();
        ServicesCustomers cs = new ServicesCustomers();
        purchaseList.getItems().setAll(ps.getPurchaseByCustomerId(idCustomer));
        customerBox.getItems().setAll(cs.searchById(idCustomer));
        customerBox.getSelectionModel().clearAndSelect(0);
        customerBox.disableProperty();
        loadItemsList();
    }

    public void loadAll() {
        loadCustomersList();
        loadPurchasesList();
        loadItemsList();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //loadAll();
    }
}
