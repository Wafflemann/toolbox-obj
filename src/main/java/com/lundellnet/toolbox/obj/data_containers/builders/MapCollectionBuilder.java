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
import java.util.function.Function;

import com.lundellnet.toolbox.obj.data_access.configurables.ConfigurableDataAccess;
import com.lundellnet.toolbox.obj.data_containers.CollectionContainer;

@FunctionalInterface
public interface MapCollectionBuilder <D, E extends ConfigurableDataAccess<?>> {
	CollectionContainer<D, E> build(Function<E, D> i, boolean p, BiFunction<E, E, E> m);
	
	default CollectionContainer<D, E> build(Function<E, D> i, BiFunction<E, E, E> m) {
		return build(i, true, m);
	}
	
	default CollectionContainer<D, E> build(Function<E, D> i, boolean p) {
		return build(i, p, (l, r) -> l);
	}
	
	default CollectionContainer<D, E> build(Function<E, D> i) {
		return build(i, true, (l, r) -> l);
	}
}
