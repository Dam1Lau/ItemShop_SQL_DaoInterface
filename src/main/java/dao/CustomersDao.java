/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import configuration.ConfigProperties;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;
import model.Review;
import model.converters.DateConverter;
import dao.utilities.DBConnection;
import dao.utilities.SQLStatements;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.List;
import model.User;

/**
 *
 * @author Laura
 */
public class CustomersDao {

//<editor-fold defaultstate="collapsed" desc="SAVE AND LOAD FROM XML">
    public ArrayList<Customer> loadCustomers() {

        ArrayList<Customer> customers = new ArrayList();
        Path file = Paths.get(ConfigProperties.getInstance().getProperty("customers"));
        Charset charset = Charset.forName("UTF-8");
        BufferedReader reader = null;

        XStream xs = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xs);
        xs.allowTypesByWildcard(new String[]{"model.*"});

        xs.alias("customer", Customer.class);
        xs.alias("review", Review.class);

        xs.aliasField("id", Customer.class, "idCustomer");
        xs.aliasField("item", Review.class, "itemId");
        xs.addImplicitCollection(Customer.class, "reviews");

        xs.useAttributeFor(Customer.class, "idCustomer");
        xs.useAttributeFor(Review.class, "itemId");

        xs.registerConverter(new DateConverter());

        try {
            reader = Files.newBufferedReader(file, charset);
            customers = (ArrayList<Customer>) xs.fromXML(reader);

        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return customers;
    }

    public void saveCustomers(ArrayList<Customer> customers) {
        Path file = Paths.get(ConfigProperties.getInstance().getProperty("customers"));
        Charset charset = Charset.forName("UTF-8");

        XStream xs = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xs);
        xs.allowTypesByWildcard(new String[]{"model.*"});

        xs.alias("customer", Customer.class);
        xs.alias("review", Review.class);

        xs.aliasField("id", Customer.class, "idCustomer");
        xs.aliasField("item", Review.class, "itemId");
        xs.addImplicitCollection(Customer.class, "reviews");

        xs.useAttributeFor(Customer.class, "idCustomer");
        xs.useAttributeFor(Review.class, "itemId");

        xs.registerConverter(new DateConverter());

        String xml = xs.toXML(customers);

        //Creates a BufferedWriter (java.io) in a more efficient way using Files from java.nio
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
            writer.write(xml);
            writer.close();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
//</editor-fold>

    // Database Load, Delete, Add, Update.
    public List<Customer> getAll() {
        ArrayList<Customer> customers = new ArrayList();

        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            con = db.getConnection();
            stmt = con.prepareStatement(SQLStatements.SELECT_all_customers);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idCustomer = rs.getInt("idCustomer");
                String name = rs.getString("name");
                String phone = rs.getString("telephone");
                String address = rs.getString("address");

                customers.add(new Customer(idCustomer, name, phone, address));
            }

        } catch (IOException | SQLException ex) {
            Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                db.closeResultSet(rs);
            }
            if (stmt != null) {
                db.closeStatement(stmt);
            }
            if (con != null) {
                db.closeConexion(con);
            }
        }

        return customers;
    }

    public Customer get(int id) {
        Customer customer = null;

        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = db.getConnection();
            stmt = con.prepareStatement(SQLStatements.SELECT_customer_ByID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idCustomer = rs.getInt("idCustomer");
                String name = rs.getString("name");
                String phone = rs.getString("telephone");
                String address = rs.getString("address");

                customer = new Customer(idCustomer, name, phone, address);
            }
        } catch (IOException | SQLException ex) {
            Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                db.closeResultSet(rs);
            }
            if (stmt != null) {
                db.closeStatement(stmt);
            }
            if (con != null) {
                db.closeConexion(con);
            }
        }

        return customer;
    }

    public int add(Customer customer, User user) {
        int result = 0;
        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = db.getConnection();
            con.setAutoCommit(false);
            //First create Customer
            //This gives back the new generated key for that new customer
            stmt = con.prepareStatement(SQLStatements.INSERT_customer, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getPhone());
            stmt.setString(3, customer.getAddress());
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();  //THIS RETRIEVES THE NEW ID FOR THE CUSTOMER

            // Create account for that client
            stmt = con.prepareStatement(SQLStatements.INSERT_user);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPass());
            stmt.setInt(3, user.getType());
            if (rs != null && rs.next()) { //This assigns the retrieved Id to this user
                int retrievedId = rs.getInt(1);
                stmt.setInt(4, retrievedId);
            }
            stmt.executeUpdate();
            con.commit();

        } catch (SQLIntegrityConstraintViolationException ex) {
            result = 1;
        } catch (SQLServerException ex) {
            System.out.println("ERROR REPEATED USERNAME: " + ex.getErrorCode());
            if (ex.getErrorCode() == 2601) {
                result = 1;
            } else {
                result = 2;
            }
        } catch (Exception ex) {
            result = 2;
            Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                db.closeResultSet(rs);
            }
            if (stmt != null) {
                db.closeStatement(stmt);
            }
            if (con != null) {
                try {
                    if (result != 0) {
                        con.rollback();
                    }
                    con.setAutoCommit(true);

                } catch (SQLException ex) {
                    Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    db.closeConexion(con);
                }
            }
        }
        return result;
    }

    public int delete(Customer customer) {
        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement stmt = null;

        int result = 0;

        try {
            con = db.getConnection();
            con.setAutoCommit(false);

            //DELETE ITS USER ACCOUNT
            stmt = con.prepareStatement(SQLStatements.DELETE_user_byCustomer);
            stmt.setInt(1, customer.getIdCustomer());
            stmt.executeUpdate();

            //DELETE USER
            stmt = con.prepareStatement(SQLStatements.DELETE_customer);
            stmt.setInt(1, customer.getIdCustomer());
            stmt.executeUpdate();

            con.commit();

        } catch (SQLIntegrityConstraintViolationException ex) {
            result = 1;
        } catch (SQLServerException ex) {
            if (ex.getErrorCode() == 547) {
                result = 1;
            } else {
                result = 2;
            }
        } catch (Exception ex) {
            result = 2;
            Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                db.closeStatement(stmt);
            }
            if (con != null) {
                try {
                    if (result != 0) {
                        con.rollback();
                    }
                    con.setAutoCommit(true);
                } catch (SQLException ex) {
                    Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    db.closeConexion(con);
                }
            }
        }
        return result;
    }

    public int deleteFullCustomer(Customer customer) {
        int result = 0;
        Connection con = null;
        PreparedStatement stmt = null;
        DBConnection db = new DBConnection();

        try {
            con = db.getConnection();
            con.setAutoCommit(false);

            //DELETE REVIEWS OF THAT CUSTOMER FIRST
            stmt = con.prepareStatement(SQLStatements.DELETE_reviews_byCustomer);
            stmt.setInt(1, customer.getIdCustomer());
            stmt.executeUpdate();

            //DELETE PURCHASES OF THAT CUSTOMER SECOND
            stmt = con.prepareStatement(SQLStatements.DELETE_purchases_byCustomer);
            stmt.setInt(1, customer.getIdCustomer());
            stmt.executeUpdate();

            //DELETE THE USER ACCOUNT FOR THAT CUSTOMER
            stmt = con.prepareStatement(SQLStatements.DELETE_user_byCustomer);
            stmt.setInt(1, customer.getIdCustomer());
            stmt.executeUpdate();

            //FINALLY DELETE THE CUSTOMER
            stmt = con.prepareStatement(SQLStatements.DELETE_customer);
            stmt.setInt(1, customer.getIdCustomer());
            stmt.executeUpdate();
            //COMMIT ALL THE ACTIONS
            con.commit();

        } catch (SQLIntegrityConstraintViolationException ex) {
            result = 1;
        } catch (SQLServerException ex) {
            System.out.println("ERROR CONSTRAINT: " + ex.getErrorCode());
            if (ex.getErrorCode() == 547) {
                result = 1;
            }
        } catch (Exception ex) {
            result = 2;
            Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                db.closeStatement(stmt);
            }
            try {
                if (con != null) {
                    if (result != 0) {
                        con.rollback();
                    }
                    con.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                db.closeConexion(con);
            }
        }
        return result;
    }

    public int update(Customer customer) {

        int result = 0;
        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = db.getConnection();

            stmt = con.prepareStatement(SQLStatements.UPDATE_customer);
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getPhone());
            stmt.setString(3, customer.getAddress());
            stmt.setInt(4, customer.getIdCustomer());
            stmt.executeUpdate();

        } catch (Exception ex) {
            result = 2;
            Logger.getLogger(CustomersDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                db.closeStatement(stmt);
            }
            if (con != null) {
                db.closeConexion(con);
            }
        }
        return result;
    }
}
