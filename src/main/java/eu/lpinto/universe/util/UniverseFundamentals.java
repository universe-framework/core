package eu.lpinto.universe.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * System configuration based on a properties file.
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
public final class UniverseFundamentals {

    public static final String FILE_PATH = "universe.properties";

    public static final String ENV;
    public static final String HOST;

    /* Images (upload) */
    public static final String AVATAR_FOLDER;
    public static final String AVATAR_URL_PREFIX;
    public static final String AVATAR_DEFAULT_FILE_NAME;
    
    /* REST api */
    public static final String APP_NAME;
    public static final String REST_BASE_URI = "api";
    public static final String REST_SERVICES_PACKAGE = "eu.lpinto.universe.api.services";
    public static final String REST_FILTERS_PACKAGE = "eu.lpinto.universe.api.filters";

    /* Persistence */
    public static final String PU_NAME;

    static {
        try (InputStream inputStream = UniverseFundamentals.class.getClassLoader().getResourceAsStream(FILE_PATH);) {

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

            AVATAR_FOLDER = properties.getProperty("DATA_STORE_FOLDER");
            if (AVATAR_FOLDER == null) {
                throw new AssertionError("Missing property: DATA_STORE_FOLDER");
            }

            AVATAR_URL_PREFIX = properties.getProperty("IMAGES_URL");
            if (AVATAR_URL_PREFIX == null) {
                throw new AssertionError("Missing property: IMAGES_URL");
            }
            
            AVATAR_DEFAULT_FILE_NAME = properties.getProperty("AVATAR_FILE_NAME") == null ? "logo.jpg" : properties.getProperty("AVATAR_FILE_NAME");

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

    private UniverseFundamentals() {
        throw new AssertionError("Private Constructor.");
    }

    public interface Enviroments {

        public static final String DEV = "dev";
        public static final String QA = "qa";
        public static final String PROD = "prod";
    }
}
