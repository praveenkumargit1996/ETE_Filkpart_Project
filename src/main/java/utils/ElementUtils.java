package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;

public class ElementUtils {

    private static final Logger logger =
            LogManager.getLogger(ElementUtils.class);

    private final WebDriver driver;
    private final WaitUtils waitUtils;

    public ElementUtils(WebDriver driver, WaitUtils waitUtils) {

        this.driver = driver;
        this.waitUtils = waitUtils;
    }

    public void click(By element) {

        try {

            waitUtils.waitForClickability(element);
            scrollIntoView(element);
            driver.findElement(element).click();

        } catch (StaleElementReferenceException |
                 ElementClickInterceptedException e) {

            waitUtils.waitForClickability(element);
            scrollIntoView(element);
            driver.findElement(element).click();
        }
    }

    public void scrollIntoView(By locator) {

        WebElement element = driver.findElement(locator);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                element
        );
    }

    public String getText(By locator) {

        for (int i = 0; i < 2; i++) {
            try {
                waitUtils.waitForVisibility(locator);
                return driver.findElement(locator).getText();
            } catch (StaleElementReferenceException e) {
                logger.debug("Element became stale. Retrying...");
            }
        }

        throw new StaleElementReferenceException(
                "Unable to get text after retry.");
    }

    public boolean isDisplayed(By locator) {

        waitUtils.waitForVisibility(locator);
        return driver.findElement(locator).isDisplayed();
    }
}