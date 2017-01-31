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
package com.lundellnet.toolbox.obj.collectors;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import com.lundellnet.toolbox.obj.collections.DataElementCollection;
import com.lundellnet.toolbox.obj.parsers.DataParser;

@FunctionalInterface
public interface ParsingCollector <T, C extends DataElementCollection<R, ?>, R>
		extends CoreCollector<T, DataParser<T, C, ?, R>, C, R>
{
	@Override
	default BiConsumer<DataParser<T, C, ?, R>, T> accumulator() {
		return (p, t) -> p.accept(t);
	}
	
	@Override
	default BinaryOperator<DataParser<T, C, ?, R>> combiner() {
		return (p1, p2) -> p1;
	}
	
	@Override
	default Function<DataParser<T, C, ?, R>, R> finisher() {
		return (p) -> p.transform();
	}
}
