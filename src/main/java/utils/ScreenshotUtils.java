package utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    private static final Logger logger =
            LogManager.getLogger(ScreenshotUtils.class);

    private WebDriver driver;

    // Base screenshot directory
    private static final String BASE_PATH =
            System.getProperty("user.dir") +
                    "/reports/screenshots/";

    public ScreenshotUtils(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Generate timestamp
     */
    private String getTimestamp() {

        return new SimpleDateFormat(
                "yyyyMMdd_HHmmss_SSS"
        ).format(new Date());
    }

    /**
     * Create directory if not exists
     */
    private void createDirectory(String path) {

        File dir = new File(path);

        if (!dir.exists()) {

            boolean created = dir.mkdirs();

            if (created) {
                logger.info("Directory created: {}", path);
            }
        }
    }

    /**
     * Capture screenshot helper
     */
    private String captureScreenshot(String folderName,
                                     String screenshotName) {

        createDirectory(BASE_PATH + folderName);

        String timestamp = getTimestamp();

        String finalPath =
                BASE_PATH +
                        folderName +
                        "/" +
                        screenshotName +
                        "_" +
                        timestamp +
                        ".png";

        try {

            File srcFile =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.FILE);

            File destFile = new File(finalPath);

            FileUtils.copyFile(srcFile, destFile);

            logger.info("Screenshot captured: {}", finalPath);

            return finalPath;

        } catch (IOException e) {

            logger.error("Failed to capture screenshot", e);

            throw new RuntimeException(
                    "Screenshot capture failed", e);
        }
    }

    /**
     * Capture failure screenshot
     */
    public String captureFailureScreenshot(String testName) {

        logger.info("Capturing failure screenshot");

        return captureScreenshot(
                "failures",
                testName
        );
    }

    /**
     * Capture step screenshot
     */
    public String captureStepScreenshot(String stepName) {

        logger.info("Capturing step screenshot");

        return captureScreenshot(
                "steps",
                stepName
        );
    }

    /**
     * Capture full page screenshot
     */
    public String captureFullPageScreenshot(String pageName) {

        logger.info("Capturing full page screenshot");

        return captureScreenshot(
                "fullpage",
                pageName
        );
    }

    /**
     * Capture element screenshot
     */
    public String captureElementScreenshot(WebElement element,
                                           String elementName) {

        createDirectory(BASE_PATH + "elements");

        String timestamp = getTimestamp();

        String finalPath =
                BASE_PATH +
                        "elements/" +
                        elementName +
                        "_" +
                        timestamp +
                        ".png";

        try {

            File srcFile =
                    element.getScreenshotAs(OutputType.FILE);

            File destFile = new File(finalPath);

            FileHandler.copy(srcFile, destFile);

            logger.info("Element screenshot captured: {}",
                    finalPath);

            return finalPath;

        } catch (IOException e) {

            logger.error("Failed to capture element screenshot", e);

            throw new RuntimeException(
                    "Element screenshot failed", e);
        }
    }

    /**
     * Capture screenshot as Base64
     * Useful for Allure/Extent Reports
     */
    public String captureScreenshotBase64() {

        logger.info("Capturing Base64 screenshot");

        return ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BASE64);
    }

    /**
     * Delete old screenshots
     * Useful in CI/CD cleanup
     */
    public void clearOldScreenshots() {

        File screenshotDir = new File(BASE_PATH);

        if (screenshotDir.exists()) {

            try {

                FileUtils.deleteDirectory(screenshotDir);

                logger.info("Old screenshots deleted");

            } catch (IOException e) {

                logger.error("Failed to delete screenshots", e);
            }
        }
    }
}