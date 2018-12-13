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
public class User {
    
    private int idUser;
    private String username;
    private String pass;
    private int type;       // 1= Admin, 2= Customer
    private int idCustomer;

    public User(int idUser, String username, String pass, int type, int idCustomer) {
        this.idUser = idUser;
        this.username = username;
        this.pass = pass;
        this.type = type;
        this.idCustomer = idCustomer;
    }
    
    public User(String username, String pass, int type, int idCustomer) {
        this.username = username;
        this.pass = pass;
        this.type = type;
        this.idCustomer = idCustomer;
    }

    public int getIdUser() {
        return idUser;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }
    
    
}
