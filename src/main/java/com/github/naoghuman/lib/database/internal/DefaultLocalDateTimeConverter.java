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
import java.time.LocalDateTime;
import java.util.Optional;

/**
 *
 * @author Naoghuman
 * @since  0.6.0
 */
public final class DefaultLocalDateTimeConverter extends Converter<LocalDateTime> {

    @Override
    public String to(final LocalDateTime value) {
        DefaultValidator.requireNonNull(value);
        
        final StringBuilder sb = new StringBuilder();
        sb.append(";"); // NOI18N
        sb.append(value.getYear());
        sb.append(";"); // NOI18N
        sb.append(value.getMonthValue());
        sb.append(";"); // NOI18N
        sb.append(value.getDayOfMonth());
        sb.append(";"); // NOI18N
        sb.append(value.getHour());
        sb.append(";"); // NOI18N
        sb.append(value.getMinute());
        sb.append(";"); // NOI18N
        sb.append(value.getSecond());
        sb.append(";"); // NOI18N
        sb.append(value.getNano());
        
        return sb.toString();
    }

    @Override
    public Optional<LocalDateTime> from(final String value) {
        DefaultValidator.requireNonNullAndNotEmpty(value);
        
        Optional<LocalDateTime> optional = Optional.empty();
        
        try {
            final String[] splitted = value.split(";"); // NOI18N
            if (splitted.length != 7) {
                throw new IllegalArgumentException(String.format(
                        "Can't splitt '%s' into 7 parts with delimeter ';'.", // NOI18N
                        value)); // NOI18N
            }
            
            final int year         = Integer.parseInt(splitted[0]);
            final int month        = Integer.parseInt(splitted[1]);
            final int dayOfMonth   = Integer.parseInt(splitted[2]);
            final int hour         = Integer.parseInt(splitted[3]);
            final int minute       = Integer.parseInt(splitted[4]);
            final int second       = Integer.parseInt(splitted[5]);
            final int nanoOfSecond = Integer.parseInt(splitted[6]);
            
            optional = Optional.ofNullable(LocalDateTime.of(year, month, dayOfMonth, hour, minute, second, nanoOfSecond));
        } catch (Exception ex) {
            LoggerFacade.getDefault().error(this.getClass(),
                    String.format("Error by parsing the values from '%s' to LocalDateTime.", value), // NOI18N
                    ex);
        }
        
        return optional;
    }
    
}
