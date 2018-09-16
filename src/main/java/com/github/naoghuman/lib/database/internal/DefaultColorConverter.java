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
import javafx.scene.paint.Color;

/**
 *
 * @author Naoghuman
 * @since  0.6.0
 */
public final class DefaultColorConverter extends Converter<Color> {

    @Override
    public String to(final Color value) {
        DefaultValidator.requireNonNull(value);
        
        final StringBuilder sb = new StringBuilder();
        sb.append(";"); // NOI18N
        sb.append(value.getHue());
        sb.append(";"); // NOI18N
        sb.append(value.getSaturation());
        sb.append(";"); // NOI18N
        sb.append(value.getBrightness());
        sb.append(";"); // NOI18N
        sb.append(value.getOpacity());
        
        return sb.toString();
    }

    @Override
    public Optional<Color> from(final String value) {
        DefaultValidator.requireNonNullAndNotEmpty(value);
        
        Optional<Color> optional = Optional.empty();
        
        try {
            final String[] splitted = value.split(";"); // NOI18N
            if (splitted.length != 4) {
                throw new IllegalArgumentException(String.format(
                        "Can't splitt '%s' into 4 parts with delimeter ';'.", // NOI18N
                        value)); // NOI18N
            }

            final double hue        = Double.parseDouble(splitted[0]);
            final double saturation = Double.parseDouble(splitted[1]);
            final double brightness = Double.parseDouble(splitted[2]);
            final double opacity    = Double.parseDouble(splitted[3]);

            optional = Optional.ofNullable(Color.hsb(hue, saturation, brightness, opacity));
        } catch (Exception ex) {
            LoggerFacade.getDefault().error(this.getClass(),
                    String.format("Error by parsing the values from '%s' to Double.", value), // NOI18N
                    ex);
        }
        
        return optional;
    }
    
}
