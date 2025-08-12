package pw.mlaszczyk.automation.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private static final String CONFIG_PATH = "src/test/resources/config.properties";
    private static final String LOCAL_SECRETS_PATH = "config/secrets.local.properties";
    private static final Properties properties = new Properties();
    private static boolean isLoaded = false;

    public static String get(String key) {
        if (!isLoaded) loadAll();

        // 1) ENV: saucedemo.username -> SAUCEDEMO_USERNAME
        String envKey = key.toUpperCase().replace('.', '_');
        String envValue = System.getenv(envKey);
        if (envValue != null && !envValue.isEmpty()) return envValue;

        // 2) Files
        String val = properties.getProperty(key);
        if (val == null || val.isEmpty()) {
            throw new RuntimeException("Missing config value for key: " + key);
        }
        return val;
    }

    private static void loadAll() {
        // basic config
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load " + CONFIG_PATH, e);
        }

        // local secrets
        File local = new File(LOCAL_SECRETS_PATH);
        if (local.exists()) {
            try (FileInputStream fis = new FileInputStream(local)) {
                Properties sec = new Properties();
                sec.load(fis);
                sec.forEach((k, v) -> properties.put(k, v)); // override
            } catch (IOException ignored) {}
        }
        isLoaded = true;
    }
}
