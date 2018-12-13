/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.User;

/**
 *
 * @author dam2
 */
public interface DAOUser {
    User get(User t);

    List<User> getAll();

    void save(User t);

    void update(User t);

    void delete(User t);
}
