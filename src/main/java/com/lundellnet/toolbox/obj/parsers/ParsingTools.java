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
package com.lundellnet.toolbox.obj.parsers;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import com.lundellnet.toolbox.obj.Reflect;
import com.lundellnet.toolbox.obj.collections.DataElementCollection;
import com.lundellnet.toolbox.obj.data_access.configurables.ConfigurableDataAccess;

public class ParsingTools {
	static final String PARSING_ELEM_INIT_MSG = "Error running Element Intializer. No Element supplier to Data Parser.";
	static final String PARSING_ELEM_IDENT_MSG = "Error running Element Identifier. No Element supplied to Data Parser.";
	static final String SETUP_COL_MSG = "Cannot build Parsing Data. No Collection supplied.";
	static final String SETUP_ELEM_INIT_MSG = "Cannot build Parsing Data. No Element Initializer supplied.";
	static final String SETUP_ELEM_IDENT_MSG = "Cannot build Parsing Data. No Element Identifier supplied.";
	static final String SETUP_DATA_ACCPT_MSG = "Cannot build Parsing Data, No Data Accepter supplied.";
	static final String SETUP_ELEM_CHK_MSG = "Cannot build Parsing Data. No Data Element Checker supplied.";
	static final String SETUP_COL_FIN_MSG = "Cannot build Parsing Data. No Collection Finisher supplied.";
	
	enum ParsePhase {
		SETUP(ParsingSetupException.class),
		EXEC(ParsingException.class),
		FINISH(null);
		
		private final Class<? extends RuntimeException> exClass;
		
		private ParsePhase(Class<? extends RuntimeException> exClass) {
			this.exClass = exClass;
		}
		
		void trowEx(String msg) {
			throw (RuntimeException) Reflect.instantiateClass(exClass, msg);
		}
		
		void throwEx(String msg, Throwable ex) {
			throw (RuntimeException) Reflect.instantiateClass(exClass, msg, ex);
		}
	}
	
	static class DataParserImpl <T, C extends DataElementCollection<R, E>, E extends ConfigurableDataAccess<?>, R>
			implements DataParser<T, C, E, R>
	{
		private final C collection;
		private final BiFunction<C, T, E> elementInit;
		private final BiFunction<C, T, E> identifier;
		private final BiConsumer<E, T> accepter;
		private final BinaryOperator<E> checker;
		private final Function<C, R> finisher;
		
		DataParserImpl(
				C collection, BiFunction<C, T, E> elementInit, BiFunction<C, T, E> identifier,
				BiConsumer<E, T> accepter, BinaryOperator<E> checker, Function<C, R> finisher
		) {
			this.collection = checkNull(collection, SETUP_COL_MSG, ParsePhase.SETUP);
			this.elementInit = checkNull(elementInit, SETUP_ELEM_INIT_MSG, ParsePhase.SETUP);
			this.identifier = checkNull(identifier, SETUP_ELEM_IDENT_MSG, ParsePhase.SETUP);
			this.accepter = checkNull(accepter, SETUP_DATA_ACCPT_MSG, ParsePhase.SETUP);
			this.checker = checkNull(checker, SETUP_ELEM_CHK_MSG, ParsePhase.SETUP);
			this.finisher = checkNull(finisher, SETUP_COL_FIN_MSG, ParsePhase.SETUP);
		}

		@Override
		public C collection() {
			return collection;
		}

		@Override
		public BiFunction<C, T, E> elementInit() {
			return elementInit;
		}
		
		@Override
		public BiFunction<C, T, E> identifier() {
			return identifier;
		}

		@Override
		public BiConsumer<E, T> accepter() {
			return accepter;
		}

		@Override
		public BinaryOperator<E> checker() {
			return checker;
		}
		
		@Override
		public Function<C, R> finisher() {
			return finisher;
		}
	}
	
	static <T> T checkNull(T t, String errMsg, ParsePhase phase) {
		if (t == null) phase.trowEx(errMsg);
		
		return t;
	}
}
