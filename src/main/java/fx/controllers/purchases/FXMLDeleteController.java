/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.purchases;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import services.ServicesPurchases;
import model.Purchase;
import services.ServicesReviews;

/**
 * FXML Controller class
 *
 * @author dam2
 */
public class FXMLDeleteController implements Initializable {

    @FXML
    private ListView purchaseBox;

    public void loadPurchases() {
        ServicesPurchases ps = new ServicesPurchases();
        purchaseBox.getItems().setAll(ps.getAllPurchases());

    }

    public void deletePurchase() {
        ServicesPurchases ps = new ServicesPurchases();
        ServicesReviews rs = new ServicesReviews();
        Alert alert = new Alert(AlertType.INFORMATION);

        Purchase purchase = (Purchase) purchaseBox.getSelectionModel().getSelectedItem();

        if (purchase != null) {

            int result = ps.deletePurchase(purchase);

            switch (result) {
                case 0:
                    alert.setTitle("Purchase deleted");
                    alert.setHeaderText(null);
                    alert.setContentText("The purchase that you selected was deleted.");
                    alert.showAndWait();

                    purchaseBox.getItems().clear();
                    loadPurchases();
                    break;

                case 1:
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Could not delete the selected purchase.");
                    alert.setContentText("This purchase has reviews associated. Please"
                        + "delete those reviews before trying to erase this purchase.");
                    alert.showAndWait();

                    break;
                case 2:
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Could not execute the SQL statement.");
                    alert.setContentText("Could not do the delete item action."
                            + "There was an error within the database.");
                    alert.showAndWait();
                    break;
            }

        } else {
            alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please, select a purchase from the list.");
            alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //loadPurchases();
    }

}
