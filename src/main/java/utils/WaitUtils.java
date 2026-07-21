package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class WaitUtils {

    private static final Logger logger =
            LogManager.getLogger(WaitUtils.class);

    private WebDriver driver;

    // Default enterprise timeout values
    private static final int DEFAULT_TIMEOUT = 20;
    private static final int DEFAULT_POLLING = 500;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Generic WebDriverWait creator
     */
    private WebDriverWait getWait(int timeoutInSeconds) {

        return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }

    /**
     * Generic FluentWait creator
     */
    private FluentWait<WebDriver> getFluentWait(int timeoutInSeconds) {

        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofMillis(DEFAULT_POLLING))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementClickInterceptedException.class);
    }

    /**
     * Wait for element visibility
     */
    public WebElement waitForVisibility(By locator) {

        logger.info("Waiting for visibility of element: {}", locator);

        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for visibility with custom timeout
     */
    public WebElement waitForVisibility(By locator, int timeout) {

        logger.info("Waiting for visibility of element: {}", locator);

        return getWait(timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for clickability
     */
    public WebElement waitForClickability(By locator) {

        logger.info("Waiting for element to be clickable: {}", locator);

        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait for title contains
     */
    public boolean waitForTitle(String title) {

        logger.info("Waiting for title: {}", title);

        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.titleContains(title));
    }

    /**
     * Wait for URL contains
     */
    public boolean waitForUrlContains(String partialUrl) {

        logger.info("Waiting for URL to contain: {}", partialUrl);

        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.urlContains(partialUrl));
    }

    /**
     * Wait for page load complete
     */
    public void waitForPageLoad()  {

        logger.info("Waiting for page load completion");

        ExpectedCondition<Boolean> pageLoadCondition = driver ->

                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete");

        getWait(DEFAULT_TIMEOUT).until(pageLoadCondition);
    }

    /**
     * Wait for element invisibility
     */
    public boolean waitForInvisibility(By locator) {

        logger.info("Waiting for invisibility of element: {}", locator);

        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Wait for presence of element
     */
    public WebElement waitForPresence(By locator) {

        logger.info("Waiting for presence of element: {}", locator);

        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Wait for all elements visibility
     */
    public List<WebElement> waitForAllElementsVisible(By locator) {

        logger.info("Waiting for all elements visibility: {}", locator);

        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /**
     * Wait until text appears
     */
    public boolean waitForText(By locator, String text) {

        logger.info("Waiting for text '{}' in element: {}", text, locator);

        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    /**
     * Wait for stale element refresh
     */
    public WebElement waitForElementRefresh(By locator) {

        logger.info("Waiting for refreshed element: {}", locator);

        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.refreshed(
                        ExpectedConditions.presenceOfElementLocated(locator)
                ));
    }

    /**
     * Wait and click
     */
    public void waitAndClick(By locator) {

        waitForClickability(locator).click();
    }

    /**
     * Wait for element to disappear
     */
    public boolean waitForElementToDisappear(By locator) {

        try {

            WebDriverWait wait =new WebDriverWait(driver, Duration.ofSeconds(10));

            return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));

        } catch (Exception e) {

            return true;
        }
    }

    /**
     * Wait and send keys
     */
    public void waitAndSendKeys(By locator, String text) {

        WebElement element = waitForClickability(locator);

        element.clear();
        element.sendKeys(text);
      element.sendKeys(Keys.ENTER);
    }

    /**
     * Check if element is displayed
     */
    public boolean isDisplayed(By locator) {

        try {
            return waitForVisibility(locator).isDisplayed();
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for JS / AJAX completion
     */
    public void waitForAjaxComplete() {

        logger.info("Waiting for AJAX completion");

        ExpectedCondition<Boolean> ajaxCondition = driver -> {

            JavascriptExecutor js = (JavascriptExecutor) driver;

            return (Boolean) js.executeScript(
                    "return window.jQuery != undefined && jQuery.active == 0"
            );
        };

        try {
            getWait(DEFAULT_TIMEOUT).until(ajaxCondition);
        } catch (Exception e) {

            logger.warn("AJAX wait skipped. jQuery not present.");
        }
    }

    /**
     * Hard wait (avoid unless absolutely needed)
     */
    public void hardWait(int seconds) {

        logger.warn("Applying hard wait for {} seconds", seconds);

        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new RuntimeException("Thread interrupted", e);
        }
    }

    /**
     * Generic custom wait
     */
    public <T> T waitUntil(Function<WebDriver, T> condition,
                           int timeoutInSeconds) {

        return getFluentWait(timeoutInSeconds)
                .until(condition);
    }
    /** all webElement overload method for commenting out due to not used pagefactory approach
    /**
     * waitForVisibility overload for WebElement
     *//*
    public WebElement waitForVisibility(
            WebElement element){

        return getWait(DEFAULT_TIMEOUT)
                .until(
                        ExpectedConditions
                                .visibilityOf(element)
                );
    }

    *//**
     * waitForClickability overload for WebElement
     *//*
    public WebElement waitForClickability(
            WebElement element){

        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions
                        .elementToBeClickable(element));
    }

    public boolean waitForElementToDisappear(WebElement element){

        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            return wait.until(ExpectedConditions.invisibilityOf(element));

        }
        catch(Exception e){

            return true;
        }
    }

*/
}