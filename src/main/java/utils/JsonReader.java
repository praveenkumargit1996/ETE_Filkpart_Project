package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

public class JsonReader {

    private static final Logger logger = LogManager.getLogger(JsonReader.class);
    private final ObjectMapper objectMapper;

    public JsonReader() {

        objectMapper = new ObjectMapper();
    }

    // Generic method for Read JSON file and map to Java object
    public <T> T read(String resourceFile, Class<T> classType) {

        // Load file from resources

        InputStream inputStream =
                getClass()
                        .getClassLoader()
                        .getResourceAsStream(resourceFile);

        if(inputStream == null){

            logger.error("Unable to locate resource : {}", resourceFile);
            throw new RuntimeException("Unable to locate resource : "+ resourceFile);
        }

        try {

            return objectMapper.readValue(inputStream, classType);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to read JSON file : " + resourceFile, e);
        }
    }

}