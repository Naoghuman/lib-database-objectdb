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
 * The {@code Interface} for the default {@code Implementation} class 
 * {@link com.github.naoghuman.lib.database.internal.DefaultCrudService}.<br>
 * A common {@code Interface} for all CRUD operations. The type of the entity is 
 * specified in the {@code Implementation}.
 *
 * @author Naoghuman
 * @see    com.github.naoghuman.lib.database.core.DatabaseFacade
 * @see    com.github.naoghuman.lib.database.internal.DefaultCrudService
 */
public interface CrudService {
    
    /**
     * Start a resource transaction.
     * <p>
     * Internal following methods in following order will be executed:<br>
     * <ul>
     * <li>{@link javax.persistence.EntityTransaction#begin()}</li>
     * </ul>
     * 
     * @see javax.persistence.EntityTransaction#begin()
     */
    public void beginTransaction();
    
    /**
     * Commits the current resource transaction, writing any unflushed changes 
     * to the database.
     * <p>
     * Internal following methods in following order will be executed:<br>
     * <ul>
     * <li>{@link javax.persistence.EntityTransaction#commit()}</li>
     * <li>{@link javax.persistence.EntityManager#clear()}</li>
     * </ul>
     * 
     * @see javax.persistence.EntityTransaction#clear()
     * @see javax.persistence.EntityTransaction#commit()
     */
    public void commitTransaction();
    
    /**
     * Counts all entitys in the given {@code table}.<br>
     * Creates the {@code sql} instruction <i>SELECT COUNT(c) FROM table c</i> which
     * will then executed with {@link javax.persistence.EntityManager#createQuery(java.lang.String, java.lang.Class)}.
     * 
     * @param  table The table which entitys should be counted.
     * @return The number of entitys in the table or {@code -1} if the table doesn't exists.
     * @see    javax.persistence.EntityManager#createQuery(java.lang.String, java.lang.Class)
     */
    public Long count(final String table);
    
    /**
     * Makes an entity managed and persistent. Synchronize the persistence context 
     * to the underlying database and refresh the state of the instance from the 
     * database, overwriting changes made to the entity, if any.<br>
     * Delegates to {@link com.github.naoghuman.lib.database.core.CrudService#create(java.lang.Object, java.lang.Boolean)}
     * with the parameter {@code isSingleTransaction == true}.
     * 
     * @param  <T> the type of the entity
     * @param  entity entity instance
     * @return a created persisted instance which the given type
     * @see    com.github.naoghuman.lib.database.core.CrudService#create(java.lang.Object, java.lang.Boolean)
     */
    public <T> T create(final T entity);
    
    /**
     * Makes an entity managed and persistent. Synchronize the persistence context 
     * to the underlying database and refresh the state of the instance from the 
     * database, overwriting changes made to the entity, if any. 
     * <p>
     * Internal following methods in following order will be executed:<br>
     * <ul>
     * <li>if {@code isSingleTransaction == true} then {@link com.github.naoghuman.lib.database.core.CrudService#beginTransaction()}</li>
     * <li>{@link javax.persistence.EntityManager#persist(java.lang.Object)}</li>
     * <li>{@link javax.persistence.EntityManager#flush() }</li>
     * <li>{@link javax.persistence.EntityManager#refresh(java.lang.Object)}</li>
     * <li>if {@code isSingleTransaction == true} then {@link com.github.naoghuman.lib.database.core.CrudService#commitTransaction()}</li>
     * </ul>
     * 
     * @param  <T>    the type of the entity
     * @param  entity entity instance
     * @param  isSingleTransaction flag if is transaction a single transaction or not.
     * @return a created persisted instance which the given type
     * @see    com.github.naoghuman.lib.database.core.CrudService#beginTransaction()
     * @see    com.github.naoghuman.lib.database.core.CrudService#commitTransaction()
     * @see    com.github.naoghuman.lib.database.core.CrudService#create(java.lang.Object)
     * @see    javax.persistence.EntityManager#flush()
     * @see    javax.persistence.EntityManager#persist(java.lang.Object)
     * @see    javax.persistence.EntityManager#refresh(java.lang.Object)
     */
    public <T> T create(final T entity, final Boolean isSingleTransaction);
    
    /**
     * Remove the entity instance from the database.<br>
     * Deletes to {@link com.github.naoghuman.lib.database.core.CrudService#delete(java.lang.Class, java.lang.Object, java.lang.Boolean)}
     * with the parameter {@code isSingleTransaction == true}.
     * 
     * @param <T> the type of the entity
     * @param type the entity class
     * @param id the primary key
     * @see   com.github.naoghuman.lib.database.core.CrudService#delete(java.lang.Class, java.lang.Object, java.lang.Boolean) 
     */
    public <T> void delete(final Class<T> type, final Object id);
    
    /**
     * Remove the entity instance from the database.<br>
     * <p>
     * Internal following methods in following order will be executed:<br>
     * <ul>
     * <li>if {@code isSingleTransaction == true} then {@link com.github.naoghuman.lib.database.core.CrudService#beginTransaction()}</li>
     * <li>{@link javax.persistence.EntityManager#getReference(java.lang.Class, java.lang.Object)}</li>
     * <li>{@link javax.persistence.EntityManager#remove(java.lang.Object)}</li>
     * <li>if {@code isSingleTransaction == true} then {@link com.github.naoghuman.lib.database.core.CrudService#commitTransaction()}</li>
     * </ul>
     * 
     * @param <T> the type of the entity
     * @param type the entity class
     * @param id the primary key
     * @param isSingleTransaction flag if is transaction a single transaction or not.
     * @see   com.github.naoghuman.lib.database.core.CrudService#delete(java.lang.Class, java.lang.Object)
     * @see   javax.persistence.EntityManager#getReference(java.lang.Class, java.lang.Object)
     * @see   javax.persistence.EntityManager#remove(java.lang.Object)
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
     * @param  <T> the type of the entity
     * @param  entity entity instance
     * @return 
     * @see    com.github.naoghuman.lib.database.core.CrudService#update(java.lang.Object, java.lang.Boolean) 
     */
    public <T> T update(final T entity);
    
    /**
     * Merge the state of the given entity into the current persistence context.
     * 
     * @param  <T> the type of the entity
     * @param  entity entity instance
     * @param  isSingleTransaction flag if is transaction a single transaction or not.
     * @return 
     * @see    com.github.naoghuman.lib.database.core.CrudService#update(java.lang.Object) 
     */
    public <T> T update(final T entity, final Boolean isSingleTransaction);
    
    /**
     * Find by primary key. Search for an entity of the specified class and 
     * primary key. If the entity instance is contained in the persistence 
     * context, it is returned from there.
     * 
     * @param  <T> the type of the entity
     * @param  type the entity class
     * @param  id the primary key
     * @return 
     */
    public <T> T findById(final Class<T> type, final Object id);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param  <T> the type of the entity
     * @param  type the entity class
     * @param  queryName
     * @return 
     */
    public <T> List<T> findByNamedQuery(final Class<T> type, final String queryName);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param  <T> the type of the entity
     * @param  type the entity class
     * @param  queryName
     * @param  resultLimit
     * @return 
     */
    public <T> List<T> findByNamedQuery(final Class<T> type, final String queryName, final int resultLimit);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param  <T> the type of the entity
     * @param  type the entity class
     * @param  queryName
     * @param  parameters
     * @return 
     */
    public <T> List<T> findByNamedQuery(final Class<T> type, final String queryName, final Map<String, Object> parameters);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param  <T> the type of the entity
     * @param  type the entity class
     * @param  queryName
     * @param  parameters
     * @param  resultLimit
     * @return 
     */
    public <T> List<T> findByNamedQuery(final Class<T> type, final String queryName, final Map<String, Object> parameters, final int resultLimit);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param  <T> the type of the entity
     * @param  type the entity class
     * @param  sql
     * @return 
     */
    public <T> List<T> findByNativeQuery(final Class<T> type, final String sql);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param  <T> the type of the entity
     * @param  type the entity class
     * @param  sql
     * @param  resultLimit
     * @return 
     */
    public <T> List<T> findByNativeQuery(final Class<T> type, final String sql, final int resultLimit);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param  <T> the type of the entity
     * @param  type the entity class
     * @param  sql
     * @param  parameters
     * @return 
     */
    public <T> List<T> findByNativeQuery(final Class<T> type, final String sql, final Map<String, Object> parameters);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param  <T> the type of the entity
     * @param  type the entity class
     * @param  sql
     * @param  parameters
     * @param  resultLimit
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
