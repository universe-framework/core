package eu.lpinto.universe.persistence.facades;

import eu.lpinto.earth.persistence.entities.AbstractEntity;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 * Foundation for all facades.
 *
 * @author Luis Pinto <code>- mail@lpinto.eu</code>
 * @param <T> Type of entity to be managed.
 */
public abstract class AbstractFacade<T> {
    private final Class<T> entityClass;

    /*
     * Constructors
     */
    public AbstractFacade(final Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /*
     * DAO
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery<T> cq = getEntityManager().getCriteriaBuilder().createQuery(entityClass);
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> find(final Map<String, Object> options) {
        return findAll();
    }

    public T retrieve(final Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot perform a retrieve retrieve for " + this.entityClass.getCanonicalName() + " with id [null]");
        }

        return getEntityManager().find(entityClass, id);
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery<T> cq = getEntityManager().getCriteriaBuilder().createQuery(entityClass);
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public Long count() {
        javax.persistence.criteria.CriteriaQuery<Long> cq = getEntityManager().getCriteriaBuilder().createQuery(Long.class);
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return (Long) q.getSingleResult();
    }

    public void create(final T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Cannot create a new " + this.entityClass.getCanonicalName() + " with [null] object");
        }

        if (entity instanceof AbstractEntity) {
            create((AbstractEntity) entity, null);

        }
        else {
            getEntityManager().persist(entity);
        }

        getEntityManager().flush();
    }

    public void create(final AbstractEntity entity, final Calendar now) {
        if (entity == null) {
            throw new IllegalArgumentException("Cannot create a new " + this.entityClass.getCanonicalName() + " with [null] object");
        }

        Calendar newNow;
        if (now == null) {
            newNow = new GregorianCalendar();

        }
        else {
            newNow = now;
        }

        entity.setCreated(newNow);
        entity.setUpdated(newNow);

        getEntityManager().persist(entity);

        getEntityManager().flush();
    }

    public void edit(final T entity) {
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }

    public void remove(final T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /*
     * Getters / Setters
     */
    protected abstract EntityManager getEntityManager();

    protected Class<T> getEntityClass() {
        return entityClass;
    }
}
