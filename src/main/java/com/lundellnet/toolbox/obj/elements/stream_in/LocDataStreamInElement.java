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

import com.lundellnet.toolbox.obj.data_access.configs.CollectingDataAccessConf;
import com.lundellnet.toolbox.obj.data_access.configurables.CollectingDataAccess;
import com.lundellnet.toolbox.obj.elements.LocatableElement;
import com.lundellnet.toolbox.obj.elements.stream_in.builders.StrDataElementBuilder;

@FunctionalInterface
public interface LocDataStreamInElement <T, R>
		extends CollectingDataAccess<T, R, CollectingDataAccessConf<T, R>>, LocatableElement<Stream<T>, R, CollectingDataAccessConf<T, R>>
{
	public static <T, R>
			StrDataElementBuilder<T, R, LocDataStreamInElement<T, R>> builder()
	{ return LocDataStreamInElement::createElement; }
	
	@SuppressWarnings("unchecked")
	public static <T, R>
			LocDataStreamInElement<T, R> createElement(CollectingDataAccessConf<?, ?> elementConf)
	{ return () -> (CollectingDataAccessConf<T, R>) elementConf; }
}
