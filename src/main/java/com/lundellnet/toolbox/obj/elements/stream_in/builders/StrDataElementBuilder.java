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
package com.lundellnet.toolbox.obj.elements.stream_in.builders;

import java.lang.reflect.Field;
import java.util.function.Supplier;

import com.lundellnet.toolbox.obj.collectors.ParsingCollector;
import com.lundellnet.toolbox.obj.data_access.configs.CollectingDataAccessConf;
import com.lundellnet.toolbox.obj.data_access.configurables.CollectingDataAccess;
import com.lundellnet.toolbox.obj.elements.builders.ElementBuilder;
import com.lundellnet.toolbox.obj.elements.stream_in.configs.StrDataElementConf;

public interface StrDataElementBuilder <I, O, E extends CollectingDataAccess<I, O, ?>>
		extends ElementBuilder<CollectingDataAccessConf<I, O>, E>	
{
	default E build(Class<?> parentClass, Supplier<?> parentSupplier, Field elementField, ParsingCollector<I, ?, O> collector)
			{ return build(new StrDataElementConf<>(parentClass, parentSupplier, elementField, collector)); }
}
