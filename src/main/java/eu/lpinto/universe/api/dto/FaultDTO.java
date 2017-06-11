package eu.lpinto.universe.api.dto;

import java.io.Serializable;

/**
 * Encapsulates an error error.
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
public class FaultDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String error;
    private String code;

    /*
     * Constructors
     */
    public FaultDTO() {
        super();
    }

    public FaultDTO(final String message) {
        this.error = message;
    }

    public FaultDTO(final String code, final String error) {
        this.error = error;
        this.code = code;
    }

    /*
     * Override
     */
    @Override
    public String toString() {
        return "Faulty usage of the system [" + this.error + "].";
    }

    /*
     * Getters
     */
    public String getError() {
        return error;
    }
}
