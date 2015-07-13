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
     * Returns the crud service which allowed all sql operations.
     * 
     * @return The crud service.
     */
    public ICrudService getCrudService();
    
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
     * Close the previous registered database.
     * 
     * @see de.pro.lib.database.api.ILibDatabase#register(java.lang.String)
     */
    public void shutdown();
    
}
