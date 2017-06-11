package eu.lpinto.universe.persistence.entities;

import eu.lpinto.earth.persistence.entities.AbstractEntity;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.*;

/**
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
@Entity
@Table(name = "CLINICPERSON")
@NamedQueries({
    @NamedQuery(name = "OrganizationPerson.findByPMSOrganization", query = "SELECT cp FROM OrganizationPerson cp WHERE( cp.pmsID = :pmsID AND cp.organization.id = :organizationID)"),})
public class OrganizationPerson extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pmsID;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE) // cascade merge will update target object with this instance
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Organization organization;

    @Enumerated(EnumType.ORDINAL)
    private WorkerProfile profile;

    /*
     * Constructors
     */
    public OrganizationPerson() {
        super();
    }

    public OrganizationPerson(final Long id) {
        super(id);
    }

    public OrganizationPerson(final String pmsID, final Person person, final Organization organization, final WorkerProfile profile) {
        super();
        this.pmsID = pmsID;
        this.person = person;
        this.organization = organization;
        this.profile = profile;
    }

    public OrganizationPerson(final String pmsID, final Person person, final Organization organization, final WorkerProfile profile,
                              final Long id, final String name, final Calendar created, final Calendar updated) {
        super(id, name, created, updated);
        this.pmsID = pmsID;
        this.person = person;
        this.organization = organization;
        this.profile = profile;
    }

    /*
     * Getters/Setters
     */
    public String getPmsID() {
        return pmsID;
    }

    public void setPmsID(String pmsID) {
        this.pmsID = pmsID;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(final Organization organization) {
        assertNotNull(organization);

        this.organization = organization;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(final Person person) {
        assertNotNull(person);

        this.person = person;
    }

    public WorkerProfile getProfile() {
        return profile;
    }

    public void setProfile(final WorkerProfile profile) {
        assertNotNull(profile);

        this.profile = profile;
    }
}
