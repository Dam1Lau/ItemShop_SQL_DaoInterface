/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;
import model.Item;
import model.Purchase;
import dao.utilities.ConnectionUtilities;
import dao.utilities.DBConnection;
import dao.utilities.SQLStatements;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

/**
 *
 * @author Laura
 */
public class PurchasesDao {

    public List<Purchase> getAll() {
        ArrayList<Purchase> purchases = new ArrayList();
        DBConnection db = new DBConnection();

        ItemsDao dao = new ItemsDao();
        CustomersDao cdao = new CustomersDao();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = db.getConnection();
            stmt = con.prepareStatement(SQLStatements.SELECT_all_purchases);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idPurchase = rs.getInt("idPurchase");
                String date = rs.getString("date");
                int idCustomer = rs.getInt("idCustomer");
                int idItem = rs.getInt("idItem");

                Item purchaseItem = dao.get(idItem);
                Customer purchaseCustomer = cdao.get(idCustomer);

                purchases.add(new Purchase(idPurchase, purchaseCustomer, purchaseItem, date));
            }

        } catch (IOException | SQLException ex) {
            Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PurchasesDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeResultSet(rs);
            db.closeStatement(stmt);
            db.closeConexion(con);
        }

        return purchases;
    }

    public List<Purchase> get(String purchaseDate) {
        ArrayList<Purchase> purchases = new ArrayList();

        ItemsDao dao = new ItemsDao();
        CustomersDao cdao = new CustomersDao();

        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            con = db.getConnection();
            stmt = con.prepareStatement(SQLStatements.SELECT_purchase_ByDate);
            stmt.setString(1, purchaseDate);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idPurchase = rs.getInt("idPurchase");
                String date = rs.getString("date");
                int idCustomer = rs.getInt("idCustomer");
                int idItem = rs.getInt("idItem");
                Item purchaseItem = dao.get(idItem);
                Customer purchaseCustomer = cdao.get(idCustomer);

                purchases.add(new Purchase(idPurchase, purchaseCustomer, purchaseItem, date));
            }

        } catch (IOException | SQLException ex) {
            Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PurchasesDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeResultSet(rs);
            db.closeStatement(stmt);
            db.closeConexion(con);
        }

        return purchases;
    }

    public Purchase getById(int purchaseId) {
        Purchase purchase = null;

        ItemsDao dao = new ItemsDao();
        CustomersDao cdao = new CustomersDao();

        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            con = db.getConnection();
            stmt = con.prepareStatement(SQLStatements.SELECT_purchase_ById);
            stmt.setInt(1, purchaseId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idPurchase = rs.getInt("idPurchase");
                String date = rs.getString("date");
                int idCustomer = rs.getInt("idCustomer");
                int idItem = rs.getInt("idItem");
                Item purchaseItem = dao.get(idItem);
                Customer purchaseCustomer = cdao.get(idCustomer);

                purchase = new Purchase(idPurchase, purchaseCustomer, purchaseItem, date);
            }
        } catch (IOException | SQLException ex) {
            Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PurchasesDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeResultSet(rs);
            db.closeStatement(stmt);
            db.closeConexion(con);
        }

        return purchase;
    }

    public List<Integer> getByItemId(int itemId) {
        ArrayList<Integer> purchases = new ArrayList();

        ItemsDao dao = new ItemsDao();
        CustomersDao cdao = new CustomersDao();

        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = db.getConnection();
            stmt = con.prepareStatement(SQLStatements.SELECT_purchases_ByItemId);
            stmt.setInt(1, itemId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idPurchase = rs.getInt("idPurchase");
                purchases.add(idPurchase);
            }

        } catch (IOException | SQLException ex) {
            Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PurchasesDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeResultSet(rs);
            db.closeStatement(stmt);
            db.closeConexion(con);
        }
        return purchases;
    }

    public int add(Purchase purchase) {
        int result = 0;

        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = db.getConnection();

            stmt = con.prepareStatement(SQLStatements.INSERT_purchase, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, purchase.getDate());
            stmt.setInt(2, purchase.getCustomer().getIdCustomer());
            stmt.setInt(3, purchase.getItem().getIdItem());

            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                purchase.setIdPurchase(rs.getInt(1));
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            result = 1;
        } catch (IOException | SQLException ex) {
            result = 2;
            Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PurchasesDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeResultSet(rs);
            db.closeStatement(stmt);
            db.closeConexion(con);
        }
        return result;
    }

    public int delete(Purchase purchase) {
        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement stmt = null;

        int result = 0;

        try {
            con = db.getConnection();

            stmt = con.prepareStatement(SQLStatements.DELETE_purchase);
            stmt.setInt(1, purchase.getIdPurchase());
            stmt.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException ex) {
            result = 1;

        } catch (SQLServerException ex) {
            System.out.println("ERROR CONSTRAINT: " + ex.getErrorCode());
            if (ex.getErrorCode() == 547) {
                result = 1;
            }

        } catch (IOException | SQLException ex) {
            result = 2;
            Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(PurchasesDao.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(PurchasesDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeStatement(stmt);
            db.closeConexion(con);
        }

        return result;
    }

    public void update(Purchase purchase) {
        //The purchase update was not needed.
    }

}
