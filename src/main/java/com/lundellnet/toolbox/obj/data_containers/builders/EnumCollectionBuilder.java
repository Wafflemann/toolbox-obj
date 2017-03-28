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
package com.lundellnet.toolbox.obj.data_containers.builders;

import java.util.function.BiFunction;

import com.lundellnet.toolbox.obj.data_containers.CollectionContainer;
import com.lundellnet.toolbox.obj.elements.EnumElement;

@FunctionalInterface
public interface EnumCollectionBuilder <D extends Enum<D>, E extends EnumElement<?, ?, ?, D>> {
	CollectionContainer<D, E> build(Class<D> e, boolean p, BiFunction<E, E, E> m);
	
	default CollectionContainer<D, E> build(Class<D> e, BiFunction<E, E, E> m) {
		return build(e, true, m);
	}
	
	default CollectionContainer<D, E> build(Class<D> e, boolean p) {
		return build(e, p, (l, r) -> l);
	}
	
	default CollectionContainer<D, E> build(Class<D> e) {
		return build(e, true, (l, r) -> l);
	}
}
