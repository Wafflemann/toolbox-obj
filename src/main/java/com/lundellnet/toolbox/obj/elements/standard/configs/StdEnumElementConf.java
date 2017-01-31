/*
 Copyright 2017 Appropriate Technologies LLC.

 This file is part of toolbox-obj, a component of the Lundellnet Java Toolbox.

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.lundellnet.toolbox.obj.elements.standard.configs;

import java.lang.reflect.Field;
import java.util.function.Supplier;

import com.lundellnet.toolbox.obj.data_access.DataPoint;
import com.lundellnet.toolbox.obj.data_access.configs.StandardDataAccessConf;
import com.lundellnet.toolbox.obj.elements.configs.AbstractEnumElementConf;

public class StdEnumElementConf <T, D extends Enum<D>>
		extends AbstractEnumElementConf<T, T, D>
		implements StandardDataAccessConf<T>
{
	private final DataPoint<T, T> dataPoint;
	
	public StdEnumElementConf(
			Class<?> parentClass, Supplier<?> parentSupplier, Field elementField,
			Class<D> enumClass, Field enumConstField
	) {
		super(enumClass, enumConstField);
		
		this.dataPoint = StandardDataAccessConf.<T>dataPointBuilder().build(parentClass, parentSupplier, elementField);
	}

	@Override
	public DataPoint<T, T> dataPoint() {
		return dataPoint;
	}
}
