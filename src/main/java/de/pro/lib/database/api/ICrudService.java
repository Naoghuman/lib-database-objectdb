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
     * TODO Add JavaDoc
     */
    public void beginTransaction();
    
    /**
     * TODO Add JavaDoc
     */
    public void commitTransaction();
    
    /**
     * TODO Add JavaDoc
     * 
     * @param <T>
     * @param entity
     * @return 
     */
    public <T> T create(T entity);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param <T>
     * @param entity
     * @param isSingleTransaction
     * @return 
     */
    public <T> T create(T entity, Boolean isSingleTransaction);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param <T>
     * @param type
     * @param id 
     */
    public <T> void delete(Class<T> type, Object id);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param <T>
     * @param type
     * @param id
     * @param isSingleTransaction 
     */
    public <T> void delete(Class<T> type, Object id, Boolean isSingleTransaction);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param <T>
     * @param entity
     * @return 
     */
    public <T> T update(T entity);
    
    /**
     * TODO Add JavaDoc
     * 
     * @param <T>
     * @param entity
     * @param isSingleTransaction
     * @return 
     */
    public <T> T update(T entity, Boolean isSingleTransaction);
    
    /**
     * TODO Add JavaDoc
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
    
}
