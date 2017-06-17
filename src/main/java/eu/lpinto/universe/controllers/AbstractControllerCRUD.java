package eu.lpinto.universe.controllers;

import eu.lpinto.universe.controllers.exceptions.PermissionDeniedException;
import eu.lpinto.universe.controllers.exceptions.PreConditionException;
import eu.lpinto.universe.controllers.exceptions.UnknownIdException;
import eu.lpinto.universe.persistence.entities.UniverseEntity;
import eu.lpinto.universe.persistence.facades.AbstractFacade;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Foundation controller for CRUD operations
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 * @param <E> Domain UniverseEntity
 */
public abstract class AbstractControllerCRUD<E extends UniverseEntity> extends AbstractController implements CrudController<E> {

    private final String entityName;

    /*
     * Constructors
     */
    public AbstractControllerCRUD(final String entityName) {
        super();
        this.entityName = entityName;
    }

    /*
     * Services
     */
    @Override
    public final List<E> findAll(final Long userID) throws PermissionDeniedException {
        return find(userID, null);
    }

    @Override
    public final List<E> find(final Long userID, final Map<String, Object> options) throws PermissionDeniedException {
        Boolean permission = assertPremissionsRead(userID, null);
        if (false == isSystemAdmin(userID) && (permission == null || false == permission)) {
            throw new PermissionDeniedException();
        }

        try {
            return doFind(options);

        } catch (RuntimeException ex) {
            throw internalError(ex);
        }
    }

    public List<E> doFind(final Map<String, Object> options) throws PermissionDeniedException {
        return getFacade().find(options);
    }

    @Override
    public final E retrieve(final Long userID, final Long id) throws UnknownIdException, PermissionDeniedException, PreConditionException {
        /*
         * Preconditions
         */
        if (id == null) {
            throw missingParameter("id");
        }

        /*
         * Body
         */
        E savedEntity;
        try {
            savedEntity = doRetrieve(id);

            if (savedEntity == null) {
                throw new UnknownIdException(entityName, id);
            }
        } catch (RuntimeException ex) {
            throw internalError(ex);
        }

        Boolean permission = assertPremissionsRead(userID, savedEntity);
        if (false == isSystemAdmin(userID) && (permission == null || false == permission)) {
            throw new PermissionDeniedException();
        }

        return savedEntity;
    }

    public E doRetrieve(final Long id) throws UnknownIdException, PermissionDeniedException, PreConditionException {
        E savedEntity;
        savedEntity = getFacade().retrieve(id);
        return savedEntity;
    }

    @Override
    public final E create(final Long userID, final E entity) throws UnknownIdException, PreConditionException, PermissionDeniedException {
        Boolean permission = assertPremissionsCreate(userID, entity);
        if (false == isSystemAdmin(userID) && (permission == null || false == permission)) {
            throw new PermissionDeniedException();
        }

        Map<String, Object> options = new HashMap<>(1);
        options.put("user", userID);

        try {
            doCreate(entity, options);
            return entity;
        } catch (RuntimeException ex) {
            throw internalError(ex);
        }
    }

    public void doCreate(final E entity, final Map<String, Object> options) throws UnknownIdException, PreConditionException, PermissionDeniedException {
        getFacade().create(entity);
    }

    @Override
    public final void update(final Long userID, final E entity) throws UnknownIdException, PermissionDeniedException, PreConditionException {
        Long id = entity.getId();
        if (id == null) {
            throw missingParameter("id");
        }

        /*
         * UniverseEntity exists
         */
        E savedEntity = getFacade().retrieve(id);
        if (savedEntity == null) {
            throw new UnknownIdException(entityName, id);
        }

        /*
         * User has permissions
         */
        Boolean permission = assertPremissionsUpdateDelete(userID, savedEntity);
        if (false == isSystemAdmin(userID) && (permission == null || false == permission)) {
            throw new PermissionDeniedException();
        }

        try {
            doUpdate(entity);
        } catch (RuntimeException ex) {

            throw internalError(ex);
        }
    }

    public void doUpdate(final E entity) {
        getFacade().edit(entity);
    }

    @Override
    public final void delete(final Long userID, final Long id) throws UnknownIdException, PermissionDeniedException, PreConditionException {
        if (id == null) {
            throw missingParameter("id");
        }

        /*
         * UniverseEntity exists
         */
        E savedEntity = getFacade().retrieve(id);
        if (savedEntity == null) {
            throw new UnknownIdException(entityName, id);
        }

        /*
         * User has permissions
         */
        Boolean permission = assertPremissionsUpdateDelete(userID, savedEntity);
        if (false == isSystemAdmin(userID) && (permission == null || false == permission)) {
            throw new PermissionDeniedException();
        }

        try {
            doDelete(savedEntity);
        } catch (RuntimeException ex) {

            throw internalError(ex);
        }
    }

    public void doDelete(E savedEntity) throws UnknownIdException, PermissionDeniedException, PreConditionException {
        getFacade().remove(savedEntity);
    }

    /*
     * Protected
     */
    protected abstract AbstractFacade<E> getFacade();

    public Boolean assertPremissionsCreate(final Long userID, final E entity) throws PermissionDeniedException {
        return true;
    }

    public Boolean assertPremissionsRead(final Long userID, final E entity) throws PermissionDeniedException {
        return true;
    }

    public Boolean assertPremissionsUpdateDelete(final Long userID, final E entity) throws PermissionDeniedException {
        return true;
    }
}
