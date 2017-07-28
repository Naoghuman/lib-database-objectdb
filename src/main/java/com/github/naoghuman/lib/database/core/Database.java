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

import javax.persistence.EntityManager;

/**
 * The {@code Interface} for the {@code Implementation} {@code Class} 
 * {@link com.github.naoghuman.lib.database.internal.DefaultDatabase}.<br>
 * Over the facade {@link com.github.naoghuman.lib.database.core.DatabaseFacade} 
 * the developer can access to the default {@code Implementation} methods from 
 * this {@code Interface}.
 *
 * @author Naoghuman
 * @see    com.github.naoghuman.lib.database.core.DatabaseFacade
 * @see    com.github.naoghuman.lib.database.internal.DefaultDatabase
 */
public interface Database {
    
    /**
     * Allowed the developer to drop the defined database.<br>
     * This method can be used for testing purpose.
     * 
     * @param database The database which should be dropped.
     */
    public void drop(final String database);
    
    /**
     * Returns a {@link com.github.naoghuman.lib.database.core.CrudService} with 
     * the name {@code DEFAULT} which allowed all sql operations.
     * 
     * @return The {@code CrudService}.
     * @see    com.github.naoghuman.lib.database.core.CrudService
     */
    public CrudService getCrudService();
    
    /**
     * Returns a named {@link com.github.naoghuman.lib.database.core.CrudService} 
     * which allowed all sql operations.
     * 
     * @param  name The name from the {@code CrudService}.
     * @return The {@code CrudService}.
     * @see    com.github.naoghuman.lib.database.core.CrudService
     */
    public CrudService getCrudService(final String name);
    
    /**
     * Returns a named {@link javax.persistence.EntityManager} which allowed 
     * all sql operations.
     * 
     * @param  name The name from the {@code EntityManager}.
     * @return The {@code EntityManager}.
     * @see    javax.persistence.EntityManager
     */
    public EntityManager getEntityManager(final String name);
    
    /**
     * Creates a database with the specific parameter in the folder
     * {@code System.getProperty("user.dir") + File.separator + "database"} 
     * if it not exists.<br>
     * If the parameter have no suffix {@code .odb} then the suffix will be
     * automatically added, otherwise not.
     * 
     * @param database The name for the database which should be created.
     */
    public void register(final String database);
    
    /**
     * Removes a {@link com.github.naoghuman.lib.database.core.CrudService} with 
     * the given name. Also the associated {@link javax.persistence.EntityManager} 
     * will be removed.
     * 
     * @param name The name for the {@code CrudService} which should be removed.
     * @see   com.github.naoghuman.lib.database.core.CrudService
     * @see   com.github.naoghuman.lib.database.core.Database#removeEntityManager(java.lang.String)
     * @see   javax.persistence.EntityManager
     */
    public void removeCrudService(final String name);
    
    /**
     * Removes a {@link javax.persistence.EntityManager} with the given name. Also
     * the associated {@link com.github.naoghuman.lib.database.core.CrudService} 
     * will be removed.
     * 
     * @param name The name for the {@code EntityManager} which should be removed.
     * @see com.github.naoghuman.lib.database.core.Database#removeCrudService(java.lang.String)
     */
    public void removeEntityManager(final String name);
    
    /**
     * Close the previous registered database.
     * 
     * @see com.github.naoghuman.lib.database.core.Database#register(java.lang.String)
     */
    public void shutdown();
    
}
