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
package com.lundellnet.toolbox.obj.collections.configurables;

import java.util.function.Supplier;
import java.util.stream.Stream;

import com.lundellnet.toolbox.obj.collections.DataElementCollection;
import com.lundellnet.toolbox.obj.collections.configs.DataCollectionConf;
import com.lundellnet.toolbox.obj.data_access.configurables.ConfigurableDataAccess;

@FunctionalInterface
public interface DataCollection <C extends DataCollectionConf<R, E, ?>, R, E extends ConfigurableDataAccess<?>>
		extends DataElementCollection<R, E>
{
	C conf();
	
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
