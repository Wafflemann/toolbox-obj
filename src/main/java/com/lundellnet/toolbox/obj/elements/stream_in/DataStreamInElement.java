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
package com.lundellnet.toolbox.obj.elements.stream_in;

import java.util.stream.Stream;

import com.lundellnet.toolbox.obj.data_access.configs.DataAccessConf;
import com.lundellnet.toolbox.obj.data_access.configurables.CollectingDataAccess;
import com.lundellnet.toolbox.obj.data_access.configurables.ConfigurableFieldAccess;
import com.lundellnet.toolbox.obj.elements.stream_in.compilation.StrDataElementBuilder;

@FunctionalInterface
public interface DataStreamInElement <T, R>
		extends CollectingDataAccess<T, R, DataAccessConf<Stream<T>, R>>, ConfigurableFieldAccess<T, R, DataAccessConf<Stream<T>, R>>
{
	public static <T, R>
			StrDataElementBuilder<T, R, DataStreamInElement<T, R>> builder()
	{ return DataStreamInElement::createElement; }
	
	@SuppressWarnings("unchecked")
	public static <T, R>
			DataStreamInElement<T, R> createElement(DataAccessConf<?, ?> elementConf)
	{ return () -> (DataAccessConf<Stream<T>, R>) elementConf; }
}
