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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Review;
import services.ServicesReviews;

/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLchangeReviewController implements Initializable {

    @FXML
    private ListView reviewsBox;
    @FXML
    private ComboBox ratingBox;
    @FXML
    private TextField titleBox;
    @FXML
    private TextArea textBox;

    public void loadRating() {
        ServicesReviews rs = new ServicesReviews();
        ratingBox.getItems().addAll(rs.getRating());
    }

    public void loadReviews() {
        ServicesReviews rs = new ServicesReviews();
        ArrayList<Review> reviews = rs.getAll();
        reviewsBox.getItems().clear();
        
        if (reviews != null) {
            reviewsBox.getItems().addAll(reviews);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not load the information from the database");
            alert.showAndWait();
        }
    }

    public void updateReview() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        ServicesReviews rs = new ServicesReviews();
        Review review = (Review) reviewsBox.getSelectionModel().getSelectedItem();

        if (review != null) {
            if (ratingBox.getValue() != null) {

                rs.update(review, (int) ratingBox.getValue(), titleBox.getText(), textBox.getText());
                titleBox.clear();
                textBox.clear();
                loadReviews();

                alert.setTitle("Review updated!");
                alert.setHeaderText(null);
                alert.setContentText("The review was updated successfully!");
                alert.showAndWait();

            } else {
                alert.setTitle("Error");
                alert.setHeaderText("Could not update that review");
                alert.setContentText("Please, select a rating for the item reviewed.");
                alert.showAndWait();
            }

        } else {

            alert.setTitle("Empty Fields");
            alert.setHeaderText("Could not update:");
            alert.setContentText("Please, select a review from the list to update it.");
            alert.showAndWait();
        }

    }

    public void loadInfoToBox() {
        Review review = (Review) reviewsBox.getSelectionModel().getSelectedItem();
        if (review != null) {
             titleBox.setText(review.getTitle());
             textBox.setText(review.getDescription());
        
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadRating();
        //loadReviews();
    }

}
