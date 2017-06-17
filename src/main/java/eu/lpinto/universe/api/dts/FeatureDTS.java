package eu.lpinto.universe.api.dts;

import eu.lpinto.universe.persistence.entities.Feature;

/**
 * Feature DTO - Data Transformation Object
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
public class FeatureDTS extends AbstractDTS<Feature, eu.lpinto.universe.api.dto.Feature> {

    public static final FeatureDTS T = new FeatureDTS();

    @Override
    public eu.lpinto.universe.api.dto.Feature toAPI(Feature entity) {
        if (entity == null) {
            return null;

        }
        else if (entity.isFull()) {
            return new eu.lpinto.universe.api.dto.Feature(
                    PlanFeatureDTS.T.ids(entity.getPlans()),
                    entity.getId(), entity.getName(), entity.getCreated(), entity.getUpdated());

        }
        else {
            return new eu.lpinto.universe.api.dto.Feature(
                    null,
                    entity.getId(), entity.getName(), entity.getCreated(), entity.getUpdated());
        }
    }

    @Override
    public Feature toDomain(Long id) {
        if (id == null) {
            return null;
        }

        return new Feature(id);
    }

    @Override
    public Feature toDomain(eu.lpinto.universe.api.dto.Feature dto) {
        return new Feature(
                PlanFeatureDTS.T.toDomainIDs(dto.getPlans()),
                dto.getId(), dto.getName(), dto.getCreated(), dto.getUpdated());
    }
}
