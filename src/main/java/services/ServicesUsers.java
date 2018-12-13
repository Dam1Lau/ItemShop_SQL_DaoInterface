/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.UsersDao;
import model.User;

/**
 *
 * @author Laura
 */
public class ServicesUsers {

     UsersDao dao;
    
    public ServicesUsers() {
        dao = new UsersDao();
    }
    
    public User getUser(String username) {
        return dao.get(username);
    }

    public boolean checkUser(String username, String password) {
        User user = dao.get(username);
        boolean match = false;
        
        if(user != null){
            if(user.getPass().equals(password)){
                match = true;
            }
        }
        
        return match;
    }

}
