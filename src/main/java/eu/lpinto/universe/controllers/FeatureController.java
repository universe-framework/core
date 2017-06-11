package eu.lpinto.universe.controllers;

import eu.lpinto.universe.persistence.entities.Feature;
import eu.lpinto.universe.persistence.facades.FeatureFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
@Stateless
public class FeatureController extends AbstractCrudController<Feature> {

    @EJB
    private FeatureFacade facade;

    public FeatureController() {
        super(Feature.class.getCanonicalName());
    }

    @Override
    protected FeatureFacade getFacade() {
        return facade;
    }
}
