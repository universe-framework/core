package eu.lpinto.universe.persistence.entities;

import javax.persistence.*;

/**
 *
 * @author Luis Pinto <code>- luis.pinto@petuniversal.com</code>
 */
@MappedSuperclass
public abstract class UniverseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Constructors
     */
    public UniverseEntity() {
    }

    public UniverseEntity(Long id) {
        if (id == null) {
            throw new AssertionError("Cannot instantiate an instance of " + this.getClass().getCanonicalName() + "with id [null]");
        }
        this.id = id;
    }

    /*
     * Overide
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(final Object other) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(other instanceof AbstractEntity)) {
            return false;
        }

        return this.getId() != null && this.getId().equals(((UniverseEntity) other).getId());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [id=" + getId() + "]";
    }

    /*
     * Getters / Setters
     */
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
