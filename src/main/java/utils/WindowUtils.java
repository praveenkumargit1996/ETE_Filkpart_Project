package utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class WindowUtils {

    private static final Logger logger = LogManager.getLogger(WindowUtils.class);

    public static void switchToNewTab(WebDriver driver)  {

        logger.info("Switching to new tab");

        String currentTab = driver.getWindowHandle();

        for (String tab : driver.getWindowHandles()) {

            if (!tab.equals(currentTab)) {

                driver.switchTo().window(tab);
                break;
            }
        }
    }

}
