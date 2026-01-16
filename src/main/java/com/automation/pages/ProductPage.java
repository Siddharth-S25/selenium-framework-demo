package com.automation.pages;
import org.openqa.selenium.By;


/**
 * ProductPage class representing the product catalog page
 * Contains all elements and actions for the product page
 * Extends BasePage to inherit common methods
 *
 * @author Your Name
 * @version 1.0
 */
public class ProductPage extends BasePage {

    // ==================== LOCATORS (Private - Encapsulation) ====================

    // Size filter locators
    private final By sizeFilterXS = By.xpath("//span[normalize-space()='XS']");
    private final By sizeFilterS = By.xpath("//span[normalize-space()='S']");
    private final By sizeFilterM = By.xpath("//span[normalize-space()='M']");
    private final By sizeFilterL = By.xpath("//span[normalize-space()='L']");
    private final By sizeFilterXL = By.xpath("//span[normalize-space()='XL']");
    private final By sizeFilterXXL = By.xpath("//span[normalize-space()='XXL']");

    // Product and cart locators
    private final By addToCartButtons = By.xpath("//div[@class='sc-124al1g-2 jngQVM']//button");
    private final By firstAddToCartButton = By.xpath("(//div[@class='sc-124al1g-2 jngQVM']//button)[1]");

    private final By cartIcon = By.xpath("//div[@class='sc-1h98xa9-0 fihYFM']");
    private final By checkoutButton = By.xpath("//button[normalize-space()='Checkout']");

    private final By cartItemCount = By.xpath("//div[@class='sc-1h98xa9-1 bTfDcM']");
    private final By cartTotal = By.xpath("//p[@class='sc-1h98xa9-9 jzywDV']");

    // ==================== ACTIONS (Public - Page Methods) ====================

    /**
     * Select size filter
     * @param size Size to filter (XS, S, M, L, XL, XXL)
     * @return ProductPage for method chaining
     */
    public ProductPage selectSizeFilter(String size) {
        By sizeLocator;

        // Determine which size locator to use
        switch (size.toUpperCase()) {
            case "XS":
                sizeLocator = sizeFilterXS;
                break;
            case "S":
                sizeLocator = sizeFilterS;
                break;
            case "M":
                sizeLocator = sizeFilterM;
                break;
            case "L":
                sizeLocator = sizeFilterL;
                break;
            case "XL":
                sizeLocator = sizeFilterXL;
                break;
            case "XXL":
                sizeLocator = sizeFilterXXL;
                break;
            default:
                throw new IllegalArgumentException("Invalid size: " + size + ". Valid sizes: XS, S, M, L, XL, XXL");
        }

        // Use BasePage's click method
        click(sizeLocator);
        return this; // Return this for method chaining
    }

    /**
     * Add first product to cart
     * @return ProductPage for method chaining
     */
    public ProductPage addFirstProductToCart() {
        click(firstAddToCartButton); // Using BasePage method!
        return this;
    }

    /**
     * Add product to cart by index
     * @param index Product index (1-based, first product = 1)
     * @return ProductPage for method chaining
     */
    public ProductPage addProductToCartByIndex(int index) {
        By productButton = By.xpath("(//div[@class='sc-124al1g-2 jngQVM']//button)[" + index + "]");
        click(productButton);
        return this;
    }

    /**
     * Open shopping cart
     * @return ProductPage for method chaining
     */
    public ProductPage openCart() {
        click(cartIcon);
        return this;
    }

    /**
     * Click checkout button
     * @return ProductPage for method chaining
     */
    public ProductPage clickCheckout() {
        click(checkoutButton);
        return this;
    }

    /**
     * Accept checkout alert
     * @return ProductPage for method chaining
     */
    public ProductPage acceptCheckoutAlert() {
        acceptAlert(); // Using BasePage method!
        return this;
    }

    // ==================== VALIDATIONS (Public - Verification Methods) ====================

    /**
     * Get cart item count
     * @return Number of items in cart as String
     */
    public String getCartItemCount() {
        if (isElementDisplayed(cartItemCount)) { // Using BasePage method!
            return getText(cartItemCount); // Using BasePage method!
        }
        return "0";
    }

    /**
     * Get cart total amount
     * @return Cart total as string (e.g., "$59.90")
     */
    public String getCartTotal() {
        if (isElementDisplayed(cartTotal)) {
            return getText(cartTotal);
        }
        return "$0.00";
    }

    /**
     * Check if cart is empty
     * @return true if cart is empty, false otherwise
     */
    public boolean isCartEmpty() {
        return "0".equals(getCartItemCount());
    }

    /**
     * Complete purchase flow: select size, add product, checkout
     * This is a helper method combining multiple steps
     * @param size Size to filter
     * @return ProductPage for method chaining
     */
    public ProductPage completePurchaseFlow(String size) {
        selectSizeFilter(size);
        addFirstProductToCart();
        clickCheckout();
        acceptCheckoutAlert();
        return this;
    }
}