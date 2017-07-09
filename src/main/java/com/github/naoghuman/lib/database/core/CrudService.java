/**
 * Copyright (C) 2017 Naoghuman
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
package com.github.naoghuman.lib.database.core;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 * The {@code Interface} for the default implementation class 
 * {@link com.github.naoghuman.lib.database.internal.DefaultCrudService}.<br>
 * A common {@code Interface} for all CRUD-Component implementations. The
 * type of the entity is specified in the implementation.
 *
 * @author Naoghuman
 * @see com.github.naoghuman.lib.database.core.DatabaseFacade
 * @see com.github.naoghuman.lib.database.internal.DefaultCrudService
 */
public interface CrudService {
    
    /**
     * Start a resource transaction.
     * <p>
     * Internal following methods will be executed in following order:<br>
     * <ul>
     * <li>{@link javax.persistence.EntityTransaction#begin()}</li>
     * </ul>
     */
    public void beginTransaction();
    
    /**
     * Commits the current resource transaction, writing any unflushed changes 
     * to the database.
     * <p>
     * Internal following methods will be executed in following order:<br>
     * <ul>
     * <li>{@link javax.persistence.EntityTransaction#commit()}</li>
     * <li>{@link javax.persistence.EntityManager#clear()}</li>
     * </ul>
     */
    public void commitTransaction();
    
    /**
     * Count all entitys in the given {@code table}.
     * 
     * @param table The table which entitys should be counted.
     * @return The number of entitys in the table or {@code -1} if the table doesn't exists.
     */
    public Long count(final String table);
    
    /**
     * Makes an entity managed and persistent.<br>
     * Deletes to {@link com.github.naoghuman.lib.database.core.CrudService#create(java.lang.Object, java.lang.Boolean)}
     * with the parameter {@code isSingleTransaction == true}.
     * 
     * @param <T>
     * @param entity
     * @return 
     * @see com.github.naoghuman.lib.database.core.CrudService#create(java.lang.Object, java.lang.Boolean)
     */
    public <T> T create(final T entity);
    
    /**
     * Makes an entity managed and persistent.
     * <p>
     * Internal following methods will be executed in following order:<br>
     * <ul>
     * <li>if {@code isSingleTransaction == true} then {@link com.github.naoghuman.lib.database.core.CrudService#beginTransaction()}</li>
     * <li>{@link javax.persistence.EntityManager#persist(java.lang.Object)}</li>
     * <li>{@link javax.persistence.EntityManager#flush() }</li>
     * <li>{@link javax.persistence.EntityManager#refresh(java.lang.Object) }</li>
     * <li>if {@code isSingleTransaction == true} then {@link com.github.naoghuman.lib.database.core.CrudService#commitTransaction()}</li>
     * </ul>
     * 
     * @param <T>
     * @param entity
     * @param isSingleTransaction
     * @return 
     * @see com.github.naoghuman.lib.database.core.CrudService#beginTransaction()
     * @see com.github.naoghuman.lib.database.core.CrudService#commitTransaction()
     * @see com.github.naoghuman.lib.database.core.CrudService#create(java.lang.Object)
     */
    public <T> T create(final T entity, final Boolean isSingleTransaction);
    
    /**
     * TODO Add JavaDoc<br>
     * Deletes to {@link com.github.naoghuman.lib.database.core.CrudService#delete(java.lang.Class, java.lang.Object, java.lang.Boolean)}
     * with the parameter {@code isSingleTransaction == true}.
     * 
     * @param <T>
     * @param type
     * @param id 
     * @see com.github.naoghuman.lib.database.core.CrudService#delete(java.lang.Class, java.lang.Object, java.lang.Boolean) 
     */
    public <T> void delete(final Class<T> type, final Object id);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param <T>
     * @param type
     * @param id
     * @param isSingleTransaction 
     * @see com.github.naoghuman.lib.database.core.CrudService#delete(java.lang.Class, java.lang.Object) 
     */
    public <T> void delete(final Class<T> type, final Object id, final Boolean isSingleTransaction);
    
    /**
     * Returns the associated {@link javax.persistence.EntityManager}.
     * 
     * @return The EntityManager.
     */
    public EntityManager getEntityManager();
    
    /**
     * TODO Add JavaDoc<br>
     * Delegates to {@link com.github.naoghuman.lib.database.core.CrudService#update(java.lang.Object, java.lang.Boolean) }
     * with the parameter {@code isSingleTransaction == true}.
     * 
     * @param <T>
     * @param entity
     * @return 
     * @see com.github.naoghuman.lib.database.core.CrudService#update(java.lang.Object, java.lang.Boolean) 
     */
    public <T> T update(final T entity);
    
    /**
     * Merge the state of the given entity into the current persistence context.
     * 
     * @param <T>
     * @param entity
     * @param isSingleTransaction
     * @return 
     * @see com.github.naoghuman.lib.database.core.CrudService#update(java.lang.Object) 
     */
    public <T> T update(final T entity, final Boolean isSingleTransaction);
    
    /**
     * Find by primary key. Search for an entity of the specified class and 
     * primary key. If the entity instance is contained in the persistence 
     * context, it is returned from there.
     * 
     * @param <T>
     * @param type
     * @param id
     * @return 
     */
    public <T> T findById(final Class<T> type, final Object id);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param <T>
     * @param type
     * @param queryName
     * @return 
     */
    public <T> List<T> findByNamedQuery(final Class<T> type, final String queryName);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param <T>
     * @param type
     * @param queryName
     * @param resultLimit
     * @return 
     */
    public <T> List<T> findByNamedQuery(final Class<T> type, final String queryName, final int resultLimit);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param <T>
     * @param type
     * @param queryName
     * @param parameters
     * @return 
     */
    public <T> List<T> findByNamedQuery(final Class<T> type, final String queryName, final Map<String, Object> parameters);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param <T>
     * @param type
     * @param queryName
     * @param parameters
     * @param resultLimit
     * @return 
     */
    public <T> List<T> findByNamedQuery(final Class<T> type, final String queryName, final Map<String, Object> parameters, final int resultLimit);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param <T>
     * @param type
     * @param sql
     * @return 
     */
    public <T> List<T> findByNativeQuery(final Class<T> type, final String sql);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param <T>
     * @param type
     * @param sql
     * @param resultLimit
     * @return 
     */
    public <T> List<T> findByNativeQuery(final Class<T> type, final String sql, final int resultLimit);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param <T>
     * @param type
     * @param sql
     * @param parameters
     * @return 
     */
    public <T> List<T> findByNativeQuery(final Class<T> type, final String sql, final Map<String, Object> parameters);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param <T>
     * @param type
     * @param sql
     * @param parameters
     * @param resultLimit
     * @return 
     */
    public <T> List<T> findByNativeQuery(final Class<T> type, final String sql, final Map<String, Object> parameters, final int resultLimit);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param name 
     */
    public void shutdown(final String name);
    
}
