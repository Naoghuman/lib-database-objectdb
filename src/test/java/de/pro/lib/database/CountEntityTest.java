/*
 * Copyright (C) 2015 PRo
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
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author PRo
 */
public class CountEntityTest {
    
    private final static String TEST_DB_WITH_SUFFIX = "test.odb"; // NOI18N
    private final static String TABLE = "CountEntity"; // NOI18N
    
    @BeforeClass
    public static void setUpClass() {
        LoggerFacade.getDefault().deactivate(Boolean.TRUE);
        DatabaseFacade.getDefault().register(TEST_DB_WITH_SUFFIX);
    }

    @AfterClass
    public static void tearDownClass() {
        DatabaseFacade.getDefault().shutdown();
        DatabaseFacade.getDefault().drop(TEST_DB_WITH_SUFFIX);
    }
    
    @Test
    public void count() {
//        Long count = 0L;
        Long count = DatabaseFacade.getDefault().getCrudService().count(TABLE);
        assertTrue("count must -1", count.longValue()==-1);
        
        final CountEntity ce = DatabaseFacade.getDefault().getCrudService().create(new CountEntity());
        DatabaseFacade.getDefault().getCrudService().delete(CountEntity.class, new Long(ce.getId()));
        count = DatabaseFacade.getDefault().getCrudService().count(TABLE);
        assertTrue("count must 0", count.longValue()==0);
        
        DatabaseFacade.getDefault().getCrudService().create(new CountEntity());
        count = DatabaseFacade.getDefault().getCrudService().count(TABLE);
        assertTrue("count must 1", count.longValue()==1);
        
        DatabaseFacade.getDefault().getCrudService().create(new CountEntity());
        count = DatabaseFacade.getDefault().getCrudService().count(TABLE);
        assertTrue("count must 2", count.longValue()==2);
    }
    
}