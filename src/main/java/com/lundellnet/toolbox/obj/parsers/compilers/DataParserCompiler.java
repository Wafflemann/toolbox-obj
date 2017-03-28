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
