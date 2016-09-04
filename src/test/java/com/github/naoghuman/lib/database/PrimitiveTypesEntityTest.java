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
package com.github.naoghuman.lib.database;

import com.github.naoghuman.lib.logger.api.LoggerFacade;
import static org.junit.Assert.assertTrue;

import com.github.naoghuman.lib.database.api.DatabaseFacade;
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
        LoggerFacade.getDefault().own(PrimitiveTypesEntityTest.class, " PrimitiveTypesEntityTest#setUpClass()");
        LoggerFacade.getDefault().deactivate(Boolean.TRUE);
        
        DatabaseFacade.INSTANCE.register(TEST_DB_WITH_SUFFIX);
    }

    @AfterClass
    public static void tearDownClass() {
        LoggerFacade.getDefault().deactivate(Boolean.FALSE);
        LoggerFacade.getDefault().own(PrimitiveTypesEntityTest.class, " PrimitiveTypesEntityTest#tearDownClass()");
        
        DatabaseFacade.INSTANCE.shutdown();
        DatabaseFacade.INSTANCE.drop(TEST_DB_WITH_SUFFIX);
    }
    
    @Test
    public void create() {
        LoggerFacade.getDefault().own(this.getClass(), " #create()");
        
        final PrimitiveTypesEntity entity1 = new PrimitiveTypesEntity();
        entity1.setId(2);
        entity1.setBooleanValue(true);
        entity1.setDoubleValue(321.123d);
        entity1.setLongValue(123L);
        
        final PrimitiveTypesEntity entity2 = DatabaseFacade.INSTANCE.getCrudService("create")
                .create(entity1);
        assertTrue("id must 2", entity2.getId()==2);
        assertTrue("boolean must true", entity2.getBooleanValue()==true);
        assertTrue("double must 321.123d", entity2.getDoubleValue()==321.123d);
        assertTrue("long must 123L", entity2.getLongValue()==123L);
        
        DatabaseFacade.INSTANCE.getCrudService("create").delete(PrimitiveTypesEntity.class, 2L);
    }
    
    @Test
    public void createWithManuellBeginAndCommitTransaction() {
        LoggerFacade.getDefault().own(this.getClass(), " #createWithManuellBeginAndCommitTransaction()");
        
        DatabaseFacade.INSTANCE.getCrudService("createWithManuellBeginAndCommitTransaction")
                .beginTransaction();
        
        final PrimitiveTypesEntity entity1 = new PrimitiveTypesEntity();
        entity1.setId(32);
        entity1.setBooleanValue(true);
        entity1.setDoubleValue(321.123d);
        entity1.setLongValue(123L);
        DatabaseFacade.INSTANCE.getCrudService("createWithManuellBeginAndCommitTransaction")
                .create(entity1, false);
        
        final PrimitiveTypesEntity entity2 = new PrimitiveTypesEntity();
        entity2.setId(33);
        entity2.setBooleanValue(true);
        entity2.setDoubleValue(32.23d);
        entity2.setLongValue(23L);
        DatabaseFacade.INSTANCE.getCrudService("createWithManuellBeginAndCommitTransaction")
                .create(entity2, false);
        
        DatabaseFacade.INSTANCE.getCrudService("createWithManuellBeginAndCommitTransaction")
                .commitTransaction();
        
        List<PrimitiveTypesEntity> entities = DatabaseFacade.INSTANCE
                .getCrudService("createWithManuellBeginAndCommitTransaction")
                .findByNamedQuery(PrimitiveTypesEntity.class, "PrimitiveTypesEntity.findAll");
        assertTrue("entities.size() == 2 not " + entities.size(), entities.size() == 2);
    }
    
    @Test
    public void delete() {
        LoggerFacade.getDefault().own(this.getClass(), " #delete()");
        
        final PrimitiveTypesEntity entity1 = new PrimitiveTypesEntity();
        entity1.setId(10);
        entity1.setBooleanValue(true);
        entity1.setDoubleValue(321.123d);
        entity1.setLongValue(123L);
        
        DatabaseFacade.INSTANCE.getCrudService("delete").create(entity1);
        DatabaseFacade.INSTANCE.getCrudService("delete").delete(PrimitiveTypesEntity.class, 10L);
        
        final PrimitiveTypesEntity entity2 = DatabaseFacade.INSTANCE
                .getCrudService("delete").findById(PrimitiveTypesEntity.class, 10L);
        assertTrue("entity2 must null", entity2 == null);
    }
    
    @Test
    public void update() {
        LoggerFacade.getDefault().own(this.getClass(), " #update()");
        
        final PrimitiveTypesEntity entity1 = new PrimitiveTypesEntity();
        entity1.setId(20);
        entity1.setBooleanValue(true);
        entity1.setDoubleValue(321.123d);
        entity1.setLongValue(123L);
        final PrimitiveTypesEntity entity2 = DatabaseFacade.INSTANCE
                .getCrudService("update").create(entity1);
        
        entity2.setBooleanValue(false);
        entity2.setDoubleValue(0.01d);
        
        final PrimitiveTypesEntity entity3 = DatabaseFacade.INSTANCE
                .getCrudService("update").update(entity2);
        assertTrue("id must 20", entity3.getId()==20);
        assertTrue("boolean must false", entity3.getBooleanValue()==false);
        assertTrue("double must 0.01d", entity3.getDoubleValue()==0.01d);
        
        DatabaseFacade.INSTANCE.getCrudService("update").delete(PrimitiveTypesEntity.class, 20L);
    }
    
}
