package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtentManager {

    private static final Logger logger =
            LogManager.getLogger(ExtentManager.class);

    private static ExtentReports extent;

    private static final ThreadLocal<ExtentTest> test =
            new ThreadLocal<>();

    public static ExtentReports getInstance() {

        if (extent == null) {

            logger.info("Initializing Extent Report");

            ExtentSparkReporter reporter =
                    new ExtentSparkReporter(
                            System.getProperty("user.dir")
                                    + "/reports/extent-report.html");

            reporter.config().setDocumentTitle(
                    "Automation Report");

            reporter.config().setReportName(
                    "Flipkart E2E Execution");

            extent = new ExtentReports();

            extent.attachReporter(reporter);

            extent.setSystemInfo(
                    "Environment",
                    "QA");

            extent.setSystemInfo(
                    "Tester",
                    "SDET Automation");

            logger.info("Extent Report initialized successfully");
        }

        return extent;
    }

    public static void setTest(ExtentTest extentTest) {
        logger.debug("Setting ExtentTest for current thread");
        test.set(extentTest);
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void unload() {
        logger.debug("Removing ExtentTest from ThreadLocal");
        test.remove();
    }
}