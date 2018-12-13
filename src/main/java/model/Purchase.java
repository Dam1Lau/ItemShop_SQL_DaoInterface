/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Laura
 */
public class Purchase {

    private int idPurchase;
    private Customer customer;
    private Item item;
    private String date;

    public Purchase() {
    }

    public Purchase(int idPurchase, Customer customer, Item item, String date) {
        this.idPurchase = idPurchase;
        this.customer = customer;
        this.item = item;
        this.date = date;
    }

    public Purchase(Customer customer, Item item, String date) {
        this.customer = customer;
        this.item = item;
        this.date = date;
    }
    

    public int getIdPurchase() {
        return idPurchase;
    }

    public void setIdPurchase(int idPurchase) {
        this.idPurchase = idPurchase;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ID: " + idPurchase + "  Customer: " + customer.getIdCustomer() + "  Item: " + item + "  Date: " + date;
    }
    
    public String toStringForClientInfo() {
        return "ID: " + idPurchase + "  Item: " + item + "  Date: " + date + "\n";
    }

    public String toStringTexto() {
        return idPurchase + ";" + customer.getIdCustomer() + ";" + item + ";" + date;
    }
}
