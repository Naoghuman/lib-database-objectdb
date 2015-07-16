/*
 * Copyright (C) 2014 PRo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.pro.lib.database;

import de.pro.lib.database.api.ICrudService;
import de.pro.lib.logger.api.LoggerFacade;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * The implementation from the Interface {@link de.pro.lib.database.api.ICrudService}.<br />
 * Access to this class is over the facade {@link de.pro.lib.database.api.DatabaseFacade}.
 * 
 * @author PRo
 * @see de.pro.lib.database.api.ICrudService
 * @see de.pro.lib.database.api.DatabaseFacade
 */
public final class CrudService implements ICrudService {
    
    private EntityManager entityManager = null;
    
    /**
     * Constructor for the class <code>CrudService</code>.
     * 
     * @param entityManager The {@link javax.persistence.EntityManager}.
     * @see de.pro.lib.database.api.ICrudService
     * @see de.pro.lib.database.api.DatabaseFacade
     */
    public CrudService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    @Override
    public void commitTransaction() {
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    @Override
    public Long count(String table) {
        LoggerFacade.getDefault().own(this.getClass(), "Count all entitys from table: " + table); // NOI18N
        
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(c) FROM "); // NOI18N
        sql.append(table);
        sql.append(" c"); // NOI18N
        
        TypedQuery<Long> query;
        try {
           query = entityManager.createQuery(sql.toString(), Long.class);
           return query.getSingleResult();
        } catch (Exception e) {
        }
        
        return -1L;
    }

    @Override
    public <T> T create(T entity) {
        return this.create(entity, Boolean.TRUE);
    }

    @Override
    public <T> T create(T entity, Boolean isSingleTransaction) {
        LoggerFacade.getDefault().own(this.getClass(), "Create entity from type: " // NOI18N
                + entity.getClass().getSimpleName()
                + " with single transaction: " + isSingleTransaction); // NOI18N
        
        if (isSingleTransaction) {
            this.beginTransaction();
        }
        
        entityManager.persist(entity);
        entityManager.flush();
        entityManager.refresh(entity);
        
        if (isSingleTransaction) {
            this.commitTransaction();
        }
        
        return entity;
    }

    @Override
    public <T> void delete(Class<T> type, Object id) {
        this.delete(type, id, Boolean.TRUE);
    }

    @Override
    public <T> void delete(Class<T> type, Object id, Boolean isSingleTransaction) {
        LoggerFacade.getDefault().own(this.getClass(), "Delete entity(id=" // NOI18N
                + (Long) id + ") from type: " // NOI18N
                + type.getClass().getSimpleName()
                + " with single transaction: " + isSingleTransaction); // NOI18N
        
        if (isSingleTransaction) {
            this.beginTransaction();
        }
        
        final Object ref = entityManager.getReference(type, id);
        entityManager.remove(ref);
        
        if (isSingleTransaction) {
            this.commitTransaction();
        }
    }

    @Override
    public <T> T update(T entity) {
        return this.update(entity, Boolean.TRUE);
    }

    @Override
    public <T> T update(T entity, Boolean isSingleTransaction) {
        LoggerFacade.getDefault().own(this.getClass(), "Update entity from type: " // NOI18N
                + entity.getClass().getSimpleName()
                + " with single transaction: " + isSingleTransaction); // NOI18N);
        
        if (isSingleTransaction) {
            this.beginTransaction();
        }
        
        final Object ref = entityManager.merge(entity);
        
        if (isSingleTransaction) {
            this.commitTransaction();
        }
        
        return (T) ref;
    }

    @Override
    public <T> T findById(Class<T> type, Object id) {
        LoggerFacade.getDefault().own(this.getClass(), "Find entity(" // NOI18N
                + (Long) id + ") from type: " // NOI18N
                + type.getClass().getSimpleName());
        
        return (T) entityManager.find(type, id);
    }

    @Override
    public <T> List<T> findByNamedQuery(Class<T> type, String queryName) {
        LoggerFacade.getDefault().own(this.getClass(), "Find by named query: " // NOI18N
                + queryName);
        
        return entityManager
                .createNamedQuery(queryName, type)
                .getResultList();
    }

    @Override
    public <T> List<T> findByNamedQuery(Class<T> type, String queryName, int resultLimit) {
        LoggerFacade.getDefault().own(this.getClass(), "Find by named query: " // NOI18N
                + queryName + " with result limit: " + resultLimit); // NOI18N
        
        return entityManager
                .createNamedQuery(queryName, type)
                .setMaxResults(resultLimit)
                .getResultList();
    }

    @Override
    public <T> List<T> findByNamedQuery(Class<T> type, String queryName, Map<String, Object> parameters) {
        return this.findByNamedQuery(type, queryName, parameters, 0);
    }

    @Override
    public <T> List<T> findByNamedQuery(Class<T> type, String queryName, Map<String, Object> parameters, int resultLimit) {
        LoggerFacade.getDefault().own(this.getClass(), "Find by named query: " // NOI18N
                + queryName + " with result limit: " + resultLimit // NOI18N
                + " and with additional parameter."); // NOI18N
        
        final Set<Entry<String, Object>> entrySet = parameters.entrySet();
        final TypedQuery<T> query = entityManager.createNamedQuery(queryName, type);
        if (resultLimit > 0) {
            query.setMaxResults(resultLimit);
        }
        
        for (Entry<String, Object> entry : entrySet) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        
        return query.getResultList();
    }

    @Override
    public <T> List<T> findByNativeQuery(Class<T> type, String sql) {
        LoggerFacade.getDefault().own(this.getClass(), "Find by native query: " // NOI18N
                + sql + " with type: " + type.getClass().getSimpleName()); // NOI18N
        
        return entityManager
                .createNativeQuery(sql, type)
                .getResultList();
    }

    @Override
    public <T> List<T> findByNativeQuery(Class<T> type, String sql, int resultLimit) {
        LoggerFacade.getDefault().own(this.getClass(), "Find by native query: " // NOI18N
                + sql + " with type: " + type.getClass().getSimpleName() // NOI18N
                + " and with result limit: " + resultLimit); // NOI18N
        
        return entityManager
                .createNativeQuery(sql, type)
                .setMaxResults(resultLimit)
                .getResultList();
    }

    @Override
    public <T> List<T> findByNativeQuery(Class<T> type, String sql, Map<String, Object> parameters) {
        return this.findByNativeQuery(type, sql, parameters, 0);
    }

    @Override
    public <T> List<T> findByNativeQuery(Class<T> type, String sql, Map<String, Object> parameters, int resultLimit) {
        LoggerFacade.getDefault().own(this.getClass(), "Find by native query: " // NOI18N
                + sql + " with type: " + type.getClass().getSimpleName() // NOI18N
                + " and with result limit: " + resultLimit // NOI18N
                + " and with additional parameter."); // NOI18N
        
        final Set<Entry<String, Object>> entrySet = parameters.entrySet();
        final Query query = entityManager.createNativeQuery(sql, type);
        if (resultLimit > 0) {
            query.setMaxResults(resultLimit);
        }
        
        for (Entry<String, Object> entry : entrySet) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        
        return query.getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void shutdown(String name) {
        LoggerFacade.getDefault().own(this.getClass(), "Shutdown EntityManager: " + name); // NOI18N
        
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
            entityManager = null;
        }
    }
    
}
