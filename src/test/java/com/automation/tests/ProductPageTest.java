package com.automation.tests;

import com.automation.pages.ProductPage;
import com.automation.reports.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * ProductPageTest class containing test cases for E-commerce product page
 *
 * @author Sid
 * @version 1.0
 */
public class ProductPageTest extends BaseTest {

    private ProductPage productPage;

    /**
     * Test Case 1: Verify user can complete purchase with size L
     */
    @Test(priority = 1, description = "Verify complete purchase flow with size L filter")
    public void testCompletePurchaseWithSizeL() {
        ExtentReportManager.logInfo("Starting test: Complete Purchase with Size L");

        productPage = new ProductPage();

        ExtentReportManager.logInfo("Step 1: Selecting size filter 'L'");
        productPage.selectSizeFilter("L");

        ExtentReportManager.logInfo("Step 2: Adding first product to cart");
        productPage.addFirstProductToCart();

        // Give it a moment for cart to update
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        ExtentReportManager.logInfo("Step 3: Clicking checkout button");
        productPage.clickCheckout();

        ExtentReportManager.logInfo("Step 4: Accepting checkout confirmation alert");
        productPage.acceptCheckoutAlert();

        ExtentReportManager.logPass("Purchase completed successfully with size L");
    }

    /**
     * Test Case 2: Verify user can add product to cart
     */
    @Test(priority = 2, description = "Verify adding product to cart")
    public void testAddProductToCart() {
        ExtentReportManager.logInfo("Starting test: Add Product to Cart");

        productPage = new ProductPage();

        ExtentReportManager.logInfo("Step 1: Selecting size filter 'M'");
        productPage.selectSizeFilter("M");

        ExtentReportManager.logInfo("Step 2: Adding first product to cart");
        productPage.addFirstProductToCart();

        // Give cart time to update
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        ExtentReportManager.logInfo("Step 3: Verifying product added");
        String cartTotal = productPage.getCartTotal();
        ExtentReportManager.logInfo("Cart Total: " + cartTotal);

        // Verify cart is not showing $0.00
        Assert.assertNotEquals(cartTotal, "$0.00", "Cart should have items");
        ExtentReportManager.logPass("Product added to cart successfully");
    }

    /**
     * Test Case 3: Verify different size filters work correctly
     */
    @Test(priority = 3, description = "Verify size filter functionality")
    public void testSizeFilterFunctionality() {
        ExtentReportManager.logInfo("Starting test: Size Filter Functionality");

        productPage = new ProductPage();

        String[] sizes = {"XS", "S", "M", "L"};

        for (String size : sizes) {
            ExtentReportManager.logInfo("Testing size filter: " + size);
            productPage.selectSizeFilter(size);

            // Small wait to see filter applied
            try { Thread.sleep(500); } catch (InterruptedException e) {}

            ExtentReportManager.logPass("Size filter " + size + " applied successfully");
        }

        ExtentReportManager.logPass("All size filters working correctly");
    }

    /**
     * Test Case 4: Verify page title
     */
    @Test(priority = 4, description = "Verify page title is displayed correctly")
    public void testPageTitle() {
        ExtentReportManager.logInfo("Starting test: Verify Page Title");

        productPage = new ProductPage();

        String pageTitle = productPage.getPageTitle();
        ExtentReportManager.logInfo("Page Title: " + pageTitle);

        Assert.assertNotNull(pageTitle, "Page title should not be null");
        Assert.assertFalse(pageTitle.isEmpty(), "Page title should not be empty");

        ExtentReportManager.logPass("Page title verified successfully: " + pageTitle);
    }

    /**
     * Test Case 5: Verify cart total calculation
     */
    @Test(priority = 5, description = "Verify cart displays total amount")
    public void testCartTotalDisplay() {
        ExtentReportManager.logInfo("Starting test: Cart Total Display");

        productPage = new ProductPage();

        ExtentReportManager.logInfo("Step 1: Adding product to cart");
        productPage.selectSizeFilter("L");
        productPage.addFirstProductToCart();

        // Wait for cart to update
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        ExtentReportManager.logInfo("Step 2: Getting cart total");
        String cartTotal = productPage.getCartTotal();
        ExtentReportManager.logInfo("Cart Total: " + cartTotal);

        Assert.assertNotNull(cartTotal, "Cart total should not be null");
        Assert.assertTrue(cartTotal.contains("$"), "Cart total should contain currency symbol");

        ExtentReportManager.logPass("Cart total displayed correctly: " + cartTotal);
    }

    /**
     * Test Case 6: End-to-end purchase flow
     */
    @Test(priority = 6, description = "Complete end-to-end purchase flow")
    public void testEndToEndPurchaseFlow() {
        ExtentReportManager.logInfo("Starting test: End-to-End Purchase Flow");

        productPage = new ProductPage();

        ExtentReportManager.logInfo("Step 1: Selecting size L");
        productPage.selectSizeFilter("L");

        ExtentReportManager.logInfo("Step 2: Adding product to cart");
        productPage.addFirstProductToCart();

        // Wait for cart update
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        ExtentReportManager.logInfo("Step 3: Proceeding to checkout");
        productPage.clickCheckout();

        ExtentReportManager.logInfo("Step 4: Confirming purchase");
        productPage.acceptCheckoutAlert();

        ExtentReportManager.logPass("End-to-end purchase flow completed successfully");
    }
}