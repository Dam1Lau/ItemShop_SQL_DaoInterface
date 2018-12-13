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
import javafx.scene.control.ListView;
import model.Item;
import services.ServicesItems;


/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLdeleteItemController implements Initializable {

    @FXML
    private ListView itemBox;

    public void loadItemsList() {
        ServicesItems si = new ServicesItems();
        ArrayList items = si.getAll();
        if (items != null) {
            itemBox.getItems().setAll(items);
        } else {
            itemBox.getItems().clear();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not load the items from de database.");
            alert.showAndWait();
        }
    }

    public void deleteItem() {
        ServicesItems si = new ServicesItems();

        Item itemSelected = (Item) itemBox.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (itemSelected != null) {

            int result = si.delete(itemSelected);

            switch (result) {
                case 0:
                    alert.setTitle("Item deleted");
                    alert.setHeaderText(null);
                    alert.setContentText("The item selected was deleted.");
                    alert.showAndWait();

                    itemBox.getItems().remove(itemSelected);
                    itemBox.refresh();
                    break;
                    
                case 1:
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Could not delete the selected item.");
                    alert.setContentText("The selected item has purchases associated. Please, delete those purchases before deleting this item.");
                    alert.showAndWait();
                    
                    break;
                case 2:
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Could not execute the SQL statement.");
                    alert.setContentText("Could not do the delete item action.");
                    alert.showAndWait();
                    break;
            }
            

        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please, select an item to delete it from de database.");
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
