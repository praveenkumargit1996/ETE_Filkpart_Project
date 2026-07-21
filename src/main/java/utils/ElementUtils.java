package utils;

import org.openqa.selenium.*;

public class ElementUtils {

    private final WebDriver driver;
    private final WaitUtils waitUtils;

    public ElementUtils(WebDriver driver) {

        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
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
}