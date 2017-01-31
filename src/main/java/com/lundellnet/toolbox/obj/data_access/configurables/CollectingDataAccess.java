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
package com.lundellnet.toolbox.obj.data_access.configurables;

import java.util.stream.Stream;

import com.lundellnet.toolbox.obj.data_access.configs.CollectingDataAccessConf;

public interface CollectingDataAccess <I, O, C extends CollectingDataAccessConf<I, O>>
		extends ConfigurableDataAccess<C>
{
	default O get() {
		return conf().dataPoint().getter().get();
	}

	default void set(Stream<I> s) {
		conf().dataPoint().setter().accept(s);
	}
}
