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
package com.lundellnet.toolbox.obj.elements.stream_in.collections;

import java.lang.reflect.Field;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.lundellnet.toolbox.obj.collections.CollectingDataElementCollection;
import com.lundellnet.toolbox.obj.collections.configurables.EnumDataCollection;
import com.lundellnet.toolbox.obj.collectors.compilation.ParsingCollectorGenerator;
import com.lundellnet.toolbox.obj.elements.stream_in.EnumDataStreamInElement;
import com.lundellnet.toolbox.obj.elements.stream_in.compilation.StrEnumElementBuilder;
import com.lundellnet.toolbox.obj.elements.stream_in.configs.StrEnumCollectionConf;

@FunctionalInterface
public interface StrEnumCollection <D extends Enum<D>, R, E extends EnumDataStreamInElement<?, ?, D>>
		extends EnumDataCollection<StrEnumCollectionConf<D, R, E>, D, R, E>, CollectingDataElementCollection<R, E>
{
    @SuppressWarnings("unchecked")
	default <I, O>
    		E createElement(Field elementField, Field enumConstField, Class<O> resultClass, Supplier<O> resultSupplier)
    { return (E) ((StrEnumElementBuilder<I, O, D, ?>) conf().elementBuilder()).build(getDataClass(), getDataSupplier(), elementField, conf().<I, O>colGen().generate(resultClass, resultSupplier), getCollectionEnumClass(), enumConstField); }

	@Override
	default <I, O>
			ParsingCollectorGenerator<I, O> collectorGenerator()
	{ return conf().colGen(); }
	
	@Override
	default
			void includeElement(E e)
	{ conf().collectionStream().includeElement(e); }

	@Override
	default
			Stream<E> elements()
	{ return conf().collectionStream().getStream(); }

	@Override
	default
			Class<R> getDataClass()
	{ return conf().resultClass(); }

	@Override
	default
			Supplier<R> getDataSupplier()
	{ return conf().resultSupplier(); }

	@Override
	default
			R getData()
	{ return conf().resultSupplier().get(); }
}
