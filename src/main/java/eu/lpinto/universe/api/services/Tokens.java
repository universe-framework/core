package eu.lpinto.universe.api.services;

import eu.lpinto.universe.api.dto.Errors;
import eu.lpinto.universe.controllers.exceptions.PreConditionException;
import eu.lpinto.universe.api.dts.UserDTS;
import eu.lpinto.universe.controllers.TokenController;
import eu.lpinto.universe.persistence.entities.Token;
import eu.lpinto.universe.persistence.entities.User;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST CRUD service for Token.
 *
 * @author Luís Pinto <code>- mail@lpinto.eu</code>
 */
@Singleton
@Path("tokens")
public class Tokens extends AbstractService {

    @EJB
    private TokenController controller;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("grant_type") String grantType, @FormParam("username") String email, @FormParam("password") String password) {
        try {
            User user = UserDTS.T.toDomain(email, password);

            Token newToken = controller.login(user);

            String result = "{\"access_token\": \"" + newToken.getToken() + "\"}";

            return ok(result);
        }

        catch (PreConditionException ex) {
            return unprocessableEntity(new Errors(ex.getErrors()));
        }

        catch (RuntimeException ex) {
            return internalError(ex);
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@HeaderParam("userID") final Long userID, @PathParam("id") final String id) {
        controller.logout(userID, id);

        return noContent();
    }
}
