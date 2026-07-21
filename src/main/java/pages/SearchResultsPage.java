package pages;

import base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WindowUtils;
import org.openqa.selenium.NoSuchElementException;

public class SearchResultsPage extends BasePage {

 /*   private WebDriver driver;

    private WaitUtils waitUtils;
    */

    private static final Logger logger =LogManager.getLogger(SearchResultsPage.class);

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    /* ---------- Locators ---------- */
    private final By searchResultsContainer =
            By.cssSelector("div[data-id]");

    private final By productTitles =
            By.cssSelector("div[data-id]");

    private final By firstProduct =
            By.cssSelector("div[data-id]");

    private final By sortDropdown =
            By.xpath("//div[contains(text(),'Sort By')]");

    private final By filterSection =
            By.xpath("//section");

    /* ---------- Validation ---------- */

    // Verify that the search results page is loaded by checking the presence of search results container
    public boolean isPageLoaded() {

        logger.info("Verifying Search Results page");

        waitUtils.waitForVisibility(searchResultsContainer);

        return driver.findElements(searchResultsContainer).size() > 0;
    }

    // Verify that the product is displayed in the search results
    public boolean isProductDisplayed(String productName) {

        logger.info("Verifying product displayed : {}", productName);

        waitForResults();

        return driver.findElements(productTitles)
                .stream()
                .anyMatch(product ->
                        product.getText()
                                .toLowerCase()
                                .contains(productName.toLowerCase()));

    }
    private void waitForResults() {

        logger.debug("Waiting for search results");
        waitUtils.waitForVisibility(productTitles);
        waitUtils.waitForPageLoad();
    }

   // WindowUtils.switchToNewTab(driver);
   public ProductPage openProduct(String productName) {

       String expectedProduct = productName.toLowerCase();
        logger.info("Opening product : {}", productName);

        waitForResults();

        driver.findElements(productTitles)
                .stream()
                .filter(product ->
                        product.getText()
                                .toLowerCase()
                                .contains(expectedProduct))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Product not found: " + productName))
                .click();

        logger.info("Switching to new product tab");
        WindowUtils.switchToNewTab(driver);
        waitUtils.waitForPageLoad();

        return new ProductPage(driver);
    }
}