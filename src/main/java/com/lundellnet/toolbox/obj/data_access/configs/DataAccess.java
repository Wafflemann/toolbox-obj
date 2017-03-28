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
package com.lundellnet.toolbox.obj.data_access.configs;

import java.util.Set;

import com.lundellnet.toolbox.obj.data_access.DataAccessTools;
import com.lundellnet.toolbox.obj.data_access.builders.CollectingObjectDataPointBuilder;
import com.lundellnet.toolbox.obj.data_access.builders.CollectingSetDataPointBuilder;
import com.lundellnet.toolbox.obj.data_access.builders.DataPointBuilder;

class DataAccess
		extends DataAccessTools
{
	static <T>
			DataPointBuilder<T, T> dataPointBasicBuilder()
	{ return DataPointBasic<T, T>::new; }
	
	static <T>
			DataPointBuilder<T, Set<T>> dataPointSetBuilder()
	{ return DataPointSet<T>::new; }
	
	static <T, R>
			CollectingObjectDataPointBuilder<T, R> collectingDataPointObjectBuilder()
	{ return CollectingObjectDataPoint<T, R>::new; }
	
	static <T, R>
			CollectingSetDataPointBuilder<T, R> collectingDataPointSetBuilder()
	{ return CollectingSetDataPoint<T, R>::new; }
}
