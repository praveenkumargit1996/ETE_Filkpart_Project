package base;

import org.openqa.selenium.WebDriver;
import utils.ElementUtils;
import utils.WaitUtils;

public abstract class BasePage {

    protected WebDriver driver;
    protected WaitUtils waitUtils;
    protected ElementUtils elementUtils;

    public BasePage(WebDriver driver){

        this.driver=driver;
        this.waitUtils= new WaitUtils(driver);
        this.elementUtils = new ElementUtils(driver, waitUtils);

        // PageFactory is not used in this implementation, because we are using By locators to avoid StaleElementReferenceException.
        //   PageFactory.initElements(driver, this);
    }
}