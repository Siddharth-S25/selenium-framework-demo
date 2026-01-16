package com.automation.tests;

import com.automation.config.ConfigReader;
import com.automation.reports.ExtentReportManager;
import com.automation.utils.DriverManager;
import com.automation.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

/**
 * BaseTest class containing setup and teardown methods for all test classes
 * Manages test lifecycle, reporting, and screenshots
 *
 * @author Your Name
 * @version 1.0
 */
public class BaseTest {

    protected WebDriver driver;
    protected ConfigReader config;

    /**
     * Suite level setup - runs once before all tests
     */
    @BeforeSuite
    public void suiteSetup() {
        System.out.println("========================================");
        System.out.println("Starting Test Execution");
        System.out.println("========================================");
        ExtentReportManager.initReports();
    }

    /**
     * Test level setup - runs before each test method
     */
    @BeforeMethod
    public void setUp(ITestResult result) {
        config = ConfigReader.getInstance();

        // Create test in report
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        ExtentReportManager.createTest(testName, description);

        ExtentReportManager.logInfo("Initializing WebDriver: " + config.getBrowser());
        driver = DriverManager.getDriver();

        ExtentReportManager.logInfo("Navigating to application URL: " + config.getApplicationUrl());
        driver.get(config.getApplicationUrl());
    }

    /**
     * Test level teardown - runs after each test method
     */
    @AfterMethod
    public void tearDown(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        // Handle test result
        if (result.getStatus() == ITestResult.FAILURE) {
            ExtentReportManager.logFail("Test Failed: " + result.getThrowable().getMessage());

            // Capture screenshot on failure
            if (config.shouldTakeScreenshotOnFailure()) {
                String screenshotPath = ScreenshotUtil.captureScreenshot(driver, testName);
                if (screenshotPath != null) {
                    ExtentReportManager.addScreenshot(screenshotPath);
                    ExtentReportManager.logInfo("Screenshot captured at: " + screenshotPath);
                }
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            ExtentReportManager.logPass("Test Passed Successfully");
        } else if (result.getStatus() == ITestResult.SKIP) {
            ExtentReportManager.logSkip("Test Skipped: " + result.getThrowable().getMessage());
        }

        // Close browser
        ExtentReportManager.logInfo("Closing browser");
        DriverManager.quitDriver();

        // Remove test from ThreadLocal
        ExtentReportManager.removeTest();
    }

    /**
     * Suite level teardown - runs once after all tests
     */
    @AfterSuite
    public void suiteTeardown() {
        ExtentReportManager.flushReports();
        System.out.println("========================================");
        System.out.println("Test Execution Completed");
        System.out.println("Reports generated successfully");
        System.out.println("========================================");
    }
}