/*
 * Copyright (C) 2018 Naoghuman
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
package com.github.naoghuman.lib.database.internal;

import java.util.Optional;
import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Contains {@code UnitTests} for the class {@link com.github.naoghuman.lib.database.internal.DefaultColorConverter}.
 *
 * @author Naoghuman
 * @since  0.6.0
 * @see    com.github.naoghuman.lib.database.internal.DefaultColorConverter
 */
public class DefaultColorConverterTest {
    
    public DefaultColorConverterTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test(expected=NullPointerException.class)
    public void testToThrowNullPointerException() {
        Color value = null;
        DefaultColorConverter instance = new DefaultColorConverter();
        
        instance.to(value);
    }

    @Test
    public void testToColorBlack() {
        Color value = Color.BLACK;
        DefaultColorConverter instance = new DefaultColorConverter();
        
        String expResult = "0.0;0.0;0.0;1.0"; // NOI18N
        String result = instance.to(value);
        
        assertEquals(expResult, result);
    }

    @Test
    public void testToColorOwn() {
        Color value = Color.hsb(0.1d, 0.2d, 0.3d, 0.4d);
        DefaultColorConverter instance = new DefaultColorConverter();
        
        String expResult = "0.10000166396252341;0.20000004967053533;0.30000001192092896;0.4000000059604645"; // NOI18N
        String result = instance.to(value);
        
        assertEquals(expResult, result);
    }

    @Test
    public void testToColorWhite() {
        Color value = Color.WHITE;
        DefaultColorConverter instance = new DefaultColorConverter();
        
        String expResult = "0.0;0.0;1.0;1.0"; // NOI18N
        String result = instance.to(value);
        
        assertEquals(expResult, result);
    }
    
    @Test(expected=NullPointerException.class)
    public void testFromThrowNullPointerException() {
        String value = null;
        DefaultColorConverter instance = new DefaultColorConverter();
        
        instance.from(value);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testFromThrowIllegalArgumentExceptionBecauseEmpty() {
        String value = ""; // NOI18N
        DefaultColorConverter instance = new DefaultColorConverter();

        instance.from(value);
    }

    @Test
    public void testFromColorBlack() {
        String value = "0.0;0.0;0.0;1.0"; // NOI18N
        DefaultColorConverter instance = new DefaultColorConverter();
        
        Optional<Color> expResult = Optional.of(Color.BLACK);
        Optional<Color> result = instance.from(value);
        
        assertTrue(result.isPresent());
        assertEquals(expResult.get(), result.get());
    }

    @Test
    public void testFromColorOwn() {
        String value = "0.0;0.0;1.0;1.0"; // NOI18N
        DefaultColorConverter instance = new DefaultColorConverter();
        
        Optional<Color> expResult = Optional.of(Color.WHITE);
        Optional<Color> result = instance.from(value);
        
        assertTrue(result.isPresent());
        assertEquals(expResult.get(), result.get());
    }

    @Test
    public void testFromColorWhite() {
        String value = "0.10000166396252341;0.20000004967053533;0.30000001192092896;0.4000000059604645"; // NOI18N
        DefaultColorConverter instance = new DefaultColorConverter();
        
        Optional<Color> expResult = Optional.of(Color.hsb(0.1d, 0.2d, 0.3d, 0.4d));
        Optional<Color> result = instance.from(value);
        
        assertTrue(result.isPresent());
        assertEquals(expResult.get(), result.get());
    }
    
}
