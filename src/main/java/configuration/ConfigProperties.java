/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 *
 * @author Laura
 */
public class ConfigProperties {
    
    private static ConfigProperties instance=null;
    private Properties textProperties;

    private ConfigProperties() {
        Path p1 = Paths.get("propertiesFiles/paths.properties");
        textProperties = new Properties();
        InputStream propertiesStream=null;
        try {
            propertiesStream = Files.newInputStream(p1);
            textProperties.load(propertiesStream);
        } catch (IOException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
// Singleton, only creates one instance of this class
    public static ConfigProperties getInstance() {

        if (instance==null) {
            instance=new ConfigProperties();
        }
        return instance;
    }

    public String getProperty(String key) {
        return textProperties.getProperty(key);
    }
}
