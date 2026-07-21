package config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    // 1. Initialize Log4j2 Logger
    private static final Logger log = LogManager.getLogger(ConfigReader.class);
    Properties properties;

    public ConfigReader() {

        properties = new Properties();
        // Use a descriptive name for your path string
        String filePath = "src/main/resources/config.properties";

        try {

            // Changed from logger.info to log.info to match standard log4j instance names
            log.info("Attempting to load configuration framework file from path: [{}]", filePath);
            FileInputStream fis =new FileInputStream(filePath);
            properties.load(fis);

            log.info("Configuration properties loaded successfully.");

        } catch (IOException e) {
            // 2. Log at the ERROR level and pass 'e' cleanly as the last parameter to preserve the stack trace
            log.error("CRITICAL FRAMEWORK FAILURE: Unable to locate or load configuration file at path [{}]", filePath, e);

            // 3. Immediately halt execution! No tests can run without the configuration file.
            throw new RuntimeException("Terminating automation framework suite startup path. Configuration file is missing or unreadable.", e);
        }
    }

   /* // need to remove
    public String getBrowser() {
        return properties.getProperty("browser");
    }

    public String getUrl() {
        return properties.getProperty("url");
    }

    //headless mode
    public boolean isHeadless() {

        return Boolean.parseBoolean(properties.getProperty("headless"));
    }*/
  // till here need to remove

    private String getPropertyValue(String key,String defaultValue)
    {
        String systemProperty =System.getProperty(key);

        if(systemProperty != null)
        {
            log.info("{} loaded from Maven/Jenkins : {}", key, systemProperty);
            return systemProperty;
        }

        String environmentVariable = System.getenv(key.toUpperCase());

        if(environmentVariable != null)
        {
            log.info("{} loaded from Environment Variable : {}", key, environmentVariable);
            return environmentVariable;
        }

        String propertyValue =properties.getProperty(key,defaultValue);

        log.info("{} loaded from config.properties : {}",key,propertyValue);
        return propertyValue;

    }

    public FrameworkConfig loadConfig()
    {
        FrameworkConfig config =new FrameworkConfig();

        config.setBrowser(
                getPropertyValue(
                        "browser",
                        "chrome"
                )
        );

        config.setUrl(
                getPropertyValue(
                        "url",
                        "https://www.flipkart.com"
                )
        );

        config.setHeadless(
                Boolean.parseBoolean(
                        getPropertyValue(
                                "headless",
                                "false"
                        )
                )
        );

        config.setIncognito(
                Boolean.parseBoolean(
                        getPropertyValue(
                                "incognito",
                                "false"
                        )
                )
        );

        config.setMaximize(
                Boolean.parseBoolean(
                        getPropertyValue("maximize", "true")));

        return config;
    }

}
