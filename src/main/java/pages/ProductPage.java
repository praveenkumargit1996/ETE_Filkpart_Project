package pages;

import base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(ProductPage.class);

    public ProductPage(WebDriver driver) {
        super(driver);

    }

    /* ---------- Locators ---------- */
    private final By productTitle =
            By.xpath("//h1");

    private final By productPrice =
            By.xpath("//div[@class='v1zwn21l v1zwn20 _1psv1zeb9 _1psv1ze0']");

    private final By addToCartButton =
            By.xpath("//*[local-name()='clipPath' and @id='AddToCart_a']/ancestor::div[2]");

    /* ---------- Validation ---------- */
    public boolean isPageLoaded() {

        logger.info("Verifying Product Page loaded");

        waitUtils.waitForVisibility(productTitle);

        return driver.findElement(productTitle)
                .isDisplayed();
    }

    /* ---------- Business actions ---------- */
    public String getProductPrice() {

        logger.info("Fetching product price");

        waitUtils.waitForVisibility(productPrice);

        return driver.findElement(productPrice)
                .getText();
    }

    public boolean isAddToCartButtonVisible() {

        logger.info("Checking Add To Cart button");

        waitUtils.waitForVisibility(addToCartButton);

        return driver.findElement(addToCartButton)
                .isDisplayed();
    }

    /* ---------- Business actions ---------- */
    public String getProductTitle() {

        logger.info("Fetching product title");

        return elementUtils.getText(productTitle);
    }

    public boolean isCorrectProductDisplayed(String expectedProduct) {

        logger.info("Validating selected product");

        return getProductTitle()
                .toLowerCase()
                .contains(expectedProduct.toLowerCase());

    }

    public CartPage addToCart() {

        logger.info("Adding product to cart");

        waitUtils.waitForClickability(addToCartButton);

        driver.findElement(addToCartButton)
                .click();

        return new CartPage(driver);

    }

}