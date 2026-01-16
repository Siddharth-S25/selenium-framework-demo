package com.automation.utils;

import com.automation.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * DriverManager class to manage WebDriver instances
 * Uses ThreadLocal for parallel execution support
 * Implements Factory pattern for browser creation
 *
 * @author Your Name
 * @version 1.0
 */
public class DriverManager {

    // Step 1: ThreadLocal to store WebDriver for each thread
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Step 2: Get ConfigReader instance
    private static ConfigReader config = ConfigReader.getInstance();

    /**
     * Step 3: Get WebDriver instance (creates if doesn't exist)
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            String browser = config.getBrowser().toLowerCase();

            // Factory Pattern - create browser based on type
            switch (browser) {
                case "chrome":
                    driver.set(createChromeDriver());
                    break;
                case "firefox":
                    driver.set(createFirefoxDriver());
                    break;
                case "edge":
                    driver.set(createEdgeDriver());
                    break;
                default:
                    throw new IllegalArgumentException("Browser type not supported: " + browser);
            }

            configureDriver();
        }
        return driver.get();
    }

    /**
     * Step 4: Create Chrome WebDriver with options
     * @return ChromeDriver instance
     */
    private static WebDriver createChromeDriver() {
        // WebDriverManager automatically downloads ChromeDriver
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // If headless mode is enabled in config
        if (config.isHeadless()) {
            options.addArguments("--headless=new");
        }

        // Chrome arguments for stability
        options.addArguments(
                "--disable-notifications",
                "--disable-popup-blocking",
                "--remote-allow-origins=*",
                "--disable-dev-shm-usage",
                "--no-sandbox",
                "--disable-extensions",
                "--disable-gpu",
                "--disable-infobars"
        );

        return new ChromeDriver(options);
    }

    /**
     * Step 5: Create Firefox WebDriver with options
     * @return FirefoxDriver instance
     */
    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();

        if (config.isHeadless()) {
            options.addArguments("--headless");
        }

        return new FirefoxDriver(options);
    }

    /**
     * Step 6: Create Edge WebDriver with options
     * @return EdgeDriver instance
     */
    private static WebDriver createEdgeDriver() {
        WebDriverManager.edgedriver().setup();

        EdgeOptions options = new EdgeOptions();

        if (config.isHeadless()) {
            options.addArguments("--headless");
        }

        return new EdgeDriver(options);
    }

    /**
     * Step 7: Configure WebDriver with timeouts and window size
     */
    private static void configureDriver() {
        WebDriver webDriver = driver.get();

        // Set timeouts from config
        webDriver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(config.getImplicitWait())
        );
        webDriver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(config.getPageLoadTimeout())
        );

        // Maximize window if configured
        if (config.shouldMaximize()) {
            webDriver.manage().window().maximize();
        }
    }

    /**
     * Step 8: Quit the WebDriver and remove from ThreadLocal
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}