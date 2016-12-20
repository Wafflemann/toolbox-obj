package com.lundellnet.toolbox.obj.collections;

public abstract class AbstractDataCollector <T, R, E extends Enum<E>>
		extends InternalImpl.Collector<T, R, E, DataCollection<Object>, CollectionField>
{
	public AbstractDataCollector(Class<R> collectionDataType, Class<E> collectionFieldEnum) {
		super(DataCollection<Object>::new, collectionDataType, collectionFieldEnum);
	}
}
