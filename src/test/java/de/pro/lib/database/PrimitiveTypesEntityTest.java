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

import de.pro.lib.database.api.DatabaseFacade;
import de.pro.lib.logger.api.LoggerFacade;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author PRo
 */
public class PrimitiveTypesEntityTest {
    
    private final static String TEST_DB_WITH_SUFFIX = "test.odb"; // NOI18N
    
    public PrimitiveTypesEntityTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        LoggerFacade.INSTANCE.getLogger().own(PrimitiveTypesEntityTest.class, " PrimitiveTypesEntityTest#setUpClass()");
        LoggerFacade.INSTANCE.getLogger().deactivate(Boolean.TRUE);
        
        DatabaseFacade.getDefault().register(TEST_DB_WITH_SUFFIX);
    }

    @AfterClass
    public static void tearDownClass() {
        LoggerFacade.INSTANCE.getLogger().deactivate(Boolean.FALSE);
        LoggerFacade.INSTANCE.getLogger().own(PrimitiveTypesEntityTest.class, " PrimitiveTypesEntityTest#tearDownClass()");
        
        DatabaseFacade.getDefault().shutdown();
        DatabaseFacade.getDefault().drop(TEST_DB_WITH_SUFFIX);
    }
    
    @Test
    public void create() {
        LoggerFacade.INSTANCE.getLogger().own(this.getClass(), " #create()");
        
        final PrimitiveTypesEntity entity1 = new PrimitiveTypesEntity();
        entity1.setId(2);
        entity1.setBooleanValue(true);
        entity1.setDoubleValue(321.123d);
        entity1.setLongValue(123L);
        
        final PrimitiveTypesEntity entity2 = DatabaseFacade.getDefault()
                .getCrudService("create").create(entity1);
        assertTrue("id must 2", entity2.getId()==2);
        assertTrue("boolean must true", entity2.getBooleanValue()==true);
        assertTrue("double must 321.123d", entity2.getDoubleValue()==321.123d);
        assertTrue("long must 123L", entity2.getLongValue()==123L);
        
        DatabaseFacade.getDefault().getCrudService("create").delete(PrimitiveTypesEntity.class, 2L);
    }
    
    @Test
    public void createWithManuellBeginAndCommitTransaction() {
        LoggerFacade.INSTANCE.getLogger().own(this.getClass(), " #createWithManuellBeginAndCommitTransaction()");
        
        DatabaseFacade.getDefault().getCrudService("createWithManuellBeginAndCommitTransaction")
                .beginTransaction();
        
        final PrimitiveTypesEntity entity1 = new PrimitiveTypesEntity();
        entity1.setId(32);
        entity1.setBooleanValue(true);
        entity1.setDoubleValue(321.123d);
        entity1.setLongValue(123L);
        DatabaseFacade.getDefault().getCrudService("createWithManuellBeginAndCommitTransaction")
                .create(entity1, false);
        
        final PrimitiveTypesEntity entity2 = new PrimitiveTypesEntity();
        entity2.setId(33);
        entity2.setBooleanValue(true);
        entity2.setDoubleValue(32.23d);
        entity2.setLongValue(23L);
        DatabaseFacade.getDefault().getCrudService("createWithManuellBeginAndCommitTransaction")
                .create(entity2, false);
        
        DatabaseFacade.getDefault().getCrudService("createWithManuellBeginAndCommitTransaction")
                .commitTransaction();
        
        List<PrimitiveTypesEntity> entities = DatabaseFacade.getDefault().getCrudService("createWithManuellBeginAndCommitTransaction")
                .findByNamedQuery(PrimitiveTypesEntity.class, "PrimitiveTypesEntity.findAll");
        assertTrue("entities.size() == 2 not " + entities.size(), entities.size() == 2);
    }
    
    @Test
    public void delete() {
        LoggerFacade.INSTANCE.getLogger().own(this.getClass(), " #delete()");
        
        final PrimitiveTypesEntity entity1 = new PrimitiveTypesEntity();
        entity1.setId(10);
        entity1.setBooleanValue(true);
        entity1.setDoubleValue(321.123d);
        entity1.setLongValue(123L);
        
        DatabaseFacade.getDefault().getCrudService("delete").create(entity1);
        DatabaseFacade.getDefault().getCrudService("delete").delete(PrimitiveTypesEntity.class, 10L);
        
        final PrimitiveTypesEntity entity2 = DatabaseFacade.getDefault()
                .getCrudService("delete").findById(PrimitiveTypesEntity.class, 10L);
        assertTrue("entity2 must null", entity2 == null);
    }
    
    @Test
    public void update() {
        LoggerFacade.INSTANCE.getLogger().own(this.getClass(), " #update()");
        
        final PrimitiveTypesEntity entity1 = new PrimitiveTypesEntity();
        entity1.setId(20);
        entity1.setBooleanValue(true);
        entity1.setDoubleValue(321.123d);
        entity1.setLongValue(123L);
        final PrimitiveTypesEntity entity2 = DatabaseFacade.getDefault()
                .getCrudService("update").create(entity1);
        
        entity2.setBooleanValue(false);
        entity2.setDoubleValue(0.01d);
        
        final PrimitiveTypesEntity entity3 = DatabaseFacade.getDefault()
                .getCrudService("update").update(entity2);
        assertTrue("id must 20", entity3.getId()==20);
        assertTrue("boolean must false", entity3.getBooleanValue()==false);
        assertTrue("double must 0.01d", entity3.getDoubleValue()==0.01d);
        
        DatabaseFacade.getDefault().getCrudService("update").delete(PrimitiveTypesEntity.class, 20L);
    }
    
}
