package tests;

import base.BaseTest;
import constants.TestDataFiles;
import factory.TestDataFactory;
import models.SearchData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CartPage;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchResultsPage;

public class SearchTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(SearchTest.class);

    @Test
    public void searchProductTest() {

        SoftAssert softAssert = new SoftAssert();

        SearchData data = TestDataFactory.json().read(TestDataFiles.SEARCH, SearchData.class);

        logger.info("Loaded test data successfully");
        HomePage homePage = new HomePage(driver);

        logger.info("Home page object created");

        /* ---------------- Home Page ---------------- */
        Assert.assertTrue(homePage.isHomePageLoaded(), "Home page failed to load.");
        logger.info("Home page loaded successfully");

        /* ---------------- Search Results ---------------- */
        SearchResultsPage resultsPage = homePage.searchProduct(data.getProduct());

        Assert.assertTrue(resultsPage.isPageLoaded(), "Search Results page failed to load.");
        Assert.assertTrue(resultsPage.isProductDisplayed(data.getProduct()), "Product not found in search results");

        /* ---------------- Product Page ---------------- */
        ProductPage productPage = resultsPage.openProduct(data.getProduct());

        Assert.assertTrue(productPage.isPageLoaded(), "Product page failed to load.");
        softAssert.assertTrue(productPage.isCorrectProductDisplayed(data.getProduct()), "Incorrect product opened");

        String expectedTitle = productPage.getProductTitle();
        String expectedPrice = productPage.getProductPrice();

        softAssert.assertFalse(expectedTitle.isBlank(), "Product title is empty.");
        logger.info("Product Title : {}", expectedTitle);

        softAssert.assertFalse(expectedPrice.isBlank(), "Product price is empty.");
        logger.info("Product Price : {}", expectedPrice);

        softAssert.assertTrue(productPage.isAddToCartButtonVisible(), "Add To Cart button not visible.");

        productPage.addToCart();

        /* ---------------- Cart Page ---------------- */
        CartPage cartPage = productPage.openCart();

        softAssert.assertTrue(cartPage.isPageLoaded(), "Cart page failed to load.");

        softAssert.assertTrue(cartPage.isCorrectProductDisplayed(expectedTitle), "Incorrect product in cart");

        softAssert.assertEquals(cartPage.getProductPrice(), expectedPrice, "Product price mismatch");

        softAssert.assertEquals(cartPage.getProductQuantity(), "1", "Quantity should be 1");

        softAssert.assertTrue(cartPage.isPlaceOrderButtonVisible(), "Place Order button not visible");

        //assertAll() will collate all the soft assertions and report them at once,
        // instead of failing at the first assertion failure.
        softAssert.assertAll();


    }
}