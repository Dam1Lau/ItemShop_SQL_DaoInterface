/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.utilities;


import configuration.JDBCProperties;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;


/**
 *
 * @author Laura
 */
public class DBConnectionPool {
        private static DBConnectionPool dbconection = null;

 
        public static DBConnectionPool dbConnectionPool=null;
        public BasicDataSource pool=null;
	
	private DBConnectionPool()
			throws FileNotFoundException, IOException,
			InvalidPropertiesFormatException {
		super();		
                pool=this.getPool();
	}

        public static DBConnectionPool getInstance() throws IOException {
        if (dbConnectionPool == null) {
            dbConnectionPool = new DBConnectionPool();
        }

        return dbConnectionPool;
    }
	
	/**
	 * Creates connection dbConnectionPool
	 */
	private BasicDataSource getPool() {
		
                JDBCProperties properties=new JDBCProperties();
                BasicDataSource basicDataSource = new BasicDataSource();
		
            try {
                Class.forName(properties.getDriver());
                basicDataSource.setDriverClassName(properties.getDriver());
		basicDataSource.setUsername(properties.getUserName());
		basicDataSource.setPassword(properties.getPassword());
		basicDataSource.setUrl(properties.getUrlDB());		
		// Sets the size of the dbConnectionPool. Optional, default value is 10
		basicDataSource.setInitialSize(4);

		// Optional. For validating connection
		basicDataSource.setValidationQuery("select 1");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DBConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
            }
		
		
		System.out.println("Pool created");
		return basicDataSource;
	}
	

	/**
	 * Closes dbConnectionPool
	 */
	public static void closePool(BasicDataSource dbConnectionPool) throws SQLException {
		System.out.println("Releasing all open resources ...");
                if (dbConnectionPool != null) {
                    dbConnectionPool.close();
                    dbConnectionPool = null;
                }
	}
	
        public Connection getConnection() throws Exception {
              return pool.getConnection();
    }
	/**
	 * Prints all SQLException info to easily debug errors
	 */
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
