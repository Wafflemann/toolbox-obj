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

import java.util.EnumMap;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import com.lundellnet.toolbox.obj.data_containers.compilation.EnumCollectionBuilder;
import com.lundellnet.toolbox.obj.elements.EnumElement;

public class EnumCollectionContainer <D extends Enum<D>, E extends EnumElement<?, ?, ?, D>>
		implements CollectionContainer<D, E>
{
	public static <D extends Enum<D>, E extends EnumElement<?, ?, ?, D>>
			EnumCollectionBuilder<D, E> builder()
	{ return EnumCollectionContainer<D, E>::new; }
	
	private final boolean parallelElementStream;
	private final BiFunction<E, E, E> mergeBehavior;
	private final EnumMap<D, E> elements;

	EnumCollectionContainer(Class<D> enumClass, boolean parallelElementStream, BiFunction<E, E, E> mergeBehavior) {
		this.parallelElementStream = parallelElementStream;
		this.mergeBehavior = mergeBehavior;
		this.elements = new EnumMap<>(enumClass);
	}

	@Override
	public void includeElement(E e) {
		elements.merge(e.getEnumConstant(), e, mergeBehavior);
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
