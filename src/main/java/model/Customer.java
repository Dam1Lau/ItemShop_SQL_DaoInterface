/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;

/**
 * @author dam2
 */
public class Customer {

    private int idCustomer;
    private String name;
    private String phone;
    private String address;
    private Review review;

    public Customer() {
    }

    public Customer(int idCustomer, String name, String phone, String address) {
        this.idCustomer = idCustomer;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public Customer(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
    
    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String toStringShort() {
        return Integer.toString(idCustomer) + " - " + name;
    }

    public String toStringTexto() {
        return idCustomer + "/" + name + "/" + phone + "/" + address + "/" + review.toStringTexto();
    }

    public String toStringReviews() {
        String reviewMade =" ";
        if(review != null){
            reviewMade = review.toStringVisual();
        }

        return "ID: " + idCustomer + "  Name: " + name
                + "\nPhone: " + phone + "  Address: " + address
                + "\n\n======       Reviews done by this client:      ======\n\n"
                + reviewMade;
    }

    @Override
    public String toString() {
        return "ID: " + idCustomer + "  Name: " + name + "  Phone: " + phone + "  Address: " + address;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Customer other = (Customer) obj;
        if (this.idCustomer != other.idCustomer) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    

}
