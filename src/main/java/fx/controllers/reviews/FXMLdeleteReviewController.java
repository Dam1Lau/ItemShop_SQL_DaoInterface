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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import model.Customer;
import model.Review;
import services.ServicesReviews;

/**
 * FXML Controller class
 *
 * @author dam2
 */
public class FXMLdeleteReviewController implements Initializable {

    
    @FXML
    private ListView reviewBox;
    
     ArrayList<Customer> customers;

    public void loadReviewsList() {
        ServicesReviews rs = new ServicesReviews();
        reviewBox.getItems().clear();
        ArrayList<Review> reviews = rs.getAll();
        if(reviews != null){
            reviewBox.getItems().setAll(reviews);
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not find reviews to load.");
            alert.showAndWait();
        }
    }


    public void deleteReview() {
        ServicesReviews cs = new ServicesReviews();
        Alert alert = new Alert(AlertType.INFORMATION);

        Review review = (Review) reviewBox.getSelectionModel().getSelectedItem();
        if (review != null) {

            cs.delete(review);
            
            reviewBox.getItems().clear();
            loadReviewsList();

            alert.setTitle("Review deleted");
            alert.setHeaderText(null);
            alert.setContentText("The review was deleted successfully!");
            alert.showAndWait();
            
        } else {
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please, select a review from the list.");
            alert.showAndWait();
        }
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //loadReviewsList();
    }

}
