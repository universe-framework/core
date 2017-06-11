package eu.lpinto.universe.controllers;

import eu.lpinto.universe.controllers.exceptions.PermissionDeniedException;
import eu.lpinto.universe.controllers.exceptions.PreConditionException;
import eu.lpinto.universe.controllers.exceptions.UnknownIdException;
import eu.lpinto.earth.persistence.entities.AbstractEntity;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 */
public interface CrudController<E extends AbstractEntity> {

    public List<E> findAll(final Long userID) throws PermissionDeniedException;

    public List<E> find(final Long userID, final Map<String, Object> options) throws PermissionDeniedException;

    public E retrieve(final Long userID, final Long id) throws UnknownIdException, PermissionDeniedException, PreConditionException;

    public E create(final Long userID, final E entity) throws UnknownIdException, PreConditionException, PermissionDeniedException;

    public void update(final Long userID, final E entity) throws UnknownIdException, PermissionDeniedException, PreConditionException;

    public void delete(final Long userID, final Long id) throws UnknownIdException, PermissionDeniedException, PreConditionException;
}
