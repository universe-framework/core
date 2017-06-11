package eu.lpinto.universe.api.dts;

import eu.lpinto.universe.persistence.entities.Person;

/**
 * Person DTO - Data Transformation Object
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
public class PersonDTS extends BaseDTS<Person, eu.lpinto.universe.api.dto.Person> {

    public static final PersonDTS T = new PersonDTS();

    @Override
    public eu.lpinto.universe.api.dto.Person toAPI(Person entity) {
        if (entity == null) {
            return null;

        }
        else if (entity.isFull()) {
            return new eu.lpinto.universe.api.dto.Person(
                    entity.getEmail(),
                    entity.getPhone(),
                    entity.getMobilePhone(),
                    entity.getStreet(),
                    entity.getTown(),
                    entity.getCountry(),
                    entity.getZip(),
                    entity.getNif(),
                    OrganizationPersonDTS.T.ids(entity.getOrganizations()),
                    entity.getId(), entity.getName(), entity.getCreated(), entity.getUpdated());

        }
        else {
            return new eu.lpinto.universe.api.dto.Person(
                    entity.getEmail(),
                    entity.getPhone(),
                    entity.getMobilePhone(),
                    entity.getStreet(),
                    entity.getTown(),
                    entity.getCountry(),
                    entity.getZip(),
                    entity.getNif(),
                    null,
                    entity.getId(), entity.getName(), entity.getCreated(), entity.getUpdated());
        }
    }

    @Override
    public Person toDomain(Long id) {
        if (id == null) {
            return null;
        }

        return new Person(id);
    }

    @Override
    public Person toDomain(eu.lpinto.universe.api.dto.Person dto) {
        return new Person(
                dto.getEmail(),
                dto.getPhone(),
                dto.getMobilePhone(),
                dto.getStreet(),
                dto.getTown(),
                dto.getCountry(),
                dto.getZip(),
                dto.getNif(),
                OrganizationPersonDTS.T.toDomainIDs(dto.getOrganizations()),
                dto.getId(), dto.getName(), dto.getCreated(), dto.getUpdated());
    }
}
