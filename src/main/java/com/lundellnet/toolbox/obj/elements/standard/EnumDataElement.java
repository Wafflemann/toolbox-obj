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
package com.lundellnet.toolbox.obj.elements.standard;

import com.lundellnet.toolbox.obj.data_access.configurables.StandardDataAccess;
import com.lundellnet.toolbox.obj.elements.EnumElement;
import com.lundellnet.toolbox.obj.elements.standard.builders.StdEnumElementBuilder;
import com.lundellnet.toolbox.obj.elements.standard.configs.StdEnumElementConf;

@FunctionalInterface
public interface EnumDataElement <T, D extends Enum<D>>
		extends StandardDataAccess<T, StdEnumElementConf<T, D>>, EnumElement<T, T, StdEnumElementConf<T, D>, D>
{
	public static <T, D extends Enum<D>>
			StdEnumElementBuilder<T, D, EnumDataElement<T, D>> builder()
	{ return EnumDataElement::createElement; }
	
	@SuppressWarnings("unchecked")
	public static <T, D extends Enum<D>>
			EnumDataElement<T, D> createElement(StdEnumElementConf<?, D> elementConfig)
	{ return () -> (StdEnumElementConf<T, D>) elementConfig; }
}
