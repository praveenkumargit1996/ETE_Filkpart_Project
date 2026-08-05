package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ActionUtils {

    private final WebDriver driver;

    private final WaitUtils waitUtils;

    public ActionUtils(WebDriver driver, WaitUtils waitUtils) {

        this.driver = driver;
        this.waitUtils = waitUtils;
    }

    public void hover(By locator) {

        WebElement element = waitUtils.waitForVisibility(locator);

        new Actions(driver).moveToElement(element).perform();
    }

    public void doubleClick(By locator) {

        WebElement element = waitUtils.waitForClickability(locator);

        new Actions(driver).doubleClick(element).perform();
    }

    public void hoverAndClick(By locator) {

        WebElement element = waitUtils.waitForClickability(locator);

        new Actions(driver).moveToElement(element).click().perform();
    }

    public void dragAndDrop(By source, By target) {

        WebElement src = waitUtils.waitForVisibility(source);

        WebElement dest = waitUtils.waitForVisibility(target);

        new Actions(driver).dragAndDrop(src, dest).perform();
    }

}