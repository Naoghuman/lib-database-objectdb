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
package com.github.naoghuman.lib.database.internal;

import com.github.naoghuman.lib.database.core.Converter;
import com.github.naoghuman.lib.logger.core.LoggerFacade;
import java.util.Optional;
import java.util.regex.PatternSyntaxException;

import javafx.scene.paint.Color;

/**
 * Default implementation for the abstract class {@link com.github.naoghuman.lib.database.core.Converter} 
 * which allowed to convert a JavaFX {@link javafx.scene.paint.Color} to a {@link java.lang.String} and 
 * back again.
 * <p>
 * Converts to {@code String} and back with following pattern:
 * <ul>
 * <li>value.getHue()</li>
 * <li>DELIMITER</li>
 * <li>value.getSaturation()</li>
 * <li>DELIMITER</li>
 * <li>value.getBrightness()</li>
 * <li>DELIMITER</li>
 * <li>value.getOpacity()</li>
 * </ul>
 *
 * @author Naoghuman
 * @since  0.6.0
 * @see    com.github.naoghuman.lib.database.core.Converter
 * @see    java.lang.String
 * @see    javafx.scene.paint.Color
 */
public final class DefaultColorConverter extends Converter<Color> {

    @Override
    public String to(final Color value) {
        DefaultValidator.requireNonNull(value);
        
        final StringBuilder sb = new StringBuilder();
        sb.append(value.getHue());
        sb.append(DELIMITER);
        sb.append(value.getSaturation());
        sb.append(DELIMITER);
        sb.append(value.getBrightness());
        sb.append(DELIMITER);
        sb.append(value.getOpacity());
        
        return sb.toString();
    }

    @Override
    public Optional<Color> from(final String value) {
        DefaultValidator.requireNonNullAndNotEmpty(value);
        
        String[] splitted;
        try {
            splitted = value.split(DELIMITER);
        } catch (PatternSyntaxException pse) {
            LoggerFacade.getDefault().warn(this.getClass(), String.format(
                    "Can't split '%s' with delimiter '%s'. Return Optional.empty().", // NOI18N
                    value, DELIMITER));
            
            return Optional.empty();
        }
        
        if (splitted.length != 4) {
            LoggerFacade.getDefault().warn(this.getClass(), String.format(
                    "Can't split '%s' into 4 parts with delimiter '%s'. Return Optional.empty().", // NOI18N
                    value, DELIMITER));
            
            return Optional.empty();
        }
        
        Color color;
        try {
            final double hue        = Double.parseDouble(splitted[0]);
            final double saturation = Double.parseDouble(splitted[1]);
            final double brightness = Double.parseDouble(splitted[2]);
            final double opacity    = Double.parseDouble(splitted[3]);

            color = Color.hsb(hue, saturation, brightness, opacity);
        } catch (Exception ex) {
            LoggerFacade.getDefault().warn(this.getClass(), String.format(
                    "Error by parsing the values from '%s' to Double. Return Optional.empty().",  // NOI18N
                    value));
            
            return Optional.empty();
        }
        
        return Optional.of(color);
    }
    
}
