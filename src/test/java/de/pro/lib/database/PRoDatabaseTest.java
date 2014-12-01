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
public class PRoDatabaseTest {
    private static final String LOG
            = System.getProperty("user.dir") + File.separator // NOI18N
            + "log" + File.separator; // NOI18N
    private static final String DATABASE_PATH
            = System.getProperty("user.dir") + File.separator // NOI18N
            + "database" + File.separator; // NOI18N
    private final static String TEST_DB_WITH_SUFFIX = "test.odb"; // NOI18N
    private final static String TEST_DB_WITHOUT_SUFFIX = "bla"; // NOI18N

    public PRoDatabaseTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        final File file = new File(DATABASE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void registerWithSuffix() {
        DatabaseFacade.getDefault().register(TEST_DB_WITH_SUFFIX);
        DatabaseFacade.getDefault().shutdown();
        
        File file = new File(DATABASE_PATH + TEST_DB_WITH_SUFFIX);
        assertTrue("The database test.odb must exists...", file.exists());
        
        DatabaseFacade.getDefault().drop(TEST_DB_WITH_SUFFIX);
        assertFalse("The database test.odb must deleted...", file.exists());
    }

    @Test
    public void registerWithoutSuffix() {
        DatabaseFacade.getDefault().register(TEST_DB_WITHOUT_SUFFIX);
        DatabaseFacade.getDefault().shutdown();
        
        File file = new File(DATABASE_PATH + TEST_DB_WITHOUT_SUFFIX + ".odb");
        assertTrue("The database bla.odb must exists...", file.exists());
        
        DatabaseFacade.getDefault().drop(TEST_DB_WITHOUT_SUFFIX);
        assertFalse("The database bla.odb must deleted...", file.exists());
    }
}
