package pages;

import base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private static final Logger logger =LogManager.getLogger(HomePage.class);

    public HomePage(WebDriver driver){

        super(driver);
    }

    /* ---------- Locators ---------- */

    private final By searchBox = By.name("q");

    private final By searchButton =By.xpath("//button[@type='submit']");

    private final By loginButton =By.xpath("//span[contains(text(),'Login')]");

    private final By loginPopupCloseButton =By.xpath("//span[@role='button']");

    /* ---------- Validation ---------- */

    public boolean isHomePageLoaded(){

        logger.info("Verifying homepage loaded");
        closeLoginPopupIfPresent();
        waitUtils.waitForPageLoad();

    //    return waitUtils.waitForVisibility(searchBox).isDisplayed();
        return waitUtils.isDisplayed(searchBox);
    }

    /* ---------- Business actions ---------- */

    public SearchResultsPage searchProduct(String productName){

        logger.info("Searching product : {}", productName);

        closeLoginPopupIfPresent();
        waitUtils.waitForElementToDisappear(loginPopupCloseButton);
        waitUtils.waitAndSendKeys(searchBox, productName);

        return new SearchResultsPage(driver);
    }

    public boolean isSearchBoxVisible(){

        logger.info("Checking search box visibility");
        closeLoginPopupIfPresent();

        return waitUtils.isDisplayed(searchBox);
    }

    public void clickLogin(){

        logger.info("Clicking login button");
        waitUtils.waitAndClick(loginButton);
    }


    private void closeLoginPopupIfPresent() {

        try {
            logger.info("Checking for Flipkart login popup");

            if (waitUtils.isDisplayed(loginPopupCloseButton)) {

                logger.info("Login popup detected. Closing it.");
                waitUtils.waitAndClick(loginPopupCloseButton);
                logger.info("Login popup closed.");
            }

       } catch (Exception e) {

            logger.info("Login popup not present.");
        }
    }

}