package pages;

import base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Objects;


public class CartPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(CartPage.class);

    public CartPage(WebDriver driver) {
        super(driver);

    }
    /*---------------------------------------------------------
                    Locators
    ----------------------------------------------------------*/

private final By cartTitle =
        By.xpath("Cart Page Locator");

private final By productTitle =
        By.xpath("Cart Product Title Locator");

private final By productPrice =
        By.xpath("Cart Product Price Locator");

private final By quantity =
        By.xpath("Quantity Locator");

private final By proceedToCheckoutButton =
        By.xpath("Proceed To Checkout Locator");

    /*---------------------------------------------------------
                Page Validation
    ----------------------------------------------------------*/

public boolean isPageLoaded() {

    logger.info("Verifying Cart Page loaded");

    waitUtils.waitForVisibility(cartTitle);

    return driver.findElement(cartTitle)
            .isDisplayed();
}
    /*---------------------------------------------------------
                Product Details
    ----------------------------------------------------------*/

public String getProductTitle() {

    logger.info("Fetching cart product title");

    waitUtils.waitForVisibility(productTitle);

    return driver.findElement(productTitle)
            .getText();
}

public String getProductPrice() {

    logger.info("Fetching cart product price");

    waitUtils.waitForVisibility(productPrice);

    return driver.findElement(productPrice)
            .getText();
}

public int getQuantity() {

    logger.info("Fetching product quantity");

    waitUtils.waitForVisibility(quantity);

    return Integer.parseInt(
            Objects.requireNonNull(driver.findElement(quantity)
                    .getAttribute("value"))
    );
}
    /*---------------------------------------------------------
                Business Validations
    ----------------------------------------------------------*/

public boolean isCorrectProductDisplayed(String expectedProduct) {

    logger.info("Validating product present in cart");

    return getProductTitle()
            .toLowerCase()
            .contains(expectedProduct.toLowerCase());
}

public boolean isQuantityOne() {

    logger.info("Validating quantity equals one");

    return getQuantity() == 1;
}

public boolean isPriceMatching(String expectedPrice) {

    logger.info("Validating product price");

    return getProductPrice()
            .equals(expectedPrice);
}

    /*---------------------------------------------------------
                Business Actions
    ----------------------------------------------------------*/

public CheckoutPage proceedToCheckout() {

    logger.info("Proceeding to Checkout");

    elementUtils.click(proceedToCheckoutButton);

    return new CheckoutPage(driver);
}

}


