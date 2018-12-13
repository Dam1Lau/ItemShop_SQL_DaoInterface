/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.reviews;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import model.Customer;
import model.Item;
import model.Review;
import services.ServicesCustomers;
import services.ServicesItems;
import services.ServicesReviews;

/**
 * FXML Controller class
 *
 * @author dam2
 */
public class FXMLfindReviewController implements Initializable {

    @FXML
    private ListView reviewList;
    @FXML
    private ComboBox itemBox;

    public void loadItems() {
        ServicesItems is = new ServicesItems();
        itemBox.getItems().setAll(is.getAll());
    }

    public void searchByItem() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        reviewList.getItems().clear();
        ServicesReviews rs = new ServicesReviews();
        Item item = (Item) itemBox.getValue();
        ArrayList<Review> reviews = rs.searchByItem(item);

        if (item != null) {
            if (reviews != null) {
                reviewList.getItems().clear();
                reviewList.getItems().setAll(reviews);
            } else {

                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Could not load the reviews from the database");
                alert.showAndWait();
            }

        } else {
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Select an item before pressing the search button.");
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
