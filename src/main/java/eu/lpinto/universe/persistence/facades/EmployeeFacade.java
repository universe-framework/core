package eu.lpinto.universe.persistence.facades;

import eu.lpinto.universe.persistence.entities.Organization;
import eu.lpinto.universe.persistence.entities.Employee;
import eu.lpinto.universe.persistence.entities.Person;
import eu.lpinto.universe.persistence.entities.WorkerProfile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * EJB facade for shelter-people relation.
 *
 * @author Luis Pinto <code>- luis.pinto@petuniversal.com</code>
 */
@Stateless
public class EmployeeFacade extends AbstractFacade<Employee> {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private OrganizationFacade organizationFacade;

    @EJB
    private PersonFacade personFacade;

    /*
     * Constructors
     */
    public EmployeeFacade() {
        super(Employee.class);
    }

    /*
     * CRUD
     */
    @Override
    public List<Employee> find(Map<String, Object> options) {
        if (options != null) {
            if (options.containsKey("organization")) {
                if (options.containsKey("pmsID")) {
                    return getByOrganizationAndPmsID((Long) options.get("organization"), options.get("pmsID").toString());

                } else if (options.containsKey("profile")) {
                    return getByOrganizationAndProfiles((Long) options.get("organization"), options.get("profile").toString());

                } else if (options.containsKey("person")) {
                    return getByOrganizationAndPerson((Long) options.get("organization"), (Long) options.get("person"));

                } else {
                    return getByOrganization((Long) options.get("organization"));
                }
            }
        }

        return super.findAll();
    }

    public Employee retrieve(final Long organizationID, final Long personID) {
        try {
            Employee organizationPeople = getEntityManager().createQuery("SELECT cp FROM Employee cp "
                    + "WHERE cp.organization.id = :organizationID AND cp.person.id = :personID", Employee.class)
                    .setParameter("organizationID", organizationID)
                    .setParameter("PpersonID", personID)
                    .getSingleResult();

            return organizationPeople;
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    @Override
    public void create(Employee entity
    ) {
        Long otherPersonID = entity.getPerson().getId();
        Long organizationID = entity.getOrganization().getId();

        Person savedPerson = personFacade.retrieve(otherPersonID);
        Organization savedOrganization = organizationFacade.retrieve(organizationID);

        if (savedPerson == null) {
            throw new IllegalArgumentException("There is no Person with that id");
        }

        if (savedOrganization == null) {
            throw new IllegalArgumentException("There is no Organization with that id");
        }

        entity.setName(savedOrganization.getName() + "_" + savedPerson.getName());

        super.create(entity);
    }

    @Override
    public void edit(final Employee newEmployee
    ) {
        Employee savedEmployee = new Employee();
        Organization organization = new Organization();
        Person person = new Person();

        if (newEmployee.getOrganization() != null) {
            if (newEmployee.getOrganization().getId() != null) {
                Long organizationID = newEmployee.getOrganization().getId();
                organization = organizationFacade.retrieve(organizationID);
            }
        }

        if (newEmployee.getPerson() != null) {
            if (newEmployee.getPerson().getId() != null) {
                Long personID = newEmployee.getPerson().getId();
                person = personFacade.retrieve(personID);
            }
        }

        if (newEmployee.getId() != null) {
            savedEmployee = retrieve(newEmployee.getId());
        }

        if (savedEmployee == null) {
            throw new IllegalArgumentException("There is no Employee with that id");
        }
        if (organization == null) {
            throw new IllegalArgumentException("There is no Organization with that id");
        }
        if (person == null) {
            throw new IllegalArgumentException("There is no Person with that id");
        }

        if (savedEmployee.getOrganization() != null) {
            newEmployee.setOrganization(savedEmployee.getOrganization());
        }

        if (savedEmployee.getPerson() != null) {
            newEmployee.setPerson(savedEmployee.getPerson());
        }

        //The code below is to get the name of the employee with that id since the name doesnt come down
        if (savedEmployee.getName() != null) {
            String name = savedEmployee.getName();
            newEmployee.setName(name);
        }

        super.edit(newEmployee);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /*
     * Helpers
     */
    private List<Employee> getByOrganization(final Long organizationID) {
        try {
            List<Employee> organizationPeople = getEntityManager().createQuery("SELECT cp FROM Employee cp WHERE cp.organization.id = :organizationID", Employee.class)
                    .setParameter("organizationID", organizationID)
                    .getResultList();

            return organizationPeople;
        } catch (NoResultException ex) {
            return null;
        }
    }

    private List<Employee> getByOrganizationAndProfiles(final Long organizationID, final String profile) {
        List<WorkerProfile> profiles;
        if ("staff".equals(profile)) {
            profiles = Arrays.asList(WorkerProfile.ADMIN, WorkerProfile.DOCTOR, WorkerProfile.NURSE, WorkerProfile.EMPLOYEE);

        } else if ("client".equals(profile)) {
            profiles = Arrays.asList(WorkerProfile.CANDIDATE);

        } else {
            profiles = new ArrayList<>(0);
        }

        try {
            List<Employee> organizationPeople = getEntityManager().createQuery("SELECT cp FROM Employee cp WHERE  cp.organization.id = :organizationID AND cp.profile in :profiles", Employee.class)
                    .setParameter("organizationID", organizationID)
                    .setParameter("profiles", profiles)
                    .getResultList();

            return organizationPeople;
        } catch (NoResultException ex) {
            return null;
        }
    }

    private List<Employee> getByOrganizationAndPerson(final Long organizationID, final Long personID) {
        try {
            List<Employee> organizationPeople = getEntityManager().createQuery("SELECT cp FROM Employee cp WHERE  cp.organization.id = :organizationID AND cp.person.id = :personID", Employee.class)
                    .setParameter("organizationID", organizationID)
                    .setParameter("personID", personID)
                    .getResultList();

            return organizationPeople;
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<Employee> getByOrganizationAndPmsID(final Long organizationID, final String pmsID) {
        try {
            List<Employee> organizationPeople = getEntityManager().createQuery("SELECT cp FROM Employee cp WHERE (cp.pmsID = :pmsID AND cp.organization.id = :organizationID)", Employee.class)
                    .setParameter("organizationID", organizationID)
                    .setParameter("pmsID", pmsID)
                    .getResultList();

            return organizationPeople;
        } catch (NoResultException ex) {
            return null;
        }
    }
}
