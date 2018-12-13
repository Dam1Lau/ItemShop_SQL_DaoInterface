/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Review;

/**
 *
 * @author dam2
 */
public interface DAOReview {
    Review get(Review t);

    List<Review> getAll();

    void save(Review t);

    void update(Review t);

    void delete(Review t);
}
