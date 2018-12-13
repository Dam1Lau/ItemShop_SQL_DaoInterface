/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Item;

/**
 *
 * @author dam2
 */
public interface DAOItem {
    Item get(Item t);

    List<Item> getAll();

    void save(Item t);

    void update(Item t);

    void delete(Item t);
}
