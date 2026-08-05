package factory;

import config.BrowserOptionsManager;
import config.FrameworkConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    private static final Logger logger = LogManager.getLogger(DriverFactory.class);

    private static final ThreadLocal<WebDriver> driver =new ThreadLocal<>();

    public static void initDriver(FrameworkConfig config){

        BrowserOptionsManager optionsManager = new BrowserOptionsManager();
        String browser = config.getBrowser();
        WebDriver webDriver;

        switch(browser.toLowerCase()){

            case "chrome":

                logger.info("Launching Chrome");
                WebDriverManager.chromedriver().setup();
                webDriver = new ChromeDriver(optionsManager.getChromeOptions(config));

                break;

            case "firefox":

                logger.info("Launching Firefox");
                WebDriverManager.firefoxdriver().setup();
                webDriver = new FirefoxDriver(optionsManager.getFirefoxOptions(config));

                break;


            case "edge":

                logger.info("Launching Edge");

                webDriver = new EdgeDriver(optionsManager.getEdgeOptions(config));

                break;


            default:

                logger.error("Unsupported browser: {}", browser);
                throw new RuntimeException("Unsupported browser : " + browser);
        }

        driver.set(webDriver);
        logger.info("Driver initialized successfully for browser: {}", browser);
        if(config.isMaximize()) {
            getDriver().manage().window().maximize();
        }
    }

    public static WebDriver getDriver(){

        return driver.get();
    }

    public static void quitDriver(){

        if(getDriver()!=null){
            getDriver().quit();
            driver.remove();
        }
    }

}
