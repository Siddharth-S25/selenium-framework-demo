package com.automation.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigReader class to read configuration properties from config.properties file
 * Implements Singleton pattern to ensure only one instance exists
 *
 * @author Your Name
 * @version 1.0
 */
public class ConfigReader {

    // Step 1: Private static instance variable
    private static ConfigReader instance;

    // Step 2: Properties object to store config values
    private Properties properties;

    // Step 3: Path to config file
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";

    /**
     * Step 4: Private constructor (prevents external instantiation)
     * This is key to Singleton pattern
     */
    private ConfigReader() {
        properties = new Properties();
        loadProperties();
    }

    /**
     * Step 5: Public static method to get instance (Singleton)
     * This is the ONLY way to get ConfigReader object
     */
    public static ConfigReader getInstance() {
        if (instance == null) {
            synchronized (ConfigReader.class) {
                if (instance == null) {
                    instance = new ConfigReader();
                }
            }
        }
        return instance;
    }

    /**
     * Step 6: Load properties from config file
     */
    private void loadProperties() {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config file: " + CONFIG_FILE_PATH, e);
        }
    }

    /**
     * Step 7: Get property value by key
     * @param key Property key from config.properties
     * @return Property value
     */
    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config file");
        }
        return value;
    }

    /**
     * Step 8: Get property with default value (if key not found)
     * @param key Property key
     * @param defaultValue Default value if key not found
     * @return Property value or default value
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    // Step 9: Convenience methods for common configs

    public String getApplicationUrl() {
        return getProperty("app.url");
    }

    public String getBrowser() {
        return getProperty("browser");
    }

    public boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }

    public boolean shouldMaximize() {
        return Boolean.parseBoolean(getProperty("maximize", "true"));
    }

    public int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }

    public int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "20"));
    }

    public int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("page.load.timeout", "30"));
    }

    public boolean shouldTakeScreenshotOnFailure() {
        return Boolean.parseBoolean(getProperty("screenshot.on.failure", "true"));
    }

    public String getExtentReportFolder() {
        return getProperty("extent.report.folder");
    }

    public int getRetryCount() {
        return Integer.parseInt(getProperty("retry.count", "1"));
    }
}