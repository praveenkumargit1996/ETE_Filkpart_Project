package listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.ExtentManager;
import utils.ScreenshotUtils;

import java.lang.reflect.Field;

public class TestListener implements ITestListener {

    private static final Logger logger =
            LogManager.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {

        logger.info("Execution Started : {}", context.getName());

        ExtentManager.getInstance();
    }

    @Override
    public void onTestStart(ITestResult result) {

        logger.info(
                "Starting Test : {}",
                result.getMethod().getMethodName()
        );

        ExtentManager.setTest(
                ExtentManager
                        .getInstance()
                        .createTest(
                                result.getMethod()
                                        .getMethodName()
                        )
        );

        ExtentManager.getTest()
                .info("Test execution started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        logger.info(
                "Test Passed : {}",
                result.getMethod().getMethodName()
        );

        ExtentManager.getTest()
                .log(Status.PASS,"Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        logger.error("Test Failed : {}",result.getMethod().getMethodName());

        try {

            Object testClass =
                    result.getInstance();

            // Walk up the class hierarchy to find 'driver' field 
            // (could be in superclass BaseTest, not just in immediate test class)
            WebDriver driver = null;
            Class<?> clazz = result.getTestClass().getRealClass();
            Field field = null;
            
            while (clazz != null && clazz != Object.class) {
                try {
                    field = clazz.getDeclaredField("driver");
                    field.setAccessible(true);
                    driver = (WebDriver) field.get(testClass);
                    break;
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
            }

            // Fallback: try to get driver from DriverFactory if reflection failed
            if (driver == null) {
                try {
                    driver = DriverFactory.getDriver();
                    logger.info("Retrieved driver from DriverFactory (reflection fallback)");
                } catch (Exception e) {
                    logger.warn("DriverFactory.getDriver() also failed: {}", e.getMessage());
                }
            }

            // If still null, log and skip screenshot
            if (driver == null) {
                logger.warn("Driver instance is null; skipping failure screenshot for test: {}",
                            result.getMethod().getMethodName());
                ExtentManager.getTest()
                        .fail(result.getThrowable())
                        .fail("Screenshot not captured - Driver instance was null");
                return;
            }

            ScreenshotUtils screenshot =new ScreenshotUtils(driver);

            String screenshotPath =
                    screenshot.captureFailureScreenshot(
                            result.getMethod()
                                    .getMethodName()
                    );

            logger.info("Failure screenshot captured at: {}", screenshotPath);

            ExtentManager.getTest()
                    .fail( result.getThrowable())
                    .fail("Failure Screenshot",
                            MediaEntityBuilder
                                    .createScreenCaptureFromPath(screenshotPath)
                                    .build()
                    );

        }
        catch (Exception e) {

            logger.error("Screenshot capture failed",e);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        logger.warn(
                "Test Skipped : {}",
                result.getMethod().getMethodName()
        );

        ExtentManager.getTest()
                .log(
                        Status.SKIP,
                        result.getThrowable()
                );
    }

    @Override
    public void onFinish(ITestContext context) {

        logger.info(
                "Execution Finished : {}",
                context.getName()
        );

        ExtentManager.getInstance()
                .flush();

        ExtentManager.unload();
    }
}