package eu.lpinto.universe.api.dts;

import eu.lpinto.universe.persistence.entities.Employee;
import eu.lpinto.universe.persistence.entities.WorkerProfile;

/**
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
public class EmployeeDTS extends BaseDTS<Employee, eu.lpinto.universe.api.dto.Employee> {

    public static final EmployeeDTS T = new EmployeeDTS();

    @Override
    public eu.lpinto.universe.api.dto.Employee toAPI(Employee entity) {
        if (entity == null) {
            return null;

        }
        else if (entity.isFull()) {
            return new eu.lpinto.universe.api.dto.Employee(
                    entity.getPmsID(),
                    PersonDTS.id(entity.getPerson()),
                    OrganizationDTS.id(entity.getOrganization()),
                    entity.getProfile() == null ? null : entity.getProfile().ordinal(),
                    entity.getId(), entity.getName(), entity.getCreated(), entity.getUpdated());

        }
        else {
            return new eu.lpinto.universe.api.dto.Employee(
                    entity.getPmsID(),
                    PersonDTS.id(entity.getPerson()),
                    OrganizationDTS.id(entity.getOrganization()),
                    entity.getProfile() == null ? null : entity.getProfile().ordinal(),
                    entity.getId(), entity.getName(), entity.getCreated(), entity.getUpdated());
        }
    }

    @Override
    public Employee toDomain(Long id) {
        if (id == null) {
            return null;
        }

        return new Employee(id);
    }

    @Override
    public Employee toDomain(eu.lpinto.universe.api.dto.Employee dto) {
        return new Employee(
                dto.getPmsID(),
                PersonDTS.T.toDomain(dto.getPerson()),
                OrganizationDTS.T.toDomain(dto.getOrganization()),
                dto.getRole() == null ? null : WorkerProfile.values()[dto.getRole()],
                dto.getId(), dto.getName(), dto.getCreated(), dto.getUpdated());
    }
}
