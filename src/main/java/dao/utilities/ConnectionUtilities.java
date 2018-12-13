/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.utilities;

import configuration.ConfigProperties;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 *
 * @author Laura
 */
public class ConnectionUtilities {

    public String dbms;
    public String dbName;
    public String userName;
    public String password;
    public String urlString;

    private String driver;
    private String serverName;
    private int portNumber;
    private Properties prop;

    public ConnectionUtilities()
            throws FileNotFoundException, IOException,
            InvalidPropertiesFormatException {
        super();
        this.setProperties(ConfigProperties.getInstance().getProperty("sqlProperties"));
    }

    private void setProperties(String fileName) throws IOException, InvalidPropertiesFormatException {
        Path p1 = Paths.get(fileName);
        this.prop = new Properties();
        InputStream propertiesStream = null;
        try {
            propertiesStream = Files.newInputStream(p1);
            prop.loadFromXML(propertiesStream);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.dbms = this.prop.getProperty("dbms");
        this.driver = this.prop.getProperty("driver");
        this.dbName = this.prop.getProperty("database_name");
        this.userName = this.prop.getProperty("user_name");
        this.password = this.prop.getProperty("password");
        this.serverName = this.prop.getProperty("server_name");
        this.portNumber = Integer
                .parseInt(this.prop.getProperty("port_number"));

    }

    public Connection getConnection() throws SQLException {

        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);

        if (this.dbms.equals("mysql")) {
            /* Requests a database connection to DriverManager */

            conn = DriverManager.getConnection("jdbc:" + this.dbms + "://"
                    + this.serverName + ":" + this.portNumber + "/" + this.dbName,
                    connectionProps);
        } else if (this.dbms.equals("derby")) {
            conn = DriverManager.getConnection("jdbc:" + this.dbms + ":"
                    + this.dbName + ";create=true", connectionProps);
        }
        System.out.println("Connected to DB");
        return conn;
    }
    
    public static void closeConnection(java.sql.Connection connArg) {
        System.out.println("Releasing all open resources ...");
        try {
            if (connArg != null) {
                connArg.close();
                connArg = null;
            }
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }
    
    public static void printSQLException(SQLException e) {

        while (e != null) {
            if (e instanceof SQLException) {
                //ANSI SQLState
                e.printStackTrace(System.err);
                System.err.println("SQLState: "
                        + ((SQLException) e).getSQLState());

                //Error code from the DBMS
                System.err.println("Error Code: "
                        + ((SQLException) e).getErrorCode());

                //Text message
                System.err.println("Message: " + e.getMessage());

                //Objects that trigger the exception
                Throwable t = e.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
                //Any other exceptions caused by this one
                e = e.getNextException();

            }
        }
    }

}
