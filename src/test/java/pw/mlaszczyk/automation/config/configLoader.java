package pw.mlaszczyk.automation.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class for loading configuration properties from an external file.
 * <p>
 * This class reads a properties file (typically `config.properties`) located in the
 * test resources directory and provides static access to its key-value pairs.
 *
 * Usage example:
 * <pre>
 *     String username = ConfigLoader.get("saucedemo.username");
 *     String password = ConfigLoader.get("saucedemo.password");
 *     String baseURL = ConfigLoader.get("saucedemo.baseUrl");
 *     String baseURL = ConfigLoader.get("saucedemo.mainPageUrl");
 * </pre>
 */
public class configLoader {
    private static final String CONFIG_PATH = "src/test/resources/config.properties";
    private static Properties properties;

    // Static initializer block to load properties only once
    static {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load configuration from: " + CONFIG_PATH, e);
        }
    }

    /**
     * Retrieves a property value from the configuration file by key.
     *
     * @param key the name of the property to retrieve
     * @return the value associated with the given key, or null if the key doesn't exist
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }
}
