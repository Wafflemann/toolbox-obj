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
package com.lundellnet.toolbox.obj.elements.standard.collections;

import java.lang.reflect.Field;

import com.lundellnet.toolbox.obj.collections.configurables.DataCollection;
import com.lundellnet.toolbox.obj.data_access.configurables.StandardDataAccess;
import com.lundellnet.toolbox.obj.elements.standard.configs.StdDataCollectionConf;

@FunctionalInterface
public interface StdDataCollection <R, E extends StandardDataAccess<?, ?>>
		extends DataCollection<StdDataCollectionConf<R, E>, R, E>
{
	default E createElement(Field elementField)
			{ return conf().elementBuilder().build(getDataClass(), getDataSupplier(), elementField); }
}
