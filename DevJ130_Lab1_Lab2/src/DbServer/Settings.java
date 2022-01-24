
package DbServer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Settings {
    Properties properties;
    
    public final static String URL = "url";
    public final static String USER = "user";
    public final static String PSW = "psw";

    public Settings() {
        properties = new Properties();
        File file = new File("settings.prop");
        try {
            if (!file.exists())            
                file.createNewFile();
                properties.load(new FileReader(file));
            } catch (IOException ex) {} 
    }
    
    public String getValue(String key){
       String value = properties.getProperty(key);
       return value;
    }
    
//    public String getUrl(){
//       String url = properties.getProperty("url");
//       return url;
//    }
//    
//    public String getUser(){
//       String user = properties.getProperty("user");
//       return user;
//    }
//    
//    public String getPsw(){
//       String psw = properties.getProperty("psw");
//       return psw;
//    }
 
}
