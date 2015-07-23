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

import de.pro.lib.database.LibDatabase;

/**
 * The facade {@link de.pro.lib.database.api.DatabaseFacade} provides access to
 * the action methods during the Interface {@link de.pro.lib.database.api.ILibDatabase}.
 *
 * @author PRo
 * @see de.pro.lib.database.api.ILibDatabase
 */
public enum DatabaseFacade {
    
    /**
     * Over the value <code>INSTANCE</code> the developer have access to the
     * functionality in <code>DatabaseFacade</code>.
     */
    INSTANCE;
    
    private ILibDatabase instance = null;

    private DatabaseFacade() {
        this.initialize();
    }
    
    private void initialize() {
        instance = new LibDatabase();
    }
    
    /**
     * Over the Interface {@link @link de.pro.lib.database.api.ILibDatabase} 
     * the developer have access to the database methods.
     * 
     * @return a singleton instance from ILibAction.
     */
    public ILibDatabase getDatabase() {
        return instance;
    }
    
}
