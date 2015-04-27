/*
 * Copyright (C) 2015 PRo
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
package de.pro.lib.database;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
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

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "LightModel")
@NamedQueries({
    @NamedQuery(// need crud
            name = "LightModel.findAll", 
            query = "SELECT lm FROM LightModel lm"),
    @NamedQuery(
            name = "LightModel.findLight", 
            query = "SELECT lm.Id, lm.DoubleValue FROM LightModel lm")
})
public class LightModel  implements Externalizable {
    
    private static final long serialVersionUID = 1L;
    
    // START -------------------------------------------------------------------
    private IntegerProperty id;
    private int _id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
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

    @Column(name = "DoubleValue", nullable = false)
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
    private Long _longValue = null;

    @Column(name = "LongValue", nullable = true)
    public final Long getLongValue() {
        if (this.longValue == null) {
            return _longValue;
        } else {
            return longValue.get();
        }
    }

    public final void setLongValue(Long longValue) {
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(this.getId());
        out.writeDouble(this.getDoubleValue());
        out.writeLong(this.getLongValue());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setId(in.readInt());
        this.setDoubleValue(in.readDouble());
        this.setLongValue(in.readLong());
    }
    
}
