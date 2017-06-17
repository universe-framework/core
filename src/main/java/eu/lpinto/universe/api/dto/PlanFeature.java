package eu.lpinto.universe.api.dto;

import java.util.Calendar;

/**
 * Represents the relation between an feature and a owner.
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
public class PlanFeature extends AbstractDTO {

    private static final long serialVersionUID = 1L;

    private Integer value;
    private Long plan;
    private Long feature;

    /*
     * Constructors
     */
    public PlanFeature() {
        super();
    }

    public PlanFeature(Long id) {
        super(id);
    }

    public PlanFeature(final Integer value, final Long plan, final Long feature,
                       final Long id, final String name, final Calendar created, final Calendar updated) {
        super(id, name, created, updated);
        this.value = value;
        this.plan = plan;
        this.feature = feature;
    }

    /*
     * Getters/Setters
     */
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Long getFeature() {
        return feature;
    }

    public void setFeature(final Long feature) {
        this.feature = feature;
    }

    public Long getPlan() {
        return plan;
    }

    public void setPlan(final Long plan) {
        this.plan = plan;
    }
}
