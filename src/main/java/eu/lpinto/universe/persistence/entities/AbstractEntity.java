package eu.lpinto.universe.persistence.entities;

import java.util.Calendar;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Abstract base for all entities.
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
@MappedSuperclass
public abstract class AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(nullable = false)
    @Size(min = 1, message = "Invalid name size")
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, nullable = false)
    private Calendar created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Calendar updated;

    @Transient
    private boolean full = false;

    /*
     * Constructors
     */
    public AbstractEntity() {
    }

    public AbstractEntity(final Long id) {
        if (id == null) {
            throw new AssertionError("Cannot instantiate an instance of " + this.getClass().getCanonicalName() + "with id [null]");
        }

        this.id = id;
    }

    public AbstractEntity(final String name) {
        this.name = name;
    }

    public AbstractEntity(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public AbstractEntity(final Long id, final String name, final Calendar created, final Calendar updated) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.updated = updated;
    }

    /*
     * Overide
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(final Object other) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(other instanceof AbstractEntity)) {
            return false;
        }

        return this.id != null && this.id.equals(((AbstractEntity) other).id);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[id=" + getId() + ", name=" + getName() + "]";
    }

    /*
     * Getters/Setters
     */
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        assertNotNull(id);

        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        assertNotNull(name);

        this.name = name;
    }

    public Calendar getCreated() {
        return this.created;
    }

    public void setCreated(final Calendar created) {
        assertNotNull(created);

        this.created = created;
    }

    public Calendar getUpdated() {
        return updated;
    }

    public void setUpdated(final Calendar updated) {
        assertNotNull(updated);

        this.updated = updated;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    /*
     * Helpers
     */
    protected void assertNotNull(final List<Object> field) {
        if (field == null || field.isEmpty()) {
            throw new IllegalArgumentException("Cannot set field with null or empty List");
        }
    }

    protected void assertNotNull(final String field) {
        if (field == null || field.isEmpty()) {
            throw new IllegalArgumentException("Cannot set field with null or empty String");
        }
    }

    protected void assertNotNull(final Object field) {
        if (field == null) {
            throw new IllegalArgumentException("Cannot set field with null");
        }
    }
}
