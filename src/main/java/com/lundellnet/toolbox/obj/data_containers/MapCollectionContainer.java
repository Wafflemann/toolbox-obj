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
package com.lundellnet.toolbox.obj.data_containers;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import com.lundellnet.toolbox.obj.data_access.configurables.ConfigurableDataAccess;
import com.lundellnet.toolbox.obj.data_containers.compilation.MapCollectionBuilder;

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
