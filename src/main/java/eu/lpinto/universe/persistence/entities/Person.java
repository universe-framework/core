package eu.lpinto.universe.persistence.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Pattern;

/**
 * A person is a real person. This means it can represent an application user an
 * animal owner or shelter worker...
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
@Entity
@Table(name = "Person")

@NamedQueries({
    @NamedQuery(name = "Person.findAllPhones", query = "SELECT phone FROM Person phone"),})

public class Person extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(unique = true)
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
             message = "Invalid email")
    private String email;

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String mobilePhone;

    private String street;

    private String town;

    private String country;

    private String zip;

    @Column(unique = true)
    private String nif;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<OrganizationPerson> organizations;

    /*
     * Constructors
     */
    public Person() {
        super();
    }

    public Person(final Long id) {
        super(id);
    }

    public Person(String name) {
        super(name);
    }

    public Person(final String email, final String phone, final String mobilePhone, final String street, final String town, final String country, final String zip, final String nif,
                  final List<OrganizationPerson> organizations,
                  final Long id, final String name, final Calendar created, final Calendar updated) {
        super(id, name, created, updated);
        this.email = email;
        this.phone = phone;
        this.mobilePhone = mobilePhone;
        this.street = street;
        this.town = town;
        this.country = country;
        this.zip = zip;
        this.nif = nif;
        this.organizations = organizations;
    }

    /*
     * Public
     */
    public boolean hasOrganization(final long clinicID) {
        if (this.organizations == null || this.organizations.isEmpty()) {
            return false;
        }

        for (OrganizationPerson clinicPerson : this.organizations) {
            if (clinicPerson.getOrganization().getId().equals(clinicID)) {
                return true;
            }
        }

        return false;
    }

    /*
     * Getters/Setters
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public List<Organization> getOrganization() {
        if (this.organizations == null) {
            return new ArrayList<>(0);
        }
        else {
            List result = new ArrayList(organizations.size());
            for (OrganizationPerson cp : organizations) {
                result.add(cp.getOrganization());
            }
            return result;
        }
    }

    public List<OrganizationPerson> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<OrganizationPerson> clinics) {
        this.organizations = clinics;
    }
}
