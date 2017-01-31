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
package com.lundellnet.toolbox.obj.data_access.configs;

import com.lundellnet.toolbox.obj.data_access.builders.DataPointBuilder;

@FunctionalInterface
public interface StandardDataAccessConf <T>
		extends DataAccessConf<T, T>
{
	static <T>
			DataPointBuilder<T, T> dataPointBuilder()
	{ return DataAccess.dataPointBasicBuilder(); }
}
