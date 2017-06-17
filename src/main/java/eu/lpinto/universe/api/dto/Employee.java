package eu.lpinto.universe.api.dto;

import eu.lpinto.universe.persistence.entities.WorkerProfile;
import java.util.Calendar;

/**
 * Represents the relation between and organization and a person.
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
public class Employee extends AbstractEntityDTO {

    private static final long serialVersionUID = 1L;

    private String pmsID;
    private Long person;
    private Long organization;
    private Integer role;

    /*
     * Constructors
     */
    public Employee() {
    }

    public Employee(final String pmsID, final Long person, final Long organization, final Integer role) {
        this.pmsID = pmsID;
        this.person = person;
        this.organization = organization;
        this.role = role;
    }

    public Employee(final String pmsID, final Long person, final Long organization, final Integer role,
                              final Long id, final String name, final Calendar created, final Calendar updated) {
        super(id, name, created, updated);
        this.pmsID = pmsID;
        this.person = person;
        this.organization = organization;
        this.role = role;
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

    public Long getOrganization() {
        return organization;
    }

    public void setOrganization(final Long organization) {
        this.organization = organization;
    }

    public Long getPerson() {
        return person;
    }

    public void setPerson(final Long person) {
        this.person = person;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(final Integer role) {
        this.role = role;
    }

    public WorkerProfile getWorkerProfile(int role) {
        WorkerProfile profile = WorkerProfile.values()[role];

        return profile;
    }
}
