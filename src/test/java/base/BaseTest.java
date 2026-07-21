package base;

import config.ConfigReader;
import config.FrameworkConfig;
import factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.WaitUtils;

public class BaseTest {

    protected WebDriver driver;
    protected ConfigReader configReader;
    protected FrameworkConfig frameworkConfig;
    protected WaitUtils waitUtils;

    private static final Logger logger =
            LogManager.getLogger(BaseTest.class);


    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setup( @Optional("chrome") String browser){

        try{

            logger.info("Initializing test setup");

            configReader = new ConfigReader();
            frameworkConfig = configReader.loadConfig();

            /*
             Browser priority:
             1 TestNG parameter
             2 System property
             3 Config file
             */

           /* String browserName =
                    browser != null ? browser : System.getProperty(
                            "browser", configReader.getBrowser());
*/
            logger.info("Launching browser : {}",frameworkConfig.getBrowser());

            DriverFactory.initDriver(frameworkConfig);

            driver = DriverFactory.getDriver();

            waitUtils = new WaitUtils(driver);

            logger.info("Opening application URL");

            driver.get(frameworkConfig.getUrl());

            waitUtils.waitForPageLoad();

            logger.info("Application launched successfully");

        }

        catch(Exception e){

            logger.error("Setup failed", e);

            throw new RuntimeException("Framework initialization failed", e);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){

        logger.info("Closing browser");

        DriverFactory.quitDriver();

        logger.info("Browser closed successfully");
    }

}