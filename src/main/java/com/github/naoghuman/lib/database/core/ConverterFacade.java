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

import com.github.naoghuman.lib.database.internal.DefaultColorConverter;
import com.github.naoghuman.lib.database.internal.DefaultLocalDateTimeConverter;
import java.time.LocalDateTime;
import java.util.Optional;
import javafx.scene.paint.Color;

/**
 *
 * @author Naoghuman
 * @since  0.6.0
 */
public final class ConverterFacade {
    
    private static final Optional<ConverterFacade> INSTANCE = Optional.of(new ConverterFacade());

    /**
     * Returns a singleton instance from the class {@code ConverterFacade}.
     * 
     * @return a singleton instance from the class {@code ConverterFacade}.
     */
    public static final ConverterFacade getDefault() {
        return INSTANCE.get();
    }
    
    private Converter<Color>         colorConverter;
    private Converter<LocalDateTime> localDateTimeConverter;
    
    private ConverterFacade() {
        this.initialize();
    }

    private void initialize() {
        colorConverter         = new DefaultColorConverter();
        localDateTimeConverter = new DefaultLocalDateTimeConverter();
    }

    public Converter<Color> getColorConverter() {
        return colorConverter;
    }

    public Converter<LocalDateTime> getLocalDateTimeConverter() {
        return localDateTimeConverter;
    }
    
}
