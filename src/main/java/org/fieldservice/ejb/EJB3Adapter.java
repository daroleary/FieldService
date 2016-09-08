package org.fieldservice.ejb;

import com.google.common.collect.ImmutableList;
import org.fieldservice.model.LongPk;
import org.fieldservice.model.RootEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

public abstract class EJB3Adapter<TPK extends LongPk,
        TEntity extends RootEntity<TPK>> {

    @PersistenceContext
    protected EntityManager _em;

    private final Class<TEntity> _entityClass;

    public EJB3Adapter(Class<TEntity> entityClass) {
        _entityClass = entityClass;
    }

    public void create(TEntity entity) {

        _em.persist(entity);
        _em.flush();
    }

    public void edit(TEntity entity) {
        _em.merge(entity);
        _em.flush();
    }

    public void remove(TEntity entity) {
        _em.remove(_em.merge(entity));
    }

    public TEntity find(TPK id) {
        return _em.find(_entityClass, id);
    }

    @SuppressWarnings("unchecked")
    public ImmutableList<TEntity> getAll() {
        CriteriaQuery query = _em.getCriteriaBuilder().createQuery();
        query.select(query.from(_entityClass));
        return ImmutableList.<TEntity>copyOf(_em.createQuery(query).getResultList());
    }

    public TEntity getSingleResult(Query query) {
        TEntity entity;
        try {
            //noinspection unchecked
            entity = (TEntity) query.getSingleResult();
        } catch (NoResultException ex) {
            entity = null;
        }

        return entity;
    }
}
