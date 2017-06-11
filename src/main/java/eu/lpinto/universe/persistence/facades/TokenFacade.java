package eu.lpinto.universe.persistence.facades;

import eu.lpinto.earth.persistence.facades.AbstractFacade;
import eu.lpinto.universe.persistence.entities.Token;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
@Stateless
public class TokenFacade extends AbstractFacade<Token> {

    @PersistenceContext(unitName = PU_NAME)
    private EntityManager em;

    public TokenFacade() {
        super(Token.class);
    }

    /*
     * DAO
     */
    public Token findByToken(final String token) {
        try {
            Token session = getEntityManager().createQuery(
                    "SELECT t FROM Token t WHERE t.token = :token", Token.class)
                    .setParameter("token", token)
                    .getSingleResult();

            return session;
        }
        catch (NoResultException ex) {
            return null;
        }
    }

    /*
     * CRUD
     */
    @Override
    public void create(final Token entity) {
        if (entity.getUser() != null && entity.getUser().getTokens() != null && !entity.getUser().getTokens().isEmpty()) {
            Token oldToken = entity.getUser().getTokens().get(0);
            this.remove(oldToken);
        }

        Calendar now = new GregorianCalendar();

        entity.setCreated(now);
        entity.setUpdated(now);

        super.create(entity);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
