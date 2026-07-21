package base;

import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public abstract class BasePage {

    protected WebDriver driver;
    protected WaitUtils waitUtils;

    public BasePage(WebDriver driver){

        this.driver=driver;
        this.waitUtils= new WaitUtils(driver);

     //   PageFactory.initElements(driver, this);
    }
}