package service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper {
    private static final String PROPERTIES_FILE_LOCATION = "src/test/resources/application.properties";

    public static String getPropertyValue(String key) {
        try {
            Properties properties = new Properties();

            FileReader fileReader = new FileReader(PROPERTIES_FILE_LOCATION);

            properties.load(fileReader);

            fileReader.close();

            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
