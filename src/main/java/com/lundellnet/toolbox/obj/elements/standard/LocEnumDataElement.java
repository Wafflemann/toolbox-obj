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

import com.lundellnet.toolbox.obj.elements.LocatableElement;
import com.lundellnet.toolbox.obj.elements.standard.builders.StdEnumElementBuilder;
import com.lundellnet.toolbox.obj.elements.standard.configs.StdEnumElementConf;

@FunctionalInterface
public interface LocEnumDataElement <T, D extends Enum<D>>
		extends EnumDataElement<T, D>, LocatableElement<T, T, StdEnumElementConf<T, D>>
{
	public static <T, D extends Enum<D>>
			StdEnumElementBuilder<T, D, LocEnumDataElement<T, D>> builder() 
	{ return LocEnumDataElement::createElement; }
	
	@SuppressWarnings("unchecked")
	public static <T, D extends Enum<D>>
			LocEnumDataElement<T, D> createElement(StdEnumElementConf<?, D> elementConfig)
	{ return () -> (StdEnumElementConf<T, D>) elementConfig; }
}
