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
import java.util.regex.PatternSyntaxException;

/**
 * Default implementation for the {@code Interface} {@link com.github.naoghuman.lib.database.core.Converter} 
 * which allowed to convert a {@link java.time.LocalDateTime} to a {@link java.lang.String} and 
 * back again.
 * <p>
 * Converts to {@code String} and back with following pattern:
 * <ul>
 * <li>value.getYear()</li>
 * <li>DELIMITER</li>
 * <li>value.getMonthValue()</li>
 * <li>DELIMITER</li>
 * <li>value.getDayOfMonth()</li>
 * <li>DELIMITER</li>
 * <li>value.getHour()</li>
 * <li>DELIMITER</li>
 * <li>value.getMinute()</li>
 * <li>DELIMITER</li>
 * <li>value.getSecond()</li>
 * <li>DELIMITER</li>
 * <li>value.getNano()</li>
 * </ul>
 *
 * @author Naoghuman
 * @since  0.6.0
 * @see    com.github.naoghuman.lib.database.core.Converter
 * @see    java.lang.String
 * @see    java.time.LocalDateTime
 */
public final class DefaultLocalDateTimeConverter implements Converter<LocalDateTime> {

    @Override
    public String to(final LocalDateTime value) {
        DefaultValidator.requireNonNull(value);
        
        final StringBuilder sb = new StringBuilder();
        sb.append(value.getYear());
        sb.append(DELIMITER);
        sb.append(value.getMonthValue());
        sb.append(DELIMITER);
        sb.append(value.getDayOfMonth());
        sb.append(DELIMITER);
        sb.append(value.getHour());
        sb.append(DELIMITER);
        sb.append(value.getMinute());
        sb.append(DELIMITER);
        sb.append(value.getSecond());
        sb.append(DELIMITER);
        sb.append(value.getNano());
        
        return sb.toString();
    }

    @Override
    public Optional<LocalDateTime> from(final String value) {
        DefaultValidator.requireNonNullAndNotEmpty(value);
        
        Optional<LocalDateTime> optional = Optional.empty();
        String[]                splitted = new String[0];
        try {
            splitted = value.split(DELIMITER);
        } catch (PatternSyntaxException pse) {
            LoggerFacade.getDefault().warn(this.getClass(),
                    String.format("Can't split '%s' with delimiter '%s'.", value, DELIMITER), // NOI18N
                    pse);
        }
        
        if (splitted.length != 7) {
            throw new IllegalArgumentException(String.format(
                    "Can't split '%s' into 7 parts with delimiter '%s'.", value, DELIMITER)); // NOI18N
        }
        
        try {
            final int year         = Integer.parseInt(splitted[0]);
            final int month        = Integer.parseInt(splitted[1]);
            final int dayOfMonth   = Integer.parseInt(splitted[2]);
            final int hour         = Integer.parseInt(splitted[3]);
            final int minute       = Integer.parseInt(splitted[4]);
            final int second       = Integer.parseInt(splitted[5]);
            final int nanoOfSecond = Integer.parseInt(splitted[6]);
            
            optional = Optional.ofNullable(LocalDateTime.of(year, month, dayOfMonth, hour, minute, second, nanoOfSecond));
        } catch (Exception ex) {
            LoggerFacade.getDefault().warn(this.getClass(),
                    String.format("Error by parsing the values from '%s' to LocalDateTime.", value), // NOI18N
                    ex);
        }
        
        return optional;
    }
    
}
