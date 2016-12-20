package com.lundellnet.toolbox.obj.collections;

public class DataCollection <T>
		extends InternalImpl.Collection<T, CollectionField>
		implements InternalApi.Collection<T, CollectionField>
{
	static <T> InternalImpl.CollectionDataBuilder<T, DataCollection<T>, CollectionField> builder() {
		return DataCollection<T>::new;
	}
	
	DataCollection(Class<T> collectionClass) {
		super((FieldBuilder<T, InternalImpl.Field<T>>) InternalImpl.Field<T>::new, collectionClass);
	}
}
