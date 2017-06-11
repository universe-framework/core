package eu.lpinto.universe.api.dts;

import eu.lpinto.universe.persistence.entities.PlanFeature;

/**
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
public class PlanFeatureDTS extends BaseDTS<PlanFeature, eu.lpinto.universe.api.dto.PlanFeature> {

    public static final PlanFeatureDTS T = new PlanFeatureDTS();

    @Override
    public eu.lpinto.universe.api.dto.PlanFeature toAPI(PlanFeature entity) {
        if (entity == null) {
            return null;

        }
        else if (entity.isFull()) {
            return new eu.lpinto.universe.api.dto.PlanFeature(
                    entity.getValue(),
                    PlanDTS.id(entity.getPlan()),
                    FeatureDTS.id(entity.getFeature()),
                    entity.getId(), entity.getName(), entity.getCreated(), entity.getUpdated());

        }
        else {
            return new eu.lpinto.universe.api.dto.PlanFeature(
                    entity.getValue(),
                    PlanDTS.id(entity.getPlan()),
                    FeatureDTS.id(entity.getFeature()),
                    entity.getId(), entity.getName(), entity.getCreated(), entity.getUpdated());
        }
    }

    @Override
    public PlanFeature toDomain(Long id) {
        if (id == null) {
            return null;
        }

        return new PlanFeature(id);
    }

    @Override
    public PlanFeature toDomain(eu.lpinto.universe.api.dto.PlanFeature dto) {
        return new PlanFeature(dto.getValue(),
                               PlanDTS.T.toDomain(dto.getPlan()),
                               FeatureDTS.T.toDomain(dto.getFeature()),
                               dto.getId(), dto.getName(), dto.getCreated(), dto.getUpdated());
    }
}
