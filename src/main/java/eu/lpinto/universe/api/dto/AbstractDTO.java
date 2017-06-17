package eu.lpinto.universe.api.dto;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Base entity, encapsulates the common data between all entities.
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
public abstract class AbstractDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Calendar created;
    private Calendar updated;

    /*
     * Constructors
     */
    protected AbstractDTO() {
    }

    protected AbstractDTO(final Long id) {
        this.id = id;
    }

    protected AbstractDTO(final Long id, final String name, final Calendar created, final Calendar updated) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.updated = updated;
    }

    /*
     * Getters/Setters
     */
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(final Calendar created) {
        this.created = created;
    }

    public Calendar getUpdated() {
        return updated;
    }

    public void setUpdated(final Calendar updated) {
        this.updated = updated;
    }

}
