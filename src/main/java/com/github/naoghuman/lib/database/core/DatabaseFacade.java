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

import com.github.naoghuman.lib.database.internal.DefaultDatabase;
import java.util.Optional;
import javax.persistence.EntityManager;

/**
 * The facade {@link com.github.naoghuman.lib.database.core.DatabaseFacade} 
 * provides access to the default implementation from the {@code Interface} 
 * {@link com.github.naoghuman.lib.database.core.Database} which is 
 * {@link com.github.naoghuman.lib.database.internal.DefaultDatabase}.
 *
 * @author Naoghuman
 * @see com.github.naoghuman.lib.database.core.Database
 * @see com.github.naoghuman.lib.database.internal.DefaultDatabase
 */
public final class DatabaseFacade implements Database {
    
    private static final Optional<DatabaseFacade> instance = Optional.of(new DatabaseFacade());

    /**
     * Returns a singleton instance from the class <code>DatabaseFacade</code>.
     * 
     * @return a singleton instance from the class <code>DatabaseFacade</code>.
     */
    public static final DatabaseFacade getDefault() {
        return instance.get();
    }
    
    private Database database = null;

    private DatabaseFacade() {
        this.initialize();
    }
    
    private void initialize() {
        database = new DefaultDatabase();
    }

    @Override
    public void drop(final String database) {
        this.database.drop(database);
    }

    @Override
    public CrudService getCrudService() {
        return database.getCrudService();
    }

    @Override
    public CrudService getCrudService(final String name) {
        return database.getCrudService(name);
    }

    @Override
    public EntityManager getEntityManager(final String name) {
        return database.getEntityManager(name);
    }

    @Override
    public void register(final String database) {
        this.database.register(database);
    }

    @Override
    public void removeCrudService(final String name) {
        database.removeCrudService(name);
    }

    @Override
    public void removeEntityManager(final String name) {
        database.removeEntityManager(name);
    }

    @Override
    public void shutdown() {
        database.shutdown();
    }
    
}
