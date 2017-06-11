package eu.lpinto.universe.persistence.facades;

import eu.lpinto.earth.persistence.facades.AbstractFacade;
import eu.lpinto.universe.persistence.entities.OrganizationPerson;
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
 * JPA facade for person entity.
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
@Stateless
public class PersonFacade extends AbstractFacade<Person> {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private OrganizationFacade organizationFacade;

    /*
     * Constructors
     */
    public PersonFacade() {
        super(Person.class);
    }

    /*
     * CRUD
     */
    @Override
    public List<Person> find(Map<String, Object> options) {
        if (options != null) {
            if (options.containsKey("clinic")) {
                if (options.containsKey("profile")) {
                    return getByOrganizationAndProfiles((Long) options.get("clinic"), options.get("profile").toString());

                }
                return getByOrganization((Long) options.get("clinic"));
            }
        }

        return super.findAll();
    }

    @Override
    public Person retrieve(Long id) {
        Person result = super.retrieve(id);

        result.setOrganizations(getEntityManager()
                .createQuery("select e from OrganizationPerson e left join fetch e.person"
                             + " where e.person.id = :id", OrganizationPerson.class).setParameter("id", id).getResultList());

        result.setFull(true);

        return result;
    }

    @Override
    public void create(final Person newPerson) {

        List<Person> savedPersons;

        if (newPerson.getPhone() != null && newPerson.getMobilePhone() != null) {
            if (newPerson.getPhone().equals(newPerson.getMobilePhone())) {
                throw new IllegalArgumentException("MobilePhone equals Phone");

            }
        }

        try {
            savedPersons = em.createNamedQuery("Person.findAllPhones").getResultList();
        }
        catch (Exception ex) {
            throw new IllegalArgumentException("Error in findAllPhones querry");
        }

        if (newPerson.getMobilePhone() != null) {
            if (savedPersons != null) {
                for (int i = 0; i < savedPersons.size(); i++) {
                    if (savedPersons.get(i) != null) {
                        if (savedPersons.get(i).getPhone() != null) {
                            if (savedPersons.get(i).getPhone().contains(newPerson.getMobilePhone())) {
                                throw new IllegalArgumentException("The number in the MobilePhone already exists");
                            }
                        }

                    }
                }
            }

            if (newPerson.getPhone() != null) {
                for (int i = 0; i < savedPersons.size(); i++) {
                    if (savedPersons.get(i) != null) {
                        if (savedPersons.get(i).getMobilePhone() != null) {
                            if (savedPersons.get(i).getMobilePhone().contains(newPerson.getPhone())) {
                                throw new IllegalArgumentException("The number in the Phone already exists");
                            }
                        }

                    }
                }
            }
        }

        super.create(newPerson);
    }

    @Override
    public void edit(final Person newPerson) {
        Person savedPerson = retrieve(newPerson.getId());

        if (savedPerson.getOrganizations() != null && !savedPerson.getOrganizations().isEmpty()) {
            newPerson.setOrganizations(savedPerson.getOrganizations());
        }

        /*
         * Update Person
         */
        super.edit(newPerson);
    }

    public List<Person> phones() {
        List<Person> savedPersons;

        try {
            savedPersons = em.createNamedQuery("Person.findAllPhones").getResultList();
            if (savedPersons == null) {
                throw new IllegalArgumentException("No phones in the DataBase");
            }
            return savedPersons;
        }
        catch (Exception ex) {
            throw new IllegalArgumentException("Error in findAllPhones querry");
        }
    }

    /*
     * Helpers
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /*
     * Helpers
     */
    private List<Person> getByOrganization(final Long clinicID) {
        try {
            List<Person> clinicPeople = getEntityManager().createQuery(
                    "SELECT p FROM Person p inner join p.clinicJobs cp WHERE :clinicID = cp.clinic.id", Person.class)
                    .setParameter("clinicID", clinicID)
                    .getResultList();

            return clinicPeople;
        }
        catch (NoResultException ex) {
            return null;
        }
    }

    private List<Person> getByOrganizationAndProfiles(final Long clinicID, final String profile) {
        List<WorkerProfile> profiles;
        if ("staff".equals(profile)) {
            profiles = Arrays.asList(WorkerProfile.ADMIN, WorkerProfile.DOCTOR, WorkerProfile.NURSE, WorkerProfile.EMPLOYEE);

        }
        else if ("client".equals(profile)) {
            profiles = Arrays.asList(WorkerProfile.CANDIDATE);

        }
        else {
            profiles = new ArrayList<>(0);
        }

        try {
            List<Person> clinicPeople = getEntityManager().createQuery(
                    "SELECT p FROM Person p inner join p.clinicJobs cp WHERE :clinicID = cp.clinic.id AND cp.profile in :profiles", Person.class)
                    .setParameter("clinicID", clinicID)
                    .setParameter("profiles", profiles)
                    .getResultList();

            return clinicPeople;
        }
        catch (NoResultException ex) {
            return null;
        }
    }
}
