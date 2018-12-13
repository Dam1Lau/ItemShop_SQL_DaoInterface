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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Item;
import dao.utilities.ConnectionUtilities;
import dao.utilities.DBConnection;
import dao.utilities.DBConnectionPool;
import dao.utilities.SQLStatements;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Laura
 */
public class ItemsDao {

    public List<Item> getAll() {
        ArrayList<Item> items = new ArrayList();
        DBConnection db = new DBConnection();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = db.getConnection();                                           
            stmt = con.prepareStatement(SQLStatements.SELECT_all_items);        
            rs = stmt.executeQuery();                                           

            while (rs.next()) {
                int idItem = rs.getInt("idItem");
                String name = rs.getString("name");
                String company = rs.getString("company");
                double price = rs.getDouble("price");

                items.add(new Item(idItem, name, company, price));
            }

        } catch (IOException | SQLException ex) {
            Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ItemsDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeResultSet(rs);
            db.closeStatement(stmt);
            db.closeConexion(con);
        }

        return items;
    }

    public Item get(int id) {
        Item item = null;

        DBConnection db = new DBConnection();
        Connection con = null;

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = db.getConnection();
            stmt = con.prepareStatement(SQLStatements.SELECT_item_ByID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idItem = rs.getInt("idItem");
                String name = rs.getString("name");
                String company = rs.getString("company");
                double price = rs.getDouble("price");

                item = new Item(idItem, name, company, price);
            }
        } catch (IOException | SQLException ex) {
            Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ItemsDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeResultSet(rs);
            db.closeStatement(stmt);
            db.closeConexion(con);

        }

        return item;
    }

    public int add(Item item) {
        int result = 0;
        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = db.getConnection();

            stmt = con.prepareStatement(SQLStatements.INSERT_item, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getCompany());
            stmt.setDouble(3, item.getPrice());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();  //THIS RETRIEVES THE NEW ID FOR THE Item
            if (rs != null && rs.next()) {           //This assigns the retrieved Id to this item if needed.
                item.setIdItem(rs.getInt(1));
            }

        } catch (SQLIntegrityConstraintViolationException ex) {
            result = 1;
        } catch (IOException | SQLException ex) {
            result = 2;
            Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ItemsDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeStatement(stmt);
            db.closeConexion(con);
        }
        return result;
    }

    public int delete(Item item) {
        int result = 0;
        Connection con = null;
        PreparedStatement stmt = null;
        DBConnection db = new DBConnection();

        try {
            con = db.getConnection();

            stmt = con.prepareStatement(SQLStatements.DELETE_item);
            stmt.setInt(1, item.getIdItem());
            stmt.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException ex) {
            result = 1;

        } catch (SQLServerException ex) {
            //System.out.println("ERROR CODE CONSTRAINT EXCEPTION:" + ex.getErrorCode());
            if (ex.getErrorCode()== 547) {
                result = 1;
            }
        } catch (IOException | SQLException ex) {
            result = 2;
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(ItemsDao.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (Exception ex) {
            Logger.getLogger(ItemsDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeStatement(stmt);
            db.closeConexion(con);
        }
        return result;
    }

    public int update(Item item) {
        int result = 0;
        Connection con = null;
        PreparedStatement stmt = null;
        DBConnection db = new DBConnection();

        try {
            con = db.getConnection();

            stmt = con.prepareStatement(SQLStatements.UPDATE_item);
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getCompany());
            stmt.setDouble(3, item.getPrice());
            stmt.setInt(4, item.getIdItem());
            stmt.executeUpdate();

        } catch (Exception ex) {
            result = 2;
            Logger.getLogger(ItemsDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeStatement(stmt);
            db.closeConexion(con);
        }
        return result;
    }

}
