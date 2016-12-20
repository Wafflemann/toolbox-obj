package com.lundellnet.toolbox.obj.collections;

public class LocatableDataCollection <T>
		extends InternalImpl.Collection<T, LocatableCollectionField>
		implements InternalApi.Collection<T, LocatableCollectionField>
{
	static <T> InternalImpl.CollectionDataBuilder<T, LocatableDataCollection<T>, LocatableCollectionField> builder() {
		return LocatableDataCollection<T>::new;
	}
	
	LocatableDataCollection(Class<T> collectionClass) {
		super((FieldBuilder<T, InternalImpl.LocatableField<T>>) InternalImpl.LocatableField<T>::new, collectionClass);
	}
}
