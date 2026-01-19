package com.automation.reports;

import com.automation.config.ConfigReader;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ExtentReportManager class to manage ExtentReports for test execution
 * Generates beautiful HTML reports with screenshots
 *
 * @author Sid
 * @version 1.0
 */
public class ExtentReportManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static ConfigReader config = ConfigReader.getInstance();

    /**
     * Initialize ExtentReports
     * This prevents multiple threads from initializing reports simultaneously
     */
    public synchronized static void initReports() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String reportPath = config.getExtentReportFolder() + "/TestReport_" + timestamp + ".html";

            // Create directory if not exists
            File reportDir = new File(config.getExtentReportFolder());
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            // Configure report
            sparkReporter.config().setDocumentTitle(config.getProperty("report.title", "Automation Report"));
            sparkReporter.config().setReportName(config.getProperty("report.name", "Test Results"));
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            // System information
            extent.setSystemInfo("Application", config.getProperty("app.name", "React Shopping Cart"));
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Browser", config.getBrowser());
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
        }
    }

    /**
     * Create a new test in the report
     */
    public static void createTest(String testName, String description) {
        ExtentTest extentTest = extent.createTest(testName, description);
        test.set(extentTest);
    }

    /**
     * Get current test instance
     */
    public static ExtentTest getTest() {
        return test.get();
    }

    /**
     * Log info message
     */
    public static void logInfo(String message) {
        getTest().log(Status.INFO, message);
    }

    /**
     * Log pass message
     */
    public static void logPass(String message) {
        getTest().log(Status.PASS, MarkupHelper.createLabel(message, ExtentColor.GREEN));
    }

    /**
     * Log fail message
     */
    public static void logFail(String message) {
        getTest().log(Status.FAIL, MarkupHelper.createLabel(message, ExtentColor.RED));
    }

    /**
     * Log skip message
     */
    public static void logSkip(String message) {
        getTest().log(Status.SKIP, MarkupHelper.createLabel(message, ExtentColor.YELLOW));
    }

    /**
     * Log warning message
     */
    public static void logWarning(String message) {
        getTest().log(Status.WARNING, MarkupHelper.createLabel(message, ExtentColor.ORANGE));
    }

    /**
     * Add screenshot to report
     */
    public static void addScreenshot(String screenshotPath) {
        try {
            getTest().addScreenCaptureFromPath(screenshotPath);
        } catch (Exception e) {
            getTest().log(Status.WARNING, "Failed to attach screenshot: " + e.getMessage());
        }
    }

    /**
     * Flush reports to file
     */

    public synchronized static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }

    /**
     * Remove test from ThreadLocal
     */
    public static void removeTest() {
        test.remove();
    }


}