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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import de.pro.lib.database.api.DatabaseFacade;
import de.pro.lib.logger.api.LoggerFacade;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author PRo
 */
public class LightModelTest {
    
    private final static String TEST_DB_WITH_SUFFIX = "Database.odb"; // NOI18N

    @BeforeClass
    public static void setUpClass() {
        LoggerFacade.INSTANCE.getLogger().own(LightModelTest.class, " LightModelTest#setUpClass()");
        LoggerFacade.INSTANCE.getLogger().deactivate(Boolean.TRUE);
        
        DatabaseFacade.getDefault().register(TEST_DB_WITH_SUFFIX);
    }

    @AfterClass
    public static void tearDownClass() {
        LoggerFacade.INSTANCE.getLogger().deactivate(Boolean.FALSE);
        LoggerFacade.INSTANCE.getLogger().own(LightModelTest.class, " LightModelTest#tearDownClass()");
        
        DatabaseFacade.getDefault().shutdown();
        DatabaseFacade.getDefault().drop(TEST_DB_WITH_SUFFIX);
    }
    
    @Test
    public void testFullModel() {
        LoggerFacade.INSTANCE.getLogger().own(this.getClass(), " #testFullModel()");
        
        LightModel lm1 = new LightModel();
        lm1.setId(1);
        lm1.setDoubleValue(2.0);
        lm1.setLongValue(3L);
        
        final LightModel lm2 = DatabaseFacade.getDefault().getCrudService("testFullModel").create(lm1);
        assertTrue("id must 1", lm2.getId()==1);
        assertTrue("double must 2.0", lm2.getDoubleValue()==2.0);
        assertTrue("long must 3L", lm2.getLongValue()==3L);
    }
    
    @Test
    public void testLightModel() {
        LoggerFacade.INSTANCE.getLogger().own(this.getClass(), " #testLightModel()");
        
        LightModel lm1 = new LightModel();
        lm1.setId(2);
        lm1.setDoubleValue(3.0);
        
        final LightModel lm2 = DatabaseFacade.getDefault().getCrudService("testLightModel").create(lm1);
        assertTrue("id must 2", lm2.getId()==2);
        assertTrue("double must 3.0", lm2.getDoubleValue()==3.0);
        assertNull("long must null", lm2.getLongValue());
    }
    
}
