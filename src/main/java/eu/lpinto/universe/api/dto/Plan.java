package eu.lpinto.universe.api.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Plan DTO - Data Transformation Object
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
public class Plan extends AbstractDTO implements Serializable {

    public static final long serialVersionUID = 1L;

    private List<Long> features;

    /*
     * Constructors
     */
    public Plan() {
        super();
    }

    public Plan(final Long id) {
        super(id);
    }

    public Plan(final List<Long> features,
                final Long id, final String name, final Calendar created, final Calendar updated) {
        super(id, name, created, updated);
        this.features = features;
    }

    /*
     * Getters/Setters
     */
    public List<Long> getFeatures() {
        return features;
    }

    public void setFeatures(final List<Long> features) {
        this.features = features;
    }
}
