package com.lundellnet.toolbox.obj.collections;

import java.util.function.Supplier;

public class LocatableDataCollection <T>
		extends InternalImpl.Collection<T, LocatableCollectionField>
		implements InternalApi.Collection<T, LocatableCollectionField>
{
	static <T> InternalImpl.CollectionBuilder<T, LocatableDataCollection<T>, LocatableCollectionField> builder() {
		return LocatableDataCollection<T>::new;
	}
	
	LocatableDataCollection(Class<T> collectionClass) {
		super((FieldBuilder<T, InternalImpl.LocatableField<T>>) InternalImpl.LocatableField<T>::new, collectionClass);
	}
	
	LocatableDataCollection(Class<T> collectionClass, Supplier<T> collectionSupplier) {
		super((FieldBuilder<T, InternalImpl.LocatableField<T>>) InternalImpl.LocatableField<T>::new, collectionClass, collectionSupplier);
	}
}
