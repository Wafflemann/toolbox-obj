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
package com.lundellnet.toolbox.obj.elements.standard.configs;

import java.lang.reflect.Field;
import java.util.function.Supplier;

import com.lundellnet.toolbox.obj.data_access.DataPoint;
import com.lundellnet.toolbox.obj.data_access.configs.StandardDataAccessConf;
import com.lundellnet.toolbox.obj.element.configs.AbstractEnumElementConf;

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
