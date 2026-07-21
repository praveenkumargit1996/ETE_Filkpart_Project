package tests;

import base.BaseTest;
import constants.TestDataFiles;
import factory.TestDataFactory;
import models.SearchData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchResultsPage;

public class SearchTest extends BaseTest {

    private static final Logger logger =
            LogManager.getLogger(SearchTest.class);

    @Test
    public void searchProductTest(){

        SearchData data =TestDataFactory.json().read(TestDataFiles.SEARCH,SearchData.class);

        HomePage homePage = new HomePage(driver);
        logger.info("Home page object created successfully");

       Assert.assertTrue(homePage.isHomePageLoaded());
       // Assert.assertTrue(false);
        logger.info("Home page loaded successfully");
       // Assert.assertTrue(homePage.searchBox.isDisplayed(), "Search box not displayed");

        SearchResultsPage resultsPage = homePage.searchProduct(data.getProduct());

        Assert.assertTrue(resultsPage.isPageLoaded());
        Assert.assertTrue(resultsPage.isProductDisplayed(data.getProduct()));

        ProductPage productPage = resultsPage.openProduct(data.getProduct());

        Assert.assertTrue(productPage.isPageLoaded());
        Assert.assertTrue(productPage.isCorrectProductDisplayed(data.getProduct()));
    }
}