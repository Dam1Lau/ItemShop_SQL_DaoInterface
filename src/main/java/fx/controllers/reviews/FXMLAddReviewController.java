/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.reviews;

import fx.controllers.FXMLPrincipalController;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Customer;
import model.Purchase;
import model.Review;
import services.ServicesCustomers;
import services.ServicesPurchases;
import services.ServicesReviews;

/**
 * FXML Controller class
 *
 * @author dam2
 */
public class FXMLAddReviewController implements Initializable {

    @FXML
    private ListView clientBox;
    @FXML
    private ListView purchaseBox;
    @FXML
    private ComboBox ratingBox;
    @FXML
    private TextField titleBox;
    @FXML
    private TextArea textBox;
    
    private FXMLPrincipalController principal;

    public void loadCustomers() {
        ServicesCustomers rs = new ServicesCustomers();
        ArrayList<Customer> customers = rs.getAllCustomers();
        if (customers != null) {
            clientBox.getItems().setAll(customers);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not load the customers from the database");
            alert.showAndWait();
        }

    }

    public void loadRating() {
        ServicesReviews rs = new ServicesReviews();
        ratingBox.getItems().addAll(rs.getRating());

    }

    public void loadPurchases() {
        ServicesPurchases ps = new ServicesPurchases();
        ArrayList<Purchase> purchases = ps.getAllPurchases();
        if (!purchases.isEmpty()) {
            int customerId = ((Customer) clientBox.getSelectionModel().getSelectedItem()).getIdCustomer();
            purchaseBox.getItems().setAll(ps.getPurchaseByCustomerId(customerId));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not load the information from the database");
            alert.showAndWait();
        }

    }

    public void addReview() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        ServicesReviews rs = new ServicesReviews();

        if (clientBox.getSelectionModel().getSelectedItem() != null
                && purchaseBox.getSelectionModel().getSelectedItem() != null) {

            if (ratingBox.getValue() != null) {

                Review review = new Review(Integer.parseInt(ratingBox.getValue().toString()), titleBox.getText(),
                        textBox.getText(),
                        ((Customer) (clientBox.getSelectionModel().getSelectedItem())).getIdCustomer(),
                        ((Purchase) (purchaseBox.getSelectionModel().getSelectedItem())).getItem().getIdItem(),
                        ((Purchase) (purchaseBox.getSelectionModel().getSelectedItem())).getIdPurchase());

                rs.add(review);
                purchaseBox.getItems().clear();
                titleBox.clear();
                textBox.clear();

                alert.setTitle("Review added!");
                alert.setHeaderText(null);
                alert.setContentText("The review was saved successfully!");
                alert.showAndWait();

            } else {
                alert.setTitle("Empty Fields");
                alert.setHeaderText("Could not add the new review:");
                alert.setContentText("Please, select a rating for the item reviewed.");
                alert.showAndWait();
            }
        } else {

            alert.setTitle("Empty Fields");
            alert.setHeaderText("Could not add the new review:");
            alert.setContentText("Please, select a customer and a purchase.");
            alert.showAndWait();
        }

    }
    
    public void loadCustomerOnly(int id){
        ServicesCustomers rs = new ServicesCustomers();
        Customer cust = rs.searchById(id);
        clientBox.getItems().clear();
        clientBox.getItems().setAll(cust);
        clientBox.getSelectionModel().clearAndSelect(0);
        clientBox.disableProperty();
        loadPurchases();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadRating();
        
    }

}
