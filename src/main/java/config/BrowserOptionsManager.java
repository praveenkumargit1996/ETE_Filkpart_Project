package config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserOptionsManager {

    private static final Logger logger =LogManager.getLogger(BrowserOptionsManager.class);

    public ChromeOptions getChromeOptions(FrameworkConfig config){

        ChromeOptions options = new ChromeOptions();
        logger.info("Configuring ChromeOptions ");
        if(config.isHeadless()) {

            options.addArguments("--headless=new");
        }

        if(config.isIncognito()) {

            options.addArguments("--incognito");
        }

        if(config.isMaximize()) {

            options.addArguments("--start-maximized");
        }

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");

        return options;

    }

    public FirefoxOptions getFirefoxOptions(FrameworkConfig config){

        FirefoxOptions options = new FirefoxOptions();
        logger.info("Configuring firefoxOptions ");
        if(config.isHeadless()) {

            options.addArguments("-headless");
        }

        if(config.isIncognito()) {

            options.addArguments("-private");
        }

   //     options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");

        return options;
    }

    public EdgeOptions getEdgeOptions(FrameworkConfig config){

        EdgeOptions options = new EdgeOptions();
        logger.info("Configuring edgeOptions ");
        if(config.isHeadless()) {

            options.addArguments("--headless=new");
        }

        if(config.isIncognito()) {

            options.addArguments("--inprivate");
        }

        if(config.isMaximize()) {

            options.addArguments("--start-maximized");
        }

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");

        return options;
    }
}