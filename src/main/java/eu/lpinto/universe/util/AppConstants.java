package eu.lpinto.universe.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * System configuration based on a properties file.
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
public final class AppConstants {

    public static final String FILE_PATH = "config.properties";

    public static final String ENV;

    /* Images (upload) */
    public static final String IMAGES_STORE_FOLDER;
    public static final String IMAGES_URL;
    public static final String HOST;

    /* REST api */
    public static final String APP_NAME;
    public static final String REST_BASE_URI = "api";
    public static final String REST_SERVICES_PACKAGE = "eu.lpinto.universe.api.services";
    public static final String REST_FILTERS_PACKAGE = "eu.lpinto.universe.api.filters";

    /* Persistence */
    public static final String PU_NAME;

    static {
        try (InputStream inputStream = AppConstants.class.getClassLoader().getResourceAsStream(FILE_PATH);) {

            if (inputStream == null) {
                throw new AssertionError("Missing config file: " + FILE_PATH);
            }

            Properties properties = new Properties();
            properties.load(inputStream);

            ENV = properties.getProperty("ENVIROMENT");
            if (ENV == null) {
                throw new AssertionError("Missing property: ENVIROMENT");
            }

            APP_NAME = properties.getProperty("APP_NAME");
            if (APP_NAME == null) {
                throw new AssertionError("Missing property: APP_NAME");
            }

            IMAGES_STORE_FOLDER = properties.getProperty("DATA_STORE_FOLDER");
            if (IMAGES_STORE_FOLDER == null) {
                throw new AssertionError("Missing property: DATA_STORE_FOLDER");
            }

            IMAGES_URL = properties.getProperty("IMAGES_URL");
            if (IMAGES_URL == null) {
                throw new AssertionError("Missing property: IMAGES_URL");
            }

            HOST = properties.getProperty("HOST");
            if (HOST == null) {
                throw new AssertionError("Missing property: HOST");
            }

            PU_NAME = properties.getProperty("PERSISTENCE_UNIT");
            if (HOST == null) {
                throw new AssertionError("Missing property: HOPERSISTENCE_UNITST");
            }
        }

        catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    private AppConstants() {
        throw new AssertionError("Private Constructor.");
    }

    public interface Enviroments {

        public static final String DEV = "dev";
        public static final String QA = "qa";
        public static final String PROD = "prod";
    }
}
