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
