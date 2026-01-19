package com.automation.pages;

import com.automation.config.ConfigReader;
import com.automation.utils.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BasePage class containing common methods for all page objects
 * Implements Page Object Model design pattern
 * All page classes will extend this base class
 *
 * @author Sid
 * @version 1.0
 */
public abstract class BasePage {

    // Step 1: Protected variables (accessible by child classes)
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected ConfigReader config;

    /**
     * Step 2: Constructor - initializes driver and wait
     * Called automatically when child page object is created
     */
    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.config = ConfigReader.getInstance();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(config.getExplicitWait()));
    }

    // ==================== WAIT METHODS ====================

    /**
     * Step 3: Wait for element to be clickable
     * @param locator Element locator (By.xpath, By.id, etc.)
     * @return WebElement that is clickable
     */
    protected WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Step 4: Wait for element to be visible
     * @param locator Element locator
     * @return WebElement that is visible
     */
    protected WebElement waitForElementToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Step 5: Wait for element to be present in DOM
     * @param locator Element locator
     * @return WebElement that is present
     */
    protected WebElement waitForElementToBePresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // ==================== ACTION METHODS ====================

    /**
     * Step 6: Click on element with wait (handles click interception)
     * @param locator Element locator
     */
    protected void click(By locator) {
        try {
            waitForElementToBeClickable(locator).click();
        } catch (ElementClickInterceptedException e) {
            // Retry with JavaScript click if normal click fails
            clickUsingJavaScript(locator);
        }
    }

    /**
     * Step 7: Click element using JavaScript (for stubborn elements)
     * @param locator Element locator
     */
    protected void clickUsingJavaScript(By locator) {
        WebElement element = driver.findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    /**
     * Step 8: Enter text into input field
     * @param locator Element locator
     * @param text Text to enter
     */
    protected void enterText(By locator, String text) {
        WebElement element = waitForElementToBeVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Step 9: Get text from element
     * @param locator Element locator
     * @return Element text
     */
    protected String getText(By locator) {
        return waitForElementToBeVisible(locator).getText();
    }

    /**
     * Step 10: Check if element is displayed
     * @param locator Element locator
     * @return true if displayed, false otherwise
     */
    protected boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // ==================== ALERT METHODS ====================

    /**
     * Step 11: Wait for alert to be present and return it
     * @return Alert object
     */
    protected Alert waitForAlert() {
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    /**
     * Step 12: Accept alert (click OK)
     */
    protected void acceptAlert() {
        waitForAlert().accept();
    }

    /**
     * Step 13: Dismiss alert (click Cancel)
     */
    protected void dismissAlert() {
        waitForAlert().dismiss();
    }

    /**
     * Step 14: Get alert text
     * @return Alert message
     */
    protected String getAlertText() {
        return waitForAlert().getText();
    }

    // ==================== SCROLL METHODS ====================

    /**
     * Step 15: Scroll to element
     * @param locator Element locator
     */
    protected void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // ==================== PAGE INFORMATION METHODS ====================

    /**
     * Step 16: Get page title
     * @return Current page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Step 17: Get current URL
     * @return Current page URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}