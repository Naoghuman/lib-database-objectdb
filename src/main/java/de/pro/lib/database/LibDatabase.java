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
package de.pro.lib.database;

import de.pro.lib.database.api.ICrudService;
import de.pro.lib.database.api.ILibDatabase;
import de.pro.lib.logger.api.LoggerFacade;
import java.io.File;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * The implementation from the Interface {@link de.pro.lib.database.api.ILibDatabase}.<br />
 * Access to this class is over the facade {@link de.pro.lib.database.api.DatabaseFacade}.
 * 
 * @author PRo
 * @see de.pro.lib.database.api.ILibDatabase
 * @see de.pro.lib.database.api.DatabaseFacade
 */
public final class LibDatabase implements ILibDatabase {
    
    private static final String DATABASE_PATH =
            System.getProperty("user.dir") + File.separator // NOI18N
            + "database" + File.separator; // NOI18N
    
    private static final String SUFFIX_ODB = ".odb"; // NOI18N
    
    private ICrudService crudService = null;
    private EntityManager entityManager = null;
    private EntityManagerFactory entityManagerFactory = null;

    /**
     * Default contructor from the class {@link de.pro.lib.database.LibDatabase}.
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
        if (crudService == null) {
            crudService = new CrudService(entityManager);
        }
        
        return crudService;
    }
    
    @Override
    public void register(String database) {
        LoggerFacade.getDefault().own(this.getClass(),
                "Initialize ObjectDB with database: " + database); // NOI18N
        
        if (!database.endsWith(SUFFIX_ODB)) {
            database = database + SUFFIX_ODB;
        }
        /*
        TODO
         - for every database will an entitymanager(factory) created.
         - getCrudService(String database)
         - add info.txt for license objectdb one db=10entity with 1.000.000 objects.
        */
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(
                    "database" + File.separator + database);
        }
        
        if (entityManager == null) {
            entityManager = entityManagerFactory.createEntityManager();
        }
    }

    @Override
    public void shutdown() {
        LoggerFacade.getDefault().own(this.getClass(), "Shutdown ObjectDB"); // NOI18N
        
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
            entityManager = null;
        }
        
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
            entityManagerFactory = null;
        }
    }
    
}
