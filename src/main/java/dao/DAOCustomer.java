/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Customer;

/**
 *
 * @author dam2
 */
public interface DAOCustomer {

    Customer get(Customer t);

    List<Customer> getAll();

    void save(Customer t);

    void update(Customer t);

    void delete(Customer t);
}
