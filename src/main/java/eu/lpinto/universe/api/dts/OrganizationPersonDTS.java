package eu.lpinto.universe.api.dts;

import eu.lpinto.universe.persistence.entities.OrganizationPerson;
import eu.lpinto.universe.persistence.entities.WorkerProfile;

/**
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
public class OrganizationPersonDTS extends BaseDTS<OrganizationPerson, eu.lpinto.universe.api.dto.OrganizationPerson> {

    public static final OrganizationPersonDTS T = new OrganizationPersonDTS();

    @Override
    public eu.lpinto.universe.api.dto.OrganizationPerson toAPI(OrganizationPerson entity) {
        if (entity == null) {
            return null;

        }
        else if (entity.isFull()) {
            return new eu.lpinto.universe.api.dto.OrganizationPerson(
                    entity.getPmsID(),
                    PersonDTS.id(entity.getPerson()),
                    OrganizationDTS.id(entity.getOrganization()),
                    entity.getProfile() == null ? null : entity.getProfile().ordinal(),
                    entity.getId(), entity.getName(), entity.getCreated(), entity.getUpdated());

        }
        else {
            return new eu.lpinto.universe.api.dto.OrganizationPerson(
                    entity.getPmsID(),
                    PersonDTS.id(entity.getPerson()),
                    OrganizationDTS.id(entity.getOrganization()),
                    entity.getProfile() == null ? null : entity.getProfile().ordinal(),
                    entity.getId(), entity.getName(), entity.getCreated(), entity.getUpdated());
        }
    }

    @Override
    public OrganizationPerson toDomain(Long id) {
        if (id == null) {
            return null;
        }

        return new OrganizationPerson(id);
    }

    @Override
    public OrganizationPerson toDomain(eu.lpinto.universe.api.dto.OrganizationPerson dto) {
        return new OrganizationPerson(
                dto.getPmsID(),
                PersonDTS.T.toDomain(dto.getPerson()),
                OrganizationDTS.T.toDomain(dto.getOrganization()),
                dto.getRole() == null ? null : WorkerProfile.values()[dto.getRole()],
                dto.getId(), dto.getName(), dto.getCreated(), dto.getUpdated());
    }
}
