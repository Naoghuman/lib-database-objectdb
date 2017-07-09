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
package com.github.naoghuman.lib.database.api;

import com.github.naoghuman.lib.database.LibDatabase;
import java.util.Optional;
import javax.persistence.EntityManager;

/**
 * The facade {@link com.github.naoghuman.lib.database.api.DatabaseFacade} provides access to
 * the action methods during the Interface {@link com.github.naoghuman.lib.database.api.ILibDatabase}.
 * <p>
 * Deprecated since 0.5.0. Use {@link com.github.naoghuman.lib.database.core.DatabaseFacade}
 * instead.
 *
 * @author PRo
 * @see com.github.naoghuman.lib.database.api.ILibDatabase
 * @see com.github.naoghuman.lib.database.core.DatabaseFacade
 */
@Deprecated
public final class DatabaseFacade implements ILibDatabase {
    
    private static final Optional<DatabaseFacade> instance = Optional.of(new DatabaseFacade());

    /**
     * Returns a singleton instance from the class <code>DatabaseFacade</code>.
     * 
     * @return a singleton instance from the class <code>DatabaseFacade</code>.
     */
    @Deprecated
    public static final DatabaseFacade getDefault() {
        return instance.get();
    }
    
    private ILibDatabase database = null;

    private DatabaseFacade() {
        this.initialize();
    }
    
    private void initialize() {
        database = new LibDatabase();
    }

    @Deprecated
    @Override
    public void drop(String database) {
        this.database.drop(database);
    }

    @Deprecated
    @Override
    public ICrudService getCrudService() {
        return database.getCrudService();
    }

    @Deprecated
    @Override
    public ICrudService getCrudService(String name) {
        return database.getCrudService(name);
    }

    @Deprecated
    @Override
    public EntityManager getEntityManager(String name) {
        return database.getEntityManager(name);
    }

    @Deprecated
    @Override
    public void register(String database) {
        this.database.register(database);
    }

    @Deprecated
    @Override
    public void removeCrudService(String name) {
        database.removeCrudService(name);
    }

    @Deprecated
    @Override
    public void removeEntityManager(String name) {
        database.removeEntityManager(name);
    }

    @Deprecated
    @Override
    public void shutdown() {
        database.shutdown();
    }
    
}
