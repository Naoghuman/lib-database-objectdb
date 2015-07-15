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
package de.pro.lib.database.api;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 * The <code>Interface</code> for the class {@link de.pro.lib.database.CrudService}.<br />
 * A common <code>Interface</code> for all CRUD-Component implementations. The
 * type of the entity is specified in the implementation.
 *
 * @author PRo
 * @see de.pro.lib.database.CrudService
 * @see de.pro.lib.database.api.DatabaseFacade
 */
public interface ICrudService {
    
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
     * Commit the current resource transaction, writing any unflushed changes 
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
     * Count all entitys in the given table.
     * 
     * @param table The table which entitys should be counted.
     * @return The number of entitys in the table or -1 if the table doesn't exists.
     */
    public Long count(String table);
    
    /**
     * Make an entity managed and persistent.<br>
     * Deletes to {@link ICrudService#create(java.lang.Object, java.lang.Boolean)}
     * with the parameter <code>isSingleTransaction = true</code>.
     * 
     * @param <T>
     * @param entity
     * @return 
     * @see ICrudService#create(java.lang.Object, java.lang.Boolean)
     */
    public <T> T create(T entity);
    
    /**
     * Make an entity managed and persistent.
     * <p>
     * Internal following methods will be executed in following order:<br>
     * <ul>
     * <li>if <code>isSingleTransaction == true</code> then {@link de.pro.lib.database.CrudService#beginTransaction()}</li>
     * <li>{@link javax.persistence.EntityManager#persist(java.lang.Object)}</li>
     * <li>{@link javax.persistence.EntityManager#flush() }</li>
     * <li>{@link javax.persistence.EntityManager#refresh(java.lang.Object) }</li>
     * <li>if <code>isSingleTransaction == true</code> then {@link de.pro.lib.database.CrudService#commitTransaction()}</li>
     * </ul>
     * 
     * @param <T>
     * @param entity
     * @param isSingleTransaction
     * @return 
     * @see ICrudService#create(java.lang.Object) 
     */
    public <T> T create(T entity, Boolean isSingleTransaction);
    
    /**
     * TODO Add JavaDoc<br>
     * Deletes to {@link ICrudService#delete(java.lang.Class, java.lang.Object, java.lang.Boolean)}
     * with the parameter <code>isSingleTransaction = true</code>.
     * 
     * @param <T>
     * @param type
     * @param id 
     * @see ICrudService#delete(java.lang.Class, java.lang.Object, java.lang.Boolean) 
     */
    public <T> void delete(Class<T> type, Object id);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param <T>
     * @param type
     * @param id
     * @param isSingleTransaction 
     * @see ICrudService#delete(java.lang.Class, java.lang.Object) 
     */
    public <T> void delete(Class<T> type, Object id, Boolean isSingleTransaction);
    
    /**
     * Returns the associated {@link javax.persistence.EntityManager}.
     * 
     * @return The EntityManager.
     */
    public EntityManager getEntityManager();
    
    /**
     * TODO Add JavaDoc<br>
     * Deletes to {@link ICrudService#update(java.lang.Object, java.lang.Boolean) }
     * with the parameter <code>isSingleTransaction = true</code>.
     * 
     * @param <T>
     * @param entity
     * @return 
     * @see ICrudService#update(java.lang.Object, java.lang.Boolean) 
     */
    public <T> T update(T entity);
    
    /**
     * Merge the state of the given entity into the current persistence context.
     * 
     * @param <T>
     * @param entity
     * @param isSingleTransaction
     * @return 
     * @see ICrudService#update(java.lang.Object) 
     */
    public <T> T update(T entity, Boolean isSingleTransaction);
    
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
    public <T> T findById(Class<T> type, Object id);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param <T>
     * @param type
     * @param queryName
     * @return 
     */
    public <T> List<T> findByNamedQuery(Class<T> type, String queryName);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param <T>
     * @param type
     * @param queryName
     * @param resultLimit
     * @return 
     */
    public <T> List<T> findByNamedQuery(Class<T> type, String queryName, int resultLimit);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param <T>
     * @param type
     * @param queryName
     * @param parameters
     * @return 
     */
    public <T> List<T> findByNamedQuery(Class<T> type, String queryName, Map<String, Object> parameters);
    
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
    public <T> List<T> findByNamedQuery(Class<T> type, String queryName, Map<String, Object> parameters, int resultLimit);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param <T>
     * @param type
     * @param sql
     * @return 
     */
    public <T> List<T> findByNativeQuery(Class<T> type, String sql);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param <T>
     * @param type
     * @param sql
     * @param resultLimit
     * @return 
     */
    public <T> List<T> findByNativeQuery(Class<T> type, String sql, int resultLimit);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param <T>
     * @param type
     * @param sql
     * @param parameters
     * @return 
     */
    public <T> List<T> findByNativeQuery(Class<T> type, String sql, Map<String, Object> parameters);
    
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
    public <T> List<T> findByNativeQuery(Class<T> type, String sql, Map<String, Object> parameters, int resultLimit);
    
    public void shutdown(String name);
    
}
