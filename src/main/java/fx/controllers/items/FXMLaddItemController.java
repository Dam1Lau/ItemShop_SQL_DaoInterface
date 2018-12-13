/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.items;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Item;
import services.ServicesItems;

/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLaddItemController implements Initializable {

    @FXML
    private ListView itemList;
    @FXML
    private TextField nameBox;
    @FXML
    private TextField companyBox;
    @FXML
    private TextField priceBox;

    Alert alert = new Alert(AlertType.INFORMATION);

    public void loadItemsList() {
        ServicesItems si = new ServicesItems();
        ArrayList<Item> items = si.getAll();
        if (items != null) {
            itemList.getItems().setAll(items);
        } else {
            alert.setTitle("Error loading the items");
            alert.setContentText("Could not load the item list.");
            alert.showAndWait();
        }
    }
    
    public void loadData(){
        Item item = (Item) itemList.getSelectionModel().getSelectedItem();
        if(item != null){
            nameBox.setText(item.getName());
            companyBox.setText(item.getCompany());
            priceBox.setText(String.valueOf(item.getPrice()));
        }
    }

    public void addItem() {

        ServicesItems si = new ServicesItems();

        try {
            Double.parseDouble(priceBox.getText());
            if (!nameBox.getText().equals("")
                    && !companyBox.getText().equals("") && !priceBox.getText().equals("")) {

                si.add(nameBox.getText(), companyBox.getText(), Double.parseDouble(priceBox.getText()));
                loadItemsList();
                alert.setTitle("Success!");
                alert.setHeaderText("New item added:");
                alert.setContentText("The new item was added to the database. It should appear in the list.");
                alert.showAndWait();

            } else {
                alert.setTitle("Empty Fields");
                alert.setHeaderText("Could not add the new item:");
                alert.setContentText("Name, company or price fields are empty. Please fill the fields with valid information.");
                alert.showAndWait();
            }

        } catch (NumberFormatException e) {
            alert.setTitle("The price is not a valid number");
            alert.setHeaderText("Could not add the new item:");
            alert.setContentText("The price is not a valid number (Correct Sample: 10.05)");
            alert.showAndWait();
        }

    }

    public void updateItem() {
        ServicesItems si = new ServicesItems();
        Item item = (Item) itemList.getSelectionModel().getSelectedItem();
        if (item != null) {
            try {
                Double.parseDouble(priceBox.getText());
                if (!nameBox.getText().equals("")
                        && !companyBox.getText().equals("") && !priceBox.getText().equals("")) {

                    si.update(item, nameBox.getText(), companyBox.getText(), Double.parseDouble(priceBox.getText()));
                    loadItemsList();
                    
                    alert.setTitle("Success!");
                    alert.setHeaderText("Item updated:");
                    alert.setContentText("The selected item was updated with the new information.");
                    alert.showAndWait();

                } else {
                    alert.setTitle("Empty Fields");
                    alert.setHeaderText("Could not add the new customer:");
                    alert.setContentText("Name or Telephone fields are empty. Please fill the fields with valid ifnromation.");
                    alert.showAndWait();
                }

            } catch (NumberFormatException e) {
                alert.setTitle("The price is not a valid number");
                alert.setHeaderText("Could not add the new item:");
                alert.setContentText("The price is not a valid number (Correct Sample: 10.05)");
                alert.showAndWait();
            }
        } else {
            alert.setTitle("Error");
            alert.setHeaderText("Select an item:");
            alert.setContentText("Please, select an item before updating all its fields.");
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
