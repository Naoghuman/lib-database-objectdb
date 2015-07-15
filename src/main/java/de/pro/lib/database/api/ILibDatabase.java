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

import javax.persistence.EntityManager;

/**
 * The <code>Interface</code> for the class {@link de.pro.lib.database.LibDatabase}.<br />
 * Over the facade {@link de.pro.lib.database.api.DatabaseFacade} you can access
 * the methods in this <code>Interface</code>.
 *
 * @author PRo
 * @see de.pro.lib.database.LibDatabase
 * @see de.pro.lib.database.api.DatabaseFacade
 */
public interface ILibDatabase {
    
    /**
     * Allowed the developer to drop the defined database.<br />
     * This method can be used for testing purpose.
     * 
     * @param database The database which should be dropped.
     */
    public void drop(String database);
    
    /**
     * Returns a {@link de.pro.lib.database.api.ICrudService} with the name 
     * DEFAULT which allowed all sql operations.
     * 
     * @return The crud service.
     */
    public ICrudService getCrudService();
    
    /**
     * Returns a named {@link de.pro.lib.database.api.ICrudService} which allowed 
     * all sql operations.
     * 
     * @param name The name from the <code>ICrudService</code>.
     * @return The <code>ICrudService</code>.
     */
    public ICrudService getCrudService(String name);
    
    /**
     * Returns a named {@link javax.persistence.EntityManager} which allowed 
     * all sql operations.
     * 
     * @param name The name from the EntityManager.
     * @return The EntityManager.
     */
    public EntityManager getEntityManager(String name);
    
    /**
     * Create a database with the specific parameter in the folder
     * <code>System.getProperty("user.dir") + File.separator + 
     * "database"</code> if it not exists.<br />
     * If the parameter have no suffix <code>.odb</code> then it will be
     * automaticaly added, otherwise not.
     * 
     * @param database The name for the database which should be created.
     */
    public void register(String database);
    
    /**
     * Remove a {@link de.pro.lib.database.api.ICrudService} with the given name. Also
     * the associated {@link javax.persistence.EntityManager} will be removed.
     * 
     * @param name The name for the <code>ICrudService</code> which should be removed.
     * @see de.pro.lib.database.api.ILibDatabase#removeEntityManager(java.lang.String)
     */
    public void removeCrudService(String name);
    
    /**
     * Remove a {@link javax.persistence.EntityManager} with the given name. Also
     * the associated {@link de.pro.lib.database.api.ICrudService} will be removed.
     * 
     * @param name The name for the <code>EntityManager</code> which should be removed.
     * @see de.pro.lib.database.api.ILibDatabase#removeCrudService(java.lang.String)
     */
    public void removeEntityManager(String name);
    
    /**
     * Close the previous registered database.
     * 
     * @see de.pro.lib.database.api.ILibDatabase#register(java.lang.String)
     */
    public void shutdown();
    
}
