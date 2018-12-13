/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.CustomersDao;
import dao.ItemsDao;
import dao.PurchasesDao;
import java.util.ArrayList;
import model.Customer;
import model.Item;
import model.Purchase;

/**
 *
 * @author dam2
 */
public class ServicesPurchases {

    public ArrayList<Purchase> getAllPurchases() {
        PurchasesDao dao = new PurchasesDao();
        return (ArrayList<Purchase>) dao.getAll();
    }

    public ArrayList<Purchase> searchByDate(String date) {
        PurchasesDao dao = new PurchasesDao();
        ArrayList<Purchase> purchasesDate = (ArrayList<Purchase>) dao.get(date);
        return purchasesDate;
    }

    public ArrayList<Purchase> getPurchaseByCustomerId(int id) {
        PurchasesDao dao = new PurchasesDao();
        ArrayList<Purchase> purchases = (ArrayList<Purchase>) dao.getAll();
        ArrayList<Purchase> clientPurchases = new ArrayList();
        for (int i = 0; i < purchases.size(); i++) {
            if(purchases.get(i).getCustomer().getIdCustomer() == id){
                clientPurchases.add(purchases.get(i));
            }
        }
        return clientPurchases;

    }
    
    public ArrayList<Integer> getPurchasesIdByItemId(int id){
        PurchasesDao dao = new PurchasesDao();
        ArrayList<Integer> purchases = (ArrayList<Integer>) dao.getByItemId(id);
        return purchases;
    
    } 

    public int deletePurchase(Purchase purchase) {
        PurchasesDao dao = new PurchasesDao();
        return dao.delete(purchase);
    }
    

    public Purchase addPurchase(Customer customer, Item item, String date) {
        PurchasesDao dao = new PurchasesDao();
        CustomersDao cdao = new CustomersDao();
        ItemsDao idao = new ItemsDao();

        Purchase newPurchase = new Purchase(customer, item, date);
        dao.add(newPurchase);

        return newPurchase;
    }
    
    

    
}
