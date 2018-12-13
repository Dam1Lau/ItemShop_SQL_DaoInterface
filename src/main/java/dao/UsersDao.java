/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.utilities.DBConnection;
import dao.utilities.SQLStatements;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author Laura
 */
public class UsersDao {
    
    public User get(String username) {
        User user = null;

        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = db.getConnection();
            stmt = con.prepareStatement(SQLStatements.SELECT_user_byName);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idUser = rs.getInt("idUser");
                String name = rs.getString("username");
                String pass = rs.getString("pass");
                int type = rs.getInt("type");
                int idCustomer = rs.getInt("idCustomer");

                user = new User(idUser, name, pass, type, idCustomer);
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

        return user;
    }
    
}
