package eu.lpinto.universe.persistence.facades;

import eu.lpinto.universe.persistence.entities.Image;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Image Facade
 *
 * @author Vítor Martins <code>- vitor.martins@petuniversal.com</code>
 */
@Stateless
public class ImageFacade extends AbstractFacade<Image> {

    @PersistenceContext
    private EntityManager em;

    public ImageFacade() {
        super(Image.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
