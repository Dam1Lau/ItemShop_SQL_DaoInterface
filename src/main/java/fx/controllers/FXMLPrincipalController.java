/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers;

import fx.controllers.purchases.FXMLDeleteController;
import fx.controllers.purchases.FXMLPurchasesController;
import fx.controllers.purchases.FXMLDatePurchasesController;
import fx.controllers.reviews.FXMLAddReviewController;
import fx.controllers.reviews.FXMLdeleteReviewController;
import fx.controllers.reviews.FXMLfindReviewController;
import fx.controllers.customers.FXMLAddCustomerController;
import fx.controllers.customers.FXMLdeleteCustomerController;
import fx.controllers.customers.FXMLfindCustomerController;
import fx.controllers.items.FXMLaddItemController;
import fx.controllers.items.FXMLdeleteItemController;
import fx.controllers.reviews.FXMLchangeReviewController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.User;

/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLPrincipalController implements Initializable {

    //Reference to the top menu to change its visibility when needed.
    @FXML
    private BorderPane fxRoot;

    public void setFxRoot(BorderPane fxRoot) {
        this.fxRoot = fxRoot;
    }
    //Reference to the top menus and submenus to disable them for each type of user
    @FXML
    private MenuBar fxMenuTop;
    @FXML
    private MenuItem purchaseFind;
    @FXML
    private MenuItem purchaseDelete;
    @FXML
    private Menu customerMenu;
    @FXML
    private MenuItem reviewAdd;
    @FXML
    private MenuItem reviewUpdate;
    @FXML
    private MenuItem reviewFind;
    @FXML
    private MenuItem reviewDelete;
    @FXML
    private Menu itemMenu;

    // Get y set of the user to use it wherever we need it
    private String username;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    // Whole User to send it to the different options
    private User thisUser;

    public User getThisUser() {
        return thisUser;
    }

    public void setThisUser(User thisUser) {
        this.thisUser = thisUser;
    }
    

    // References to other panes to load them and access their controllers
    private AnchorPane login;
    private FXMLLoginController loginController;
    private AnchorPane welcome;
    private FXMLWelcomeController welcomeController;

    private AnchorPane purchases;
    private FXMLPurchasesController purchasesController;
    private AnchorPane datePurchases;
    private FXMLDatePurchasesController datePurchasesController;
    private AnchorPane delete;
    private FXMLDeleteController deleteController;

    private AnchorPane addCustomer;
    private FXMLAddCustomerController addCustomerController;
    private AnchorPane findCustomer;
    private FXMLfindCustomerController findCustomerController;
    private AnchorPane deleteCustomer;
    private FXMLdeleteCustomerController deleteCustomerController;

    private AnchorPane addReview;
    private FXMLAddReviewController addReviewController;
    private AnchorPane findReview;
    private FXMLfindReviewController findReviewController;
    private AnchorPane deleteReview;
    private FXMLdeleteReviewController deleteReviewController;
    private AnchorPane updateReview;
    private FXMLchangeReviewController updateReviewController;

    private AnchorPane addItem;
    private FXMLaddItemController addItemController;
    private AnchorPane deleteItem;
    private FXMLdeleteItemController deleteItemController;

    public void preloadLogin() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/FXMLLogin.fxml"));
            login = loaderMenu.load();
            loginController
                    = loaderMenu.getController();

            loginController.setPrincipal(this);
        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void preloadWelcome() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/FXMLWelcome.fxml"));
            welcome = loaderMenu.load();
            welcomeController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadPurchases() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/purchases/FXMLPurchases.fxml"));
            purchases = loaderMenu.load();
            purchasesController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void preloadDatePurchases() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/purchases/FXMLDatePurchases.fxml"));
            datePurchases = loaderMenu.load();
            datePurchasesController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void preloadDelete() {

        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/purchases/FXMLDelete.fxml"));
            delete = loaderMenu.load();
            deleteController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void preloadAddCustomer() {

        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/customers/FXMLAddCustomer.fxml"));
            addCustomer = loaderMenu.load();
            addCustomerController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void preloadFindCustomer() {

        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/customers/FXMLfindCustomer.fxml"));
            findCustomer = loaderMenu.load();
            findCustomerController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void preloadDeleteCustomer() {

        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/customers/FXMLdeleteCustomer.fxml"));
            deleteCustomer = loaderMenu.load();
            deleteCustomerController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void preloadAddReview() {

        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/reviews/FXMLaddReview.fxml"));
            addReview = loaderMenu.load();
            addReviewController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void preloadFindReview() {

        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/reviews/FXMLfindReview.fxml"));
            findReview = loaderMenu.load();
            findReviewController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void preloadDeleteReview() {

        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/reviews/FXMLdeleteReview.fxml"));
            deleteReview = loaderMenu.load();
            deleteReviewController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void preloadUpdateReview() {

        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/reviews/FXMLchangeReview.fxml"));
            updateReview = loaderMenu.load();
            updateReviewController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void preloadAddItem() {

        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/items/FXMLaddItem.fxml"));
            addItem = loaderMenu.load();
            addItemController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void preloadDeleteItem() {

        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/items/FXMLdeleteItem.fxml"));
            deleteItem = loaderMenu.load();
            deleteItemController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void chargeLogin() {
        enableAllMenus();
        loginController.logout();
        fxRoot.setCenter(login);
        fxMenuTop.setVisible(false);
    }
    public void chargeWelcome() {
        welcomeController.setLogin(this.getUsername());
        fxMenuTop.setVisible(true);
        fxRoot.setCenter(welcome);
    }
    
    public void chargePurchases() {
        if(thisUser.getType() == 1){        // Is an admin (load all)
             purchasesController.loadAll();
        }else if(thisUser.getType() == 2){  // Is a customer
             purchasesController.loadForCustomer(thisUser.getIdCustomer());
        }
       
        fxRoot.setCenter(purchases);
    }
    public void chargeDatePurchases() {
        fxRoot.setCenter(datePurchases);
    }
    public void chargeDelete() {
        deleteController.loadPurchases();
        fxRoot.setCenter(delete);
    }

    public void chargeAddCustomer() {
        addCustomerController.loadCustomersList();
        fxRoot.setCenter(addCustomer);
    }
    public void chargeFindCustomer() {
        fxRoot.setCenter(findCustomer);
    }
    public void chargeDeleteCustomer() {
        deleteCustomerController.loadCustomersList();
        fxRoot.setCenter(deleteCustomer);
    }

    public void chargeAddReview() {
        if(thisUser.getType() == 1){ // Is an admin (load all)
            addReviewController.loadCustomers();
        }else if(thisUser.getType() == 2){  //Is a customer
            addReviewController.loadCustomerOnly(thisUser.getIdCustomer());
        }
        fxRoot.setCenter(addReview);
    }
    public void chargeDeleteReview() {
        deleteReviewController.loadReviewsList();
        fxRoot.setCenter(deleteReview);
    }
    public void chargeFindReview() {
        findReviewController.loadItems();
        fxRoot.setCenter(findReview);
    }
    public void chargeUpdateReview() {
        updateReviewController.loadReviews();
        fxRoot.setCenter(updateReview);
    }

    public void chargeAddItem() {
        addItemController.loadItemsList();
        fxRoot.setCenter(addItem);
    }
    public void chargeDeleteItem() {
        deleteItemController.loadItemsList();
        fxRoot.setCenter(deleteItem);
    }
    
    
    public void disableMenus(User user) {
        int type = user.getType();
        switch (type) {
            case 1:  //Admin
                reviewAdd.setDisable(true);
                break;

            case 2:  //Customer
                disableMenusForCustomer();
                break;
        }

    }

    public void disableMenusForCustomer() {
        purchaseFind.setDisable(true);
        purchaseDelete.setDisable(true);
        customerMenu.setDisable(true);
        reviewUpdate.setDisable(true);
        reviewFind.setDisable(true);
        reviewDelete.setDisable(true);
        itemMenu.setDisable(true);
    }

    public void enableAllMenus() {
        purchaseFind.setDisable(false);
        purchaseDelete.setDisable(false);
        customerMenu.setDisable(false);
        reviewAdd.setDisable(false);
        reviewUpdate.setDisable(false);
        reviewFind.setDisable(false);
        reviewDelete.setDisable(false);
        itemMenu.setDisable(false);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preloadWelcome();
        preloadLogin();

        preloadPurchases();
        preloadDatePurchases();
        preloadDelete();

        preloadAddCustomer();
        preloadFindCustomer();
        preloadDeleteCustomer();

        preloadAddReview();
        preloadDeleteReview();
        preloadFindReview();
        preloadUpdateReview();

        preloadAddItem();
        preloadDeleteItem();

        chargeLogin();

    }

}
