/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers;

import configuration.ConfigYaml;
import fx.controllers.reviews.FXMLAddReviewController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.User;
import services.ServicesUsers;

/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLLoginController implements Initializable {


    @FXML
    private TextField fxUser;
    @FXML
    private TextField passBox;
    @FXML
    private Label errorBox;

 
    private FXMLPrincipalController principal;
    public void setPrincipal(FXMLPrincipalController principal) {
        this.principal = principal;
    }

    public void logout() {
        fxUser.clear();
        passBox.clear();
        errorBox.setText(" ");
    }

    public void clickLoginOld() {

        String user = fxUser.getText();
        String pass = passBox.getText();

        if (fxUser.getText().equals(ConfigYaml.getInstance().getUser())
                && passBox.getText().equals(ConfigYaml.getInstance().getPass())) {
            principal.setUsername(fxUser.getText());
            principal.chargeWelcome();
        } else {
            errorBox.setText("User or password is wrong");
        }

    }

    public void clickLogin() {
        ServicesUsers su = new ServicesUsers();
        String user = fxUser.getText();
        String pass = passBox.getText();

        if (su.checkUser(user, pass)) {
            User thisUser = su.getUser(user);
            principal.setThisUser(thisUser);    //Send user + id to principal to know its data later
            principal.disableMenus(thisUser);  //Disabe menus depending on which type os user I got
            principal.setUsername(fxUser.getText());
            principal.chargeWelcome();

        } else {
            errorBox.setText("User or password is wrong.");
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
