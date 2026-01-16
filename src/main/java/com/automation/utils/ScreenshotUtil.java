package com.automation.utils;

import com.automation.config.ConfigReader;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ScreenshotUtil class to capture and save screenshots
 *
 * @author Your Name
 * @version 1.0
 */
public class ScreenshotUtil {

    private static final String SCREENSHOT_FOLDER = "test-output/screenshots";
    private static ConfigReader config = ConfigReader.getInstance();

    /**
     * Capture screenshot
     * @param driver WebDriver instance
     * @param testName Test name for screenshot file
     * @return Screenshot file path
     */
    public static String captureScreenshot(WebDriver driver, String testName) {
        try {
            // Create screenshots directory if not exists
            File screenshotDir = new File(SCREENSHOT_FOLDER);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            // Generate unique filename
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = testName.replaceAll("[^a-zA-Z0-9]", "_") + "_" + timestamp + ".png";
            String filePath = SCREENSHOT_FOLDER + "/" + fileName;

            // Take screenshot
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            File destination = new File(filePath);

            FileHandler.copy(source, destination);

            return new File(filePath).getAbsolutePath();

        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }

    /**
     * Capture screenshot with custom file name
     */
    public static String captureScreenshotWithCustomName(WebDriver driver, String fileName) {
        try {
            File screenshotDir = new File(SCREENSHOT_FOLDER);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            String filePath = SCREENSHOT_FOLDER + "/" + fileName + ".png";

            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            File destination = new File(filePath);

            FileHandler.copy(source, destination);

            return new File(filePath).getAbsolutePath();

        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
}