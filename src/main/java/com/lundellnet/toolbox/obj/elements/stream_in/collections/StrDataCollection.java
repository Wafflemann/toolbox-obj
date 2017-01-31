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
package com.lundellnet.toolbox.obj.elements.stream_in.collections;

import java.lang.reflect.Field;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.lundellnet.toolbox.obj.collections.CollectingDataElementCollection;
import com.lundellnet.toolbox.obj.collections.configurables.DataCollection;
import com.lundellnet.toolbox.obj.collections.generators.ParsingCollectorGenerator;
import com.lundellnet.toolbox.obj.data_access.configurables.CollectingDataAccess;
import com.lundellnet.toolbox.obj.elements.stream_in.builders.StrDataElementBuilder;
import com.lundellnet.toolbox.obj.elements.stream_in.configs.StrDataCollectionConf;

@FunctionalInterface
public interface StrDataCollection <R, E extends CollectingDataAccess<?, ?, ?>>
		extends DataCollection<StrDataCollectionConf<R, E, ?>, R, E>, CollectingDataElementCollection<R, E>
{
	@SuppressWarnings("unchecked")
	default <I, O>
			E createElement(Field elementField, Class<O> resultClass, Supplier<O> resultSupplier)
	{ return (E) ((StrDataElementBuilder<I, O, ?>) conf().elementBuilder()).build(getDataClass(), getDataSupplier(), elementField, conf().<I, O>colGen().generate(resultClass, resultSupplier)); }
	
	default <I, O>
			ParsingCollectorGenerator<I, O> collectorGenerator()
	{ return conf().colGen(); }
	
	@Override
	default
			void includeElement(E e)
	{ conf().collectionStream().includeElement(e); }

	@Override
	default
			Stream<E> elements()
	{ return conf().collectionStream().getStream(); }

	@Override
	default
			Class<R> getDataClass()
	{ return conf().resultClass(); }

	@Override
	default
			Supplier<R> getDataSupplier()
	{ return conf().resultSupplier(); }

	@Override
	default
			R getData()
	{ return conf().resultSupplier().get(); }
}
