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
package com.lundellnet.toolbox.obj.elements.stream_in.configs;

import java.lang.reflect.Field;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.lundellnet.toolbox.obj.collectors.ParsingCollector;
import com.lundellnet.toolbox.obj.data_access.DataPoint;
import com.lundellnet.toolbox.obj.data_access.configs.CollectingDataAccessConf;

public class StrDataElementConf <I, O>
		implements CollectingDataAccessConf<I, O>
{
	private final DataPoint<Stream<I>, O> dataPoint;
	
	public StrDataElementConf(
			Class<?> parentClass, Supplier<?> parentSupplier, Field elementField, ParsingCollector<I, ?, O> collector
	) {
		this.dataPoint = CollectingDataAccessConf.<I, O>dataPointBuilder().build(parentClass, parentSupplier, elementField, collector);
	}

	@Override
	public DataPoint<Stream<I>, O> dataPoint() {
		return dataPoint;
	}
}
