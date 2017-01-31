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
package com.lundellnet.toolbox.obj.parsers.compilers;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import com.lundellnet.toolbox.obj.collections.DataElementCollection;
import com.lundellnet.toolbox.obj.collectors.ParsingCollector;
import com.lundellnet.toolbox.obj.data_access.configurables.ConfigurableDataAccess;
import com.lundellnet.toolbox.obj.parsers.DataParser;

@FunctionalInterface
public interface DataParserCompiler <T, C extends DataElementCollection<R, E>, E extends ConfigurableDataAccess<?>, R> {
	DataParser<T, C, E, R> compile(BiFunction<C, T, E> e, BiFunction<C, T, E> i, BiConsumer<E, T> a, BinaryOperator<E> c, Function<C, R> f);
	
	default ParsingCollector<T, C, R> asCollector(BiFunction<C, T, E> e, BiFunction<C, T, E> i, BiConsumer<E, T> a, BinaryOperator<E> c, Function<C, R> f) {
		DataParser<T, C, E, R> p = compile(e, i, a, c, f); 
		return () -> () -> p;
	}
	
	default DataParser<T, C, E, R> compile(BiFunction<C, T, E> e, BiFunction<C, T, E> i, BiConsumer<E, T> a, Function<C, R> f) {
		return compile(e, i, a, (e1, e2) -> e1, f);
	}
	
	default ParsingCollector<T, C, R> asCollector(BiFunction<C, T, E> e, BiFunction<C, T, E> i, BiConsumer<E, T> a, Function<C, R> f) {
		DataParser<T, C, E, R> p = compile(e, i, a, f);
		return () -> () -> p;
	}
	
	default DataParser<T, C, E, R> compile(BiFunction<C, T, E> e, BiFunction<C, T, E> i, BiConsumer<E, T> a, BinaryOperator<E> c) {
		return compile(e, i, a, c, (_col) -> _col.getData());
	}
	
	default ParsingCollector<T, C, R> asCollector(BiFunction<C, T, E> e, BiFunction<C, T, E> i, BiConsumer<E, T> a, BinaryOperator<E> c) {
		DataParser<T, C, E, R> p = compile(e, i, a, c);
		return () -> () -> p;
	}
	
	default DataParser<T, C, E, R> compile(BiFunction<C, T, E> e, BiFunction<C, T, E> i, BiConsumer<E, T> a) {
		return compile(e, i, a, (e1, e2) -> e1, (_col) -> _col.getData());
	}
	
	default ParsingCollector<T, C, R> asCollector(BiFunction<C, T, E> e, BiFunction<C, T, E> i, BiConsumer<E, T> a) {
		DataParser<T, C, E, R> p = compile(e, i, a);
		return () -> () -> p;
	}
}
