/**
 * Copyright (C) 2018 - 2018 Naoghuman
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
package com.github.naoghuman.lib.database.core;

import java.util.Optional;

/**
 * This {@code Interface} allowed to convert objects to a {@link java.lang.String} 
 * and returned to the given type.
 * <p>
 * For example with the facade {@link com.github.naoghuman.lib.database.core.ConverterFacade} 
 * the developer have momentary the possiblility to convert a {@code JavaFX} color 
 * or a {@link java.time.LocalDateTime}.
 * 
 * @param  <T> the type of class which should be convert.
 * @author Naoghuman
 * @since  0.6.0
 * @see    com.github.naoghuman.lib.database.core.ConverterFacade
 * @see    java.lang.String
 * @see    java.time.LocalDateTime
 * @see    javafx.scene.paint.Color
 */
public interface Converter<T> {
    
    /**
     * Definition from a constant for the used delimiter ({@code ;}) in context 
     * from this {@code Interface}.
     * 
     * @since  0.6.0
     * @author Naoghuman
     */
    public static final String DELIMITER = ";"; // NOI18N
    
    /**
     * Converts the internal values (separated with the delimiter {@code ';'}) 
     * from the given object to a {@link java.lang.String}.
     * <br>
     * To separate the internal values the developer can use the constant 
     * {@link com.github.naoghuman.lib.database.core.Converter#DELIMITER}.
     * <p>
     * For example converting the JavaFX color {@link javafx.scene.paint.Color#BLACK} with 
     * {@link com.github.naoghuman.lib.database.core.ConverterFacade#getColorConverter()} 
     * will returns the String {@code 0.0;0.0;0.0;1.0}.
     * 
     * @param  value the object which internal values should be convert to a {@code String}.
     * @return a {@code String} which presented the internal values from the given object.
     * @throws java.lang.NullPointerException if value is {@code NULL}.
     * @since  0.6.0
     * @author Naoghuman
     * @see    com.github.naoghuman.lib.database.core.Converter#DELIMITER
     * @see    com.github.naoghuman.lib.database.core.ConverterFacade#getColorConverter()
     * @see    java.lang.String
     * @see    javafx.scene.paint.Color#BLACK
     */
    public String to(final T value);
    
    /**
     * Converts the {@code value} to the given class. If the {@link java.lang.String} 
     * can't converted to the given type then {@link java.util.Optional#empty()} 
     * will returned.
     * <p>
     * The string should be a {@code separated value list} separated with the 
     * delimiter {@code ';'}. See also 
     * {@link com.github.naoghuman.lib.database.core.Converter#DELIMITER}.
     * 
     * @param  value the object which should be converted returned to the given type.
     * @return an instance from the given class or {@link java.util.Optional#empty()}.
     * @throws java.lang.IllegalArgumentException if value is {@code EMPTY}.
     * @throws java.lang.NullPointerException if value is {@code NULL}.
     * @since  0.6.0
     * @author Naoghuman
     * @see    com.github.naoghuman.lib.database.core.Converter#DELIMITER
     * @see    java.lang.String
     * @see    java.util.Optional
     */
    public Optional<T> from(final String value);
    
}
