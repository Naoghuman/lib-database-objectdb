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

import de.pro.lib.database.api.DatabaseFacade;
import de.pro.lib.logger.api.LoggerFacade;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author PRo
 */
public class LibDatabaseTest {
    private static final String DATABASE_PATH
            = System.getProperty("user.dir") + File.separator // NOI18N
            + "database" + File.separator; // NOI18N
    private final static String TEST_DB_WITH_SUFFIX = "test4.odb"; // NOI18N
    private final static String TEST_DB_WITHOUT_SUFFIX = "bla"; // NOI18N

    public LibDatabaseTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        LoggerFacade.INSTANCE.getLogger().own(LibDatabaseTest.class, " PRoDatabaseTest#setUpClass()");
        LoggerFacade.INSTANCE.getLogger().deactivate(Boolean.TRUE);
        
    }

    @AfterClass
    public static void tearDownClass() {
        LoggerFacade.INSTANCE.getLogger().deactivate(Boolean.FALSE);
        LoggerFacade.INSTANCE.getLogger().own(LibDatabaseTest.class, " PRoDatabaseTest#tearDownClass()");
        
    }

    @Before
    public void setUp() {
        
    }

    @After
    public void tearDown() {
        
    }

    @Test
    public void registerWithSuffix() {
        DatabaseFacade.INSTANCE.register(TEST_DB_WITH_SUFFIX);
        
        // The database will only created if an transaction is done
        DatabaseFacade.INSTANCE.getCrudService("registerWithSuffix").create(new CountEntity());
        
        DatabaseFacade.INSTANCE.shutdown();
        
        File file = new File(DATABASE_PATH + TEST_DB_WITH_SUFFIX);
        assertTrue("The database test.odb must exists...", file.exists());
        
        DatabaseFacade.INSTANCE.drop(TEST_DB_WITH_SUFFIX);
        assertFalse("The database test.odb must deleted...", file.exists());
    }

    @Test
    public void registerWithoutSuffix() {
        DatabaseFacade.INSTANCE.register(TEST_DB_WITHOUT_SUFFIX);
        
        // The database will only created if an transaction is done
        DatabaseFacade.INSTANCE.getCrudService("registerWithoutSuffix").create(new CountEntity());
        
        DatabaseFacade.INSTANCE.shutdown();
        
        File file = new File(DATABASE_PATH + TEST_DB_WITHOUT_SUFFIX + ".odb");
        assertTrue("The database bla.odb must exists...", file.exists());
        
        DatabaseFacade.INSTANCE.drop(TEST_DB_WITHOUT_SUFFIX);
        assertFalse("The database bla.odb must deleted...", file.exists());
    }
    
}
