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
package com.github.naoghuman.lib.database;

import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.lib.database.api.ICrudService;
import com.github.naoghuman.lib.database.api.ILibDatabase;
import java.io.File;
import java.util.Map;
import javafx.collections.FXCollections;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * The implementation from the Interface {@link com.github.naoghuman.lib.database.api.ILibDatabase}.<br />
 * Access to this class is over the facade {@link com.github.naoghuman.lib.database.api.DatabaseFacade}.
 * 
 * @author PRo
 * @see com.github.naoghuman.lib.database.api.ILibDatabase
 * @see com.github.naoghuman.lib.database.api.DatabaseFacade
 */
public final class LibDatabase implements ILibDatabase {
    
    private static final String DATABASE_PATH =
            System.getProperty("user.dir") + File.separator // NOI18N
            + "database" + File.separator; // NOI18N
    
    private static final String DEFAULT = "DEFAULT"; // NOI18N
    private static final String SUFFIX_ODB = ".odb"; // NOI18N
    
    private static final Map<String, ICrudService> CRUDSERVICES = FXCollections.observableHashMap();
    
    private EntityManagerFactory entityManagerFactory = null;

    /**
     * Default contructor from the class {@link com.github.naoghuman.lib.database.LibDatabase}.
     */
    public LibDatabase() { }
    
    @Override
    public void drop(String database) {
        if (!database.endsWith(SUFFIX_ODB)) {
            database = database + SUFFIX_ODB;
        }
        
        final File db = new File(DATABASE_PATH + database);
        if (db.exists()) {
            LoggerFacade.getDefault().warn(this.getClass(),
                "Delete database: " + DATABASE_PATH + database); // NOI18N
            
            db.delete();
        }
    }

    @Override
    public ICrudService getCrudService() {
        return this.getCrudService(DEFAULT);
    }

    @Override
    public ICrudService getCrudService(String name) {
        if (!CRUDSERVICES.containsKey(name)) {
            LoggerFacade.getDefault().own(this.getClass(), "Add CrudService: " + name); // NOI18N

            CRUDSERVICES.put(name, new CrudService(entityManagerFactory.createEntityManager()));
        }
        
        return CRUDSERVICES.get(name);
    }

    @Override
    public EntityManager getEntityManager(String name) {
        return this.getCrudService(name).getEntityManager();
    }
    
    @Override
    public void register(String database) {
        LoggerFacade.getDefault().own(this.getClass(),
                "Initialize ObjectDB with database: " + database); // NOI18N
        
        if (!database.endsWith(SUFFIX_ODB)) {
            database = database + SUFFIX_ODB;
        }
        
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(
                    "database" + File.separator + database);
        }
    }
    
    @Override
    public void removeCrudService(String name) {
        if (!CRUDSERVICES.containsKey(name)) {
            LoggerFacade.getDefault().own(this.getClass(), 
                    "Can't remove not existing CrudService: " + name
                    + " with associated EntityManager..."); // NOI18N

            return;
        }
        
        LoggerFacade.getDefault().own(this.getClass(), "Remove CrudService: " + name); // NOI18N

        CRUDSERVICES.get(name).shutdown(name);
        CRUDSERVICES.remove(name);
    }

    @Override
    public void removeEntityManager(String name) {
        this.removeCrudService(name);
    }

    @Override
    public void shutdown() {
        LoggerFacade.getDefault().own(this.getClass(), "Shutdown ObjectDB"); // NOI18N

        CRUDSERVICES.keySet().stream().forEach((key) -> {
            CRUDSERVICES.get(key).shutdown(key);
        });
        CRUDSERVICES.clear();
        
        LoggerFacade.getDefault().own(this.getClass(), "Shutdown EntityManagerFactory"); // NOI18N
        
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
            entityManagerFactory = null;
        }
    }
    
}
