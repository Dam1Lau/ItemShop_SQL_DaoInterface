/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.ItemsDao;
import java.util.ArrayList;
import model.Item;

/**
 *
 * @author dam2
 */
public class ServicesItems {
    
    public ArrayList<Item> getAll() {
        ItemsDao dao = new ItemsDao();
        return (ArrayList<Item>) dao.getAll();
    }

    public int delete(Item item) {
        
        ItemsDao dao = new ItemsDao();
        return dao.delete(item);
        
    }

    public Item add(String name, String company, double price) {
        Item item = new Item(name, company, price);
        ItemsDao dao = new ItemsDao();
        dao.add(item);

        return item;
    }

    public void update(Item item, String name, String company, double price) {
        ItemsDao dao = new ItemsDao();
        item.setName(name);
        item.setCompany(company);
        item.setPrice(price);
        dao.update(item);
    }

}
