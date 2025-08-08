package pw.mlaszczyk.automation.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class configLoader {
    private static final String CONFIG_PATH = "src/test/resources/config.properties";
    private static Properties properties = new Properties();
    private static boolean isFileLoaded = false;

    public static String get(String key) {
        // 1. First try to get from EVN
        String envValue = System.getenv(key);
        if (envValue != null && !envValue.isEmpty()) {
            return envValue;
        }

        // 2. If env doesn't exist, use local properties
        if (!isFileLoaded) {
            try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
                properties.load(fis);
                isFileLoaded = true;
            } catch (IOException e) {
                throw new RuntimeException("Unable to load configuration from: " + CONFIG_PATH, e);
            }
        }

        return properties.getProperty(key);
    }
}
