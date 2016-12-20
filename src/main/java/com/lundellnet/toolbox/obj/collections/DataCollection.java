package com.lundellnet.toolbox.obj.collections;

import java.util.function.Supplier;

public class DataCollection <T>
		extends InternalImpl.Collection<T, CollectionField>
		implements InternalApi.Collection<T, CollectionField>
{
	static <T> InternalImpl.CollectionBuilder<T, DataCollection<T>, CollectionField> builder() {
		return DataCollection<T>::new;
	}
	
	DataCollection(Class<T> collectionClass) {
		super((FieldBuilder<T, InternalImpl.Field<T>>) InternalImpl.Field<T>::new, collectionClass);
	}
	
	DataCollection(Class<T> collectionClass, Supplier<T> collectionSupplier) {
		super((FieldBuilder<T, InternalImpl.Field<T>>) InternalImpl.Field<T>::new, collectionClass, collectionSupplier);
	}
}
