/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.CustomersDao;
import java.util.ArrayList;
import model.Customer;
import model.User;


/**
 *
 * @author Laura
 */
public class ServicesCustomers {
    
    private final CustomersDao dao;  //INITIALIZE ON CONSTRUCTOR SO WE DONT REPEAT IT

    public ServicesCustomers() {
       this.dao =   new CustomersDao();
    }

    public ArrayList<Customer> getAllCustomers() {
        
        ArrayList<Customer> customers = (ArrayList<Customer>) dao.getAll();
        return customers;
    }

    public ArrayList<String> getShortCustomers() {

        ArrayList<Customer> custo = getAllCustomers();
        ArrayList<String> custoVisual = new ArrayList();

        for (int i = 0; i < custo.size(); i++) {
            custoVisual.add(custo.get(i).toStringShort());
        }
        return custoVisual;
    }


    public Customer searchById(int id) {
        
        Customer customer = dao.get(id);

        return customer;
    }

    public int deleteCustomer(Customer customer) {
        
        return dao.delete(customer);
    }

    public int deleteFullCustomer(Customer customer) {
        
        return dao.deleteFullCustomer(customer);
    }
    
    public int addCustomer(String username, String pass, String name, String phone, String address) {
        Customer customer = new Customer(name,phone,address);
        User user = new User(username,pass,2,0); //Type 2, customer only
        return dao.add(customer, user);
    }
    
    public void updateCustomer(Customer customer,String name, String telephone, String address){
        
        customer.setName(name);
        customer.setPhone(telephone);
        customer.setAddress(address);
        dao.update(customer);
    }

}
