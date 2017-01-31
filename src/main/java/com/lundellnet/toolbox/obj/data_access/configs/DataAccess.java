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
