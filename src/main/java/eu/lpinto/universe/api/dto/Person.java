package eu.lpinto.universe.api.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
public class Person extends AbstractDTO implements Serializable {

    public static final long serialVersionUID = 1L;
    private String email;
    private String phone;
    private String mobilePhone;
    private String street;
    private String town;
    private String country;
    private String zip;
    private String nif;

    private List<Long> organizations;

    /*
     * Constructors
     */
    public Person() {
        super();
    }

    public Person(final Long id) {
        super(id);
    }

    public Person(final String email, final String phone, final String mobilePhone, final String street, final String town, final String country, final String zip, final String nif,
                  final List<Long> organizations,
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
     * Getters
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

    public List<Long> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Long> organizations) {
        this.organizations = organizations;
    }
}
