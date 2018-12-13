/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Purchase;

/**
 *
 * @author dam2
 */
public interface DAOPurchase {
    Purchase get(Purchase t);

    List<Purchase> getAll();

    void save(Purchase t);

    void update(Purchase t);

    void delete(Purchase t);
}
