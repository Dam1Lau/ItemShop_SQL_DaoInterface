/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 *
 * @author dam2
 */
public class DAOFactory {

    private static DAOFactory instance;
    private String sourceCoffee;
    private Properties daoProps;

    private static final String PROPERTIES_FILE = "propertiesFiles\\dao-properties.xml";

    public DAOFactory() {
        try {
            setDAOType(PROPERTIES_FILE);
        } catch (Exception e) {
        }
    }

    private void setDAOType(String configFile) throws IOException, InvalidPropertiesFormatException {
        daoProps = new Properties();
        daoProps.loadFromXML(Files.newInputStream(Paths.get(configFile)));
    }

    public DAOCustomer getDAOCustomer() {
        DAOCustomer dao = null;
        sourceCoffee = daoProps.getProperty("daocustomer");
        //Un if por cada tipo de DAO posible a instanciar
        if (sourceCoffee.equals("DAOJDBCCustomer")) {
           // dao = new DAOJDBCCustomer();    //PONER ESTE BIEEEEEN
        } else if (sourceCoffee.equals("DAOXMLCoffee")) {
            // dao= new DAOXMLCoffee();
        } else if (sourceCoffee.equals("DAODBUtilsCoffee")) {
            // dao= new DAODBUtilsCoffee();
        }
        //... 

        return dao;
    }
}
