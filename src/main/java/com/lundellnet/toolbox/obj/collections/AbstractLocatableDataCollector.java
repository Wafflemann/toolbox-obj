package com.lundellnet.toolbox.obj.collections;

public abstract class AbstractLocatableDataCollector <T, R, E extends Enum<E>>
		extends InternalImpl.Collector<T, R, E, LocatableDataCollection<Object>, LocatableCollectionField>
{
	public AbstractLocatableDataCollector(Class<R> collectionDataType, Class<E> collectionFieldEnum) {
		super(LocatableDataCollection<Object>::new, collectionDataType, collectionFieldEnum);
	}
}
