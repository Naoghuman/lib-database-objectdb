/*
 * Copyright (C) 2014 Naoghuman
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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * JPA 2 | Dynamic Queries Vs Named Queries
 *  - http://www.javacodegeeks.com/2013/06/jpa-2-dynamic-queries-vs-named-queries.html
 * 
 * 
 *
 * @author Naoghuman
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "PrimitiveTypesEntity")
@NamedQueries({
    @NamedQuery(// need crud
            name = "PrimitiveTypesEntity.findAll", 
            query = "SELECT p from PrimitiveTypesEntity p")
})
public class PrimitiveTypesEntity implements Externalizable {
    
    private static final long serialVersionUID = 1L;
    
    // START -------------------------------------------------------------------
    private IntegerProperty id;
    private int _id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    public final int getId() {
        if (this.id == null) {
            return _id;
        } else {
            return id.get();
        }
    }

    public final void setId(int id) {
        if (this.id == null) {
            _id = id;
        } else {
            this.id.set(id);
        }
    }

    public IntegerProperty idProperty() {
        if (id == null) {
            id = new SimpleIntegerProperty(this, "Id", _id);
        }
        return id;
    }
    // END   -------------------------------------------------------------------
    
    // START -------------------------------------------------------------------
    private DoubleProperty doubleValue;
    private double _doubleValue;

    @Column(name = "DoubleValue")
    public final double getDoubleValue() {
        if (this.doubleValue == null) {
            return _doubleValue;
        } else {
            return doubleValue.get();
        }
    }

    public final void setDoubleValue(double doubleValue) {
        if (this.doubleValue == null) {
            _doubleValue = doubleValue;
        } else {
            this.doubleValue.set(doubleValue);
        }
    }

    public DoubleProperty doubleValueProperty() {
        if (doubleValue == null) {
            doubleValue = new SimpleDoubleProperty(this, "DoubleValue", _doubleValue);
        }
        return doubleValue;
    }
    // END   -------------------------------------------------------------------
    
    // START -------------------------------------------------------------------
    private LongProperty longValue;
    private long _longValue;

    @Column(name = "LongValue")
    public final long getLongValue() {
        if (this.longValue == null) {
            return _longValue;
        } else {
            return longValue.get();
        }
    }

    public final void setLongValue(long longValue) {
        if (this.longValue == null) {
            _longValue = longValue;
        } else {
            this.longValue.set(longValue);
        }
    }

    public LongProperty longValueProperty() {
        if (longValue == null) {
            longValue = new SimpleLongProperty(this, "LongValue", _longValue);
        }
        return longValue;
    }
    // END   -------------------------------------------------------------------

    // START -------------------------------------------------------------------
    private BooleanProperty booleanValue;
    private boolean _booleanValue;

    @Column(name = "BooleanValue")
    public final boolean getBooleanValue() {
        if (this.booleanValue == null) {
            return _booleanValue;
        } else {
            return booleanValue.get();
        }
    }

    public final void setBooleanValue(boolean booleanValue) {
        if (this.booleanValue == null) {
            _booleanValue = booleanValue;
        } else {
            this.booleanValue.set(booleanValue);
        }
    }

    public BooleanProperty booleanValueProperty() {
        if (booleanValue == null) {
            booleanValue = new SimpleBooleanProperty(this, "BooleanValue", _booleanValue);
        }
        return booleanValue;
    }
    // END   -------------------------------------------------------------------

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(this.getId());
        out.writeDouble(this.getDoubleValue());
        out.writeLong(this.getLongValue());
        out.writeBoolean(this.getBooleanValue());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setId(in.readInt());
        this.setDoubleValue(in.readDouble());
        this.setLongValue(in.readLong());
        this.setBooleanValue(in.readBoolean());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[Id=").append(this.getId()).append("]");
        sb.append("[Double=").append(this.getDoubleValue()).append("]");
        sb.append("[Long=").append(this.getLongValue()).append("]");
        sb.append("[Boolean=").append(this.getBooleanValue()).append("]");
        
        return sb.toString();
    }
    
}
