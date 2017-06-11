package eu.lpinto.universe.api.util;

import eu.lpinto.universe.util.AppConstants;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Root configuration of Jersey and the REST API.
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
@ApplicationPath(AppConstants.REST_BASE_URI)
public class RestApplication extends ResourceConfig {

    public RestApplication() {
        register(JacksonConfigurator.class);
        register(JacksonFeature.class);

        packages(AppConstants.REST_SERVICES_PACKAGE);
        packages(AppConstants.REST_FILTERS_PACKAGE);
    }
}
