/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.CustomersDao;
import dao.ReviewsDao;
import java.util.ArrayList;
import model.Customer;
import model.Item;
import model.Review;

/**
 *
 * @author Laura
 */
public class ServicesReviews {


    public ArrayList<Review> getAll() {
       
        ReviewsDao rdao = new ReviewsDao();
        ArrayList<Review> rev = (ArrayList<Review>) rdao.getAll();
        return rev;
    }

    public void delete(Review review) {
        ReviewsDao rdao = new ReviewsDao();
        rdao.delete(review);
    }

    public ArrayList<Review> searchByItem(Item item) {
        ReviewsDao dao = new ReviewsDao();
        ArrayList<Review> allReviews = (ArrayList<Review>) dao.getByItem(item);

        return allReviews;
    }
    
    public ArrayList<Integer> searchByPurchaseId(int id){
        ReviewsDao rdao = new ReviewsDao();
        ArrayList<Integer> reviews = (ArrayList<Integer>) rdao.getByPurchase(id);
        return reviews;
    }

    public void add(Review review) {
        ReviewsDao dao = new ReviewsDao();
        dao.add(review);
    }
    
    public void update(Review review, int rating, String title, String description){
        ReviewsDao dao = new ReviewsDao();
        review.setRating(rating);
        review.setTitle(title);
        review.setDescription(description);
        dao.update(review);
    }
    
    public ArrayList<Integer> getRating(){
        ArrayList<Integer> ratings = new ArrayList();
        for (int i = 0; i < 5; i++) {
            ratings.add(i, (Integer) i + 1);
        }
        return ratings;
    }

}
