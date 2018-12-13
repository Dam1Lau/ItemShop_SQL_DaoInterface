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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import model.Purchase;
import services.ServicesPurchases;


/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLDatePurchasesController implements Initializable {

    @FXML
    private DatePicker dateBox;
    @FXML
    private ListView purchaseList;

    public void loadPurchasesList() {
        ServicesPurchases ps = new ServicesPurchases();
        purchaseList.getItems().setAll(ps.getAllPurchases());
    }

    public void searchByDate() {
        purchaseList.getItems().clear();
        ServicesPurchases ps = new ServicesPurchases();

        if (dateBox.getValue() == null) {
            purchaseList.getItems().setAll(ps.getAllPurchases());
        } else {
            ArrayList<Purchase> purchases = ps.searchByDate(dateBox.getValue().toString());
            if(purchases != null){
                purchaseList.getItems().setAll(purchases);
            }else{
                purchaseList.getItems().add("No purchases found for that date.");
            }
            
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

}
