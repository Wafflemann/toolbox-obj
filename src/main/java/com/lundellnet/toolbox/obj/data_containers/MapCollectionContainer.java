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
package com.lundellnet.toolbox.obj.data_containers;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import com.lundellnet.toolbox.obj.data_access.configurables.ConfigurableDataAccess;
import com.lundellnet.toolbox.obj.data_containers.builders.MapCollectionBuilder;

public class MapCollectionContainer <D, E extends ConfigurableDataAccess<?>>
		implements CollectionContainer<D, E>
{
	public static <D, E extends ConfigurableDataAccess<?>>
			MapCollectionBuilder<D, E> builder()
	{ return MapCollectionContainer<D, E>::new; }
	
	private final Function<E, D> identifier;
	private final boolean parallelElementStream;
	private final BiFunction<E, E, E> mergeBehavior;
	private final ConcurrentMap<D, E> elements;

	MapCollectionContainer(Function<E, D> identifier, boolean parallelElementStream, BiFunction<E, E, E> mergeBehavior) {
		this.identifier = identifier;
		this.parallelElementStream = parallelElementStream;
		this.mergeBehavior = mergeBehavior;
		this.elements = new ConcurrentHashMap<>();
	}

	@Override
	public void includeElement(E e) {
		elements.merge(identifier.apply(e), e, mergeBehavior);
	}

	@Override
	public E retreiveElement(D ident) {
		return elements.get(ident);
	}

	@Override
	public Stream<E> getStream() {
		return (parallelElementStream ? elements.entrySet().parallelStream() : elements.entrySet().stream())
				.map((e) -> e.getValue());
	}
}
