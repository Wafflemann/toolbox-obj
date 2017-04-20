/*
 Copyright 2017 Appropriate Technologies LLC.

 This file is part of toolbox-obj, a component of the Lundellnet Java Toolbox.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package com.lundellnet.toolbox.obj.elements.stream_in.configs;

import java.lang.reflect.Field;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.lundellnet.toolbox.obj.collectors.ParsingCollector;
import com.lundellnet.toolbox.obj.data_access.DataAccessTools;
import com.lundellnet.toolbox.obj.data_access.DataPoint;
import com.lundellnet.toolbox.obj.data_access.configs.DataAccessConf;
import com.lundellnet.toolbox.obj.elements.configs.AbstractEnumElementConf;

public class StrEnumElementConf <I, O, D extends Enum<D>>
		extends AbstractEnumElementConf<Stream<I>, O, D>
		implements DataAccessConf<Stream<I>, O>
{
	private final DataPoint<Stream<I>, O> dataPoint;
	
	public StrEnumElementConf(
			Class<?> parentClass, Supplier<?> parentSupplier, Field elementField,
			ParsingCollector<I, ?, ?, O> collector, Class<D> enumClass, Field enumConstField
	) {
		super(enumClass, enumConstField);
		
		this.dataPoint = DataAccessTools.<I, O>collectingDataPointObjectBuilder().build(parentClass, parentSupplier, elementField, collector);
	}
	
	@Override
	public DataPoint<Stream<I>, O> dataPoint() {
		return dataPoint;
	}
}
