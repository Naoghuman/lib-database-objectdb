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

import java.util.Objects;

/**
 *
 * @author Naoghuman
 * @since  0.6.0
 */
public class DefaultValidator {
    
    /**
     * Validates if the attribute {@code value} isn't {@code NULL}.
     *
     * @author Naoghuman
     * @since  0.6.0
     * @param  value the attribute which should be validated.
     * @param  <T>   the type of the reference.
     * @throws NullPointerException if {@code (value == NULL)}.
     */
    public static <T> void requireNonNull(final T value) {
        Objects.requireNonNull(value, "The attribute [value] can't be NULL"); // NOI18N
    }
    
    /**
     * Validates if the attribute {@code value} isn't {@code NULL} and not {@code EMPTY}.
     *
     * @author Naoghuman
     * @since  0.6.0
     * @param  value the attribute which should be validated.
     * @throws NullPointerException     if {@code (value        == NULL)}.
     * @throws IllegalArgumentException if {@code (value.trim() == EMPTY)}.
     */
    public static void requireNonNullAndNotEmpty(final String value) {
        requireNonNull(value);
        
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException("The attribute [value] can't be EMPTY"); // NOI18N
        }
    }
    
}
