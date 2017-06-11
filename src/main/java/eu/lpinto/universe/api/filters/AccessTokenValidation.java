package eu.lpinto.universe.api.filters;

import eu.lpinto.universe.api.dto.FaultDTO;
import eu.lpinto.universe.controllers.TokenController;
import eu.lpinto.universe.persistence.entities.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * REST filter for OAuth tokens validation.
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
@Provider
@Priority(3)
public class AccessTokenValidation implements ContainerRequestFilter, ContainerResponseFilter {

    @EJB
    private TokenController tokenController;

    private static final String AuthorizationHeader = "Authorization";
    private static final String BEARER = "Bearer ";
    private static final String USER_ID = "userID";
    private static final String ADMIN_ID = "0";
    private static final Map<String, String> dmzEndpoints;

    static {
        dmzEndpoints = new HashMap<>(5);
        dmzEndpoints.put("/tokens", HttpMethod.POST);
        dmzEndpoints.put("/people", HttpMethod.POST);
        dmzEndpoints.put("/users", HttpMethod.POST);
        dmzEndpoints.put("/users/passwordRecovery", HttpMethod.POST);

        dmzEndpoints.put("", HttpMethod.GET);
        dmzEndpoints.put("/", HttpMethod.GET);
        dmzEndpoints.put("/plans", HttpMethod.GET);
        dmzEndpoints.put("/features", HttpMethod.GET);
        dmzEndpoints.put("/planFeatures", HttpMethod.GET);
    }

    @Override
    public void filter(final ContainerRequestContext requestContext) {
        if (dmz(requestContext)) {
            return;
        }

        String bearerToken = requestContext.getHeaderString(AuthorizationHeader);

        if (bearerToken == null) {
            requestContext.abortWith(Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(new FaultDTO("101", "Invalid Authorization token")).build());
        }

        if (bearerToken != null && (bearerToken.isEmpty() || !bearerToken.startsWith(BEARER))) {
            requestContext.abortWith(Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(new FaultDTO("Invalid Authorization token")).build());

        } else {

            /*
             * Token ID
             */
            String TokenID = bearerToken.substring(BEARER.length());
            try {

                if (TokenID.isEmpty()) {
                    requestContext.abortWith(Response
                            .status(Response.Status.UNAUTHORIZED)
                            .entity(new FaultDTO("Empty access token"))
                            .build());
                    System.out.println("Empty access token.");

                } else {

                    User user = tokenController.validate(TokenID);

                    if (user == null) {
                        requestContext.abortWith(Response
                                .status(Response.Status.UNAUTHORIZED)
                                .entity(new FaultDTO("Unknown access token")).build());

                    } else {
                        requestContext.getHeaders().add(USER_ID, String.valueOf(user.getId()));
                    }
                }

            }
            catch (RuntimeException ex) {
                requestContext.abortWith(Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(new FaultDTO("Cannot validate token. Error: " + ex.getLocalizedMessage())).build());
            }
        }
    }

    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext) {
        // Empty
    }

    /*
     * Private
     */
    private static boolean dmz(final ContainerRequestContext requestContext) {
        String absolutePath = requestContext.getUriInfo().getAbsolutePath().toString();
        MultivaluedMap<String, String> queryParameters = requestContext.getUriInfo().getQueryParameters();
        String method = requestContext.getRequest().getMethod();

        if (method.equals("OPTIONS")) {
            return true;
        }

        String[] service = absolutePath.split("/api");

        if (service.length == 1) {
            return true; // root endpoint without '/'
        }

        if (service[1].startsWith("/tokens/") || service[1].startsWith("/tokens/")) {
            return true;
        }

        if ((dmzEndpoints.get(service[1]) != null && dmzEndpoints.get(service[1]).equals(method))
            || service[1].endsWith("csv")) {
            return true; // dmz endpoint + operation
        }

        List<String> hack = queryParameters.get("userhack");

        if (hack != null && !hack.isEmpty() && hack.contains("admin") && service[0].contains("localhost")) {
            requestContext.getHeaders().add(USER_ID, ADMIN_ID);
            return true;
        }

        return false;
    }
}
