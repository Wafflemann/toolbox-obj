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
package com.lundellnet.toolbox.obj.parsers;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import com.lundellnet.toolbox.obj.collections.DataElementCollection;
import com.lundellnet.toolbox.obj.data_access.configurables.ConfigurableDataAccess;
import com.lundellnet.toolbox.obj.parsers.compilers.DataParserCompiler;
import com.lundellnet.toolbox.obj.parsers.generators.ElementCollectionGenerator;

public interface DataParser <T, C extends DataElementCollection<R, E>, E extends ConfigurableDataAccess<?>, R>
		extends ParsingTools.Parser<T, C, E, R>
{	
	public static <T, C extends DataElementCollection<R, E>, E extends ConfigurableDataAccess<?>, R>
			DataParserCompiler<T, C, E, R> compiler(ElementCollectionGenerator<C, E, R> cComp)
	{ return (e, i, a, c, f) -> new ParsingTools.DataParserImpl<T, C, E, R>(cComp.generate(), e, i, a, c, f); }
	
	default void accept(T t) {
		BiConsumer<E, T> a = accepter();
		BiFunction<C, T, E> e = elementInit();
		C col = collection();

		a.accept(ParsingTools.checkNull(e.apply(col, t), ParsingTools.PARSING_ELEM_INIT_MSG, ParsingTools.ParsePhase.EXEC), t);
	}
	
	default C combine(T t1, T t2) {
		BinaryOperator<E> c = checker();
		BiFunction<C, T, E> i = identifier();
		C col = collection();
		
		c.apply(
			ParsingTools.checkNull(i.apply(col, t1), ParsingTools.PARSING_ELEM_IDENT_MSG, ParsingTools.ParsePhase.EXEC),
			ParsingTools.checkNull(i.apply(col, t2), ParsingTools.PARSING_ELEM_IDENT_MSG, ParsingTools.ParsePhase.EXEC)
		);
		
		return col;
	}
	
	default R transform() {
		Function<C, R> f = finisher();
		C col = collection();
		
		return f.apply(col);
	}
}
