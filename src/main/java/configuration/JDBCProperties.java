/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Laura
 */
public class JDBCProperties {
    public String driver;
    public String userName;
    public String password;
    public String urlDB;

    private String dbms;
    private Properties prop;

    private static final String PROPERTIES_FILE= ConfigProperties.getInstance().getProperty("properties");

    public JDBCProperties() {
            super();
            this.setProperties(PROPERTIES_FILE);
    }

    /**
     * Assigns XML property values to class attributes
     */
    private void setProperties(String fileName) {
            prop = new Properties();

        try {
            prop.loadFromXML(Files.newInputStream(Paths.get(fileName)));
        } catch (IOException ex) {
            Logger.getLogger(JDBCProperties.class.getName()).log(Level.SEVERE, null, ex);
        }

            this.dbms = this.prop.getProperty("dbms");
            switch (this.dbms) {
                case "mysql":

                    this.driver = this.prop.getProperty("driverMySQL");
                    this.userName = this.prop.getProperty("user_nameMySQL");
                    this.password = this.prop.getProperty("passwordMySQL");
                    this.urlDB = this.prop.getProperty("urlDBMySQL");
                    break;

                case "sqlserver":

                    this.driver = this.prop.getProperty("driverSQLServer");
                    this.userName = this.prop.getProperty("user_nameSQLServer");
                    this.password = this.prop.getProperty("passwordSQLServer");
                    this.urlDB = this.prop.getProperty("urlDBSQLServer");
                    break;

                default:
                    this.driver = null;
                    this.userName = null;
                    this.password = null;
                    this.urlDB = null;
                    break;
            }        

    }

    public String getDriver() {
        return driver;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUrlDB() {
        return urlDB;
}
}
