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

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Contains {@code UnitTests} for the class {@link com.github.naoghuman.lib.database.internal.DefaultLocalDateTimeConverter}.
 *
 * @author Naoghuman
 * @since  0.6.0
 * @see    com.github.naoghuman.lib.database.internal.DefaultLocalDateTimeConverter
 */
public class DefaultLocalDateTimeConverterTest {
    
    public DefaultLocalDateTimeConverterTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test(expected=NullPointerException.class)
    public void testToThrowNullPointerException() {
        LocalDateTime value = null;
        DefaultLocalDateTimeConverter instance = new DefaultLocalDateTimeConverter();
        
        instance.to(value);
    }
    
    @Test(expected=NullPointerException.class)
    public void testFromThrowNullPointerException() {
        String value = null;
        DefaultLocalDateTimeConverter instance = new DefaultLocalDateTimeConverter();
        
        instance.from(value);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testFromThrowIllegalArgumentException() {
        String value = ""; // NOI18N
        DefaultLocalDateTimeConverter instance = new DefaultLocalDateTimeConverter();

        instance.from(value);
    }

}
