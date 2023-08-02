package utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties = new Properties();

    static{
        try {
            FileInputStream fileInputStream = new FileInputStream("configuration.properties");
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getProperty(String key){
        return properties.getProperty(key);
    }


}
