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
package com.lundellnet.toolbox.obj.elements.stream_in;

import java.util.stream.Stream;

import com.lundellnet.toolbox.obj.elements.LocatableElement;
import com.lundellnet.toolbox.obj.elements.stream_in.builders.StrEnumElementBuilder;
import com.lundellnet.toolbox.obj.elements.stream_in.configs.StrEnumElementConf;

@FunctionalInterface
public interface LocEnumDataStreamInElement <T, R, D extends Enum<D>>
		extends EnumDataStreamInElement<T, R, D>, LocatableElement<Stream<T>, R, StrEnumElementConf<T, R, D>>
{
	public static <T, R, D extends Enum<D>>
			StrEnumElementBuilder<T, R, D, LocEnumDataStreamInElement<T, R, D>> builder()
	{ return LocEnumDataStreamInElement::createElement; }
	
	@SuppressWarnings("unchecked")
	public static <T, R, D extends Enum<D>>
			LocEnumDataStreamInElement<T, R, D> createElement(StrEnumElementConf<?, ?, D> elementConf)
	{ return () -> (StrEnumElementConf<T, R, D>) elementConf; }
}