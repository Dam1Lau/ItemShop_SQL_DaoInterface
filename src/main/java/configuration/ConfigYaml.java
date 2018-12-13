/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Laura
 */
public class ConfigYaml {
    
    private String User;
    private String Pass;
    
    private String urlDB;
    private String driverDB;
    private String userDB;
    private String passDB;
    
    private static ConfigYaml config;

    private ConfigYaml() {
        
    }
    
    public static ConfigYaml getInstance() {
        if (config == null) {
            try {
                Yaml yaml = new Yaml();
                InputStream in = Files.newInputStream(Paths.get("propertiesFiles/users.yml"));
                // Parse the YAML document in a stream and produce the corresponding Java object.
                config = (ConfigYaml) yaml.loadAs(in, ConfigYaml.class);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(ConfigYaml.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ConfigYaml.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return config;
    }


    public String getUser() {
        return User;
    }

    public void setUser(String User) {
        this.User = User;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String Pass) {
        this.Pass = Pass;
    }

    public String getUrlDB() {
        return urlDB;
    }

    public void setUrlDB(String urlDB) {
        this.urlDB = urlDB;
    }

    public String getDriverDB() {
        return driverDB;
    }

    public void setDriverDB(String driverDB) {
        this.driverDB = driverDB;
    }

    public String getUserDB() {
        return userDB;
    }

    public void setUserDB(String userDB) {
        this.userDB = userDB;
    }

    public String getPassDB() {
        return passDB;
    }

    public void setPassDB(String passDB) {
        this.passDB = passDB;
    }
    
    

    
}
