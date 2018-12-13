/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Review;
import dao.utilities.ConnectionUtilities;
import dao.utilities.DBConnection;
import dao.utilities.SQLStatements;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDate;
import model.Customer;
import model.Item;
import model.Purchase;

/**
 *
 * @author Laura
 */
public class ReviewsDao {

    public List<Review> getAll() {
        ArrayList<Review> reviews = new ArrayList();
        PurchasesDao dao = new PurchasesDao();

        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ResultSet rp = null;

        try {

            con = db.getConnection();
            stmt = con.prepareStatement(SQLStatements.SELECT_all_reviews);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idReview = rs.getInt("idReview");
                int rating = rs.getInt("rating");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String date = rs.getString("date");
                int idPurchase = rs.getInt("idPurchase");

                Purchase reviewPurchase = dao.getById(idPurchase);
                int idItem = reviewPurchase.getItem().getIdItem();
                int idCustomer = reviewPurchase.getCustomer().getIdCustomer();

                reviews.add(new Review(idReview, rating, title, description, idCustomer, idItem, idPurchase));
            }

        } catch (IOException | SQLException ex) {
            Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ReviewsDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeResultSet(rs);
            db.closeStatement(stmt);
            db.closeConexion(con);
        }

        return reviews;
    }

    public List<Integer> getByPurchase(int id) {
        ArrayList<Integer> reviews = new ArrayList();

        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ResultSet rp = null;

        try {
            con = db.getConnection();
            stmt = con.prepareStatement(SQLStatements.SELECT_reviews_ByPurchase);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idReview = rs.getInt("idReview");
                reviews.add(idReview);
            }

        } catch (IOException | SQLException ex) {
            Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ReviewsDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeResultSet(rs);
            db.closeStatement(stmt);
            db.closeConexion(con);
        }

        return reviews;

    }

    public List<Review> getByItem(Item item) {
        ArrayList<Review> reviews = new ArrayList();

        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ResultSet rp = null;

        try {

            con = db.getConnection();
            stmt = con.prepareStatement(SQLStatements.SELECT_reviews_ByItem);
            stmt.setInt(1, item.getIdItem());
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idReview = rs.getInt("idReview");
                int rating = rs.getInt("rating");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String date = rs.getString("date");
                int idPurchase = rs.getInt("idPurchase");

                Review review = new Review(idReview, rating, title, description, LocalDate.parse(date), idPurchase);
                reviews.add(review);
            }

        } catch (IOException | SQLException ex) {
            Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ReviewsDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeResultSet(rs);
            db.closeStatement(stmt);
            db.closeConexion(con);
        }

        return reviews;

    }

    public int add(Review review) {

        int result = 0;

        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            con = db.getConnection();

            stmt = con.prepareStatement(SQLStatements.INSERT_review, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, review.getRating());
            stmt.setString(2, review.getTitle());
            stmt.setString(3, review.getDescription());
            stmt.setString(4, review.getDate().toString());
            stmt.setInt(5, review.getPurchaseId());

            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();  //THIS RETRIEVES THE NEW ID FOR THE review
            if (rs != null && rs.next()) {           //This assigns the retrieved Id to this customer
                review.setIdReview(rs.getInt(1));
            }

        } catch (SQLIntegrityConstraintViolationException ex) {
            result = 1;
        } catch (IOException | SQLException ex) {
            Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ReviewsDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeResultSet(rs);
            db.closeStatement(stmt);
            db.closeConexion(con);
        }
        return result;
    }

    public int delete(Review review) {

        int result = 0;
        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement stmt = null;

        try { 
            con = db.getConnection();

            stmt = con.prepareStatement(SQLStatements.DELETE_review);
            stmt.setInt(1, review.getIdReview());
            stmt.executeUpdate();

        }catch (Exception ex) {  //ROLLBACK THE DELETE
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(ReviewsDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeStatement(stmt);
            db.closeConexion(con);
        }
        return result;
    }


    public int update(Review review) {
        int result = 0;
        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            
            con = db.getConnection();

            stmt = con.prepareStatement(SQLStatements.UPDATE_review);
            stmt.setInt(1, review.getRating());
            stmt.setString(2, review.getTitle());
            stmt.setString(3, review.getDescription());
            stmt.setInt(4, review.getIdReview());

            stmt.executeUpdate();

        }catch (Exception ex) {
            result = 1;
            Logger.getLogger(ReviewsDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeStatement(stmt);
            db.closeConexion(con);
        }
        return result;
    }

}
