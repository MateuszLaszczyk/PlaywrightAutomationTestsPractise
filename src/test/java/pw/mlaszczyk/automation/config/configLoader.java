package pw.mlaszczyk.automation.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class configLoader {
    private static final String CONFIG_PATH = "src/test/resources/config.properties";
    private static final Properties properties = new Properties();
    private static boolean isFileLoaded = false;

    /**
     * Returns the configuration value for the given key.
     * Priority:
     *   1. Environment Variable
     *   2. config.properties (if present)
     * Throws RuntimeException if the value is missing.
     */
    public static String get(String key) {
        // 1. Try ENV variable first (Jenkins-friendly)
        String envValue = System.getenv(key);
        if (envValue != null && !envValue.isEmpty()) {
            return envValue;
        }

        // 2. Fallback to config.properties
        if (!isFileLoaded) {
            loadPropertiesFromFile();
        }

        String propValue = properties.getProperty(key);
        if (propValue == null || propValue.isEmpty()) {
            throw new RuntimeException("Missing config value for key: " + key);
        }

        return propValue;
    }

    private static void loadPropertiesFromFile() {
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            properties.load(fis);
            isFileLoaded = true;
        } catch (IOException e) {
            throw new RuntimeException("Unable to load configuration from: " + CONFIG_PATH, e);
        }
    }
}
