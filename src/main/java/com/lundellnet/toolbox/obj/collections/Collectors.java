package com.lundellnet.toolbox.obj.collections;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class Collectors {
	public static <T, R, E extends Enum<E>> Collector<T, ?, R> toDataType(
			Class<R> collectionDataType, Class<E> collectionFieldEnum, Function<T, E> identifier, BiConsumer<DataCollection<Object>, T> consumer
	) {
		return new InternalImpl.Collector<T, R, E, DataCollection<Object>, CollectionField>
					(DataCollection<Object>::new, collectionDataType, collectionFieldEnum)
			{
				@Override
				public Supplier<R> identity() {
					// TODO Auto-generated method stub
					return null;
				}
	
				@Override
				public Function<T, E> collectionIdentifier() {
					return identifier;
				}
	
				@Override
				public BiConsumer<DataCollection<Object>, T> consumer() {
					return consumer;
				}
			};
	}
	
	public static <T, R, E extends Enum<E>> Collector<T, ?, R> toLocatableDataType(
			Class<R> collectionDataType, Class<E> collectionFieldEnum, Function<T, E> identifier, BiConsumer<LocatableDataCollection<Object>, T> consumer
	) {
		return new InternalImpl.Collector<T, R, E, LocatableDataCollection<Object>, LocatableCollectionField>
					(LocatableDataCollection<Object>::new, collectionDataType, collectionFieldEnum)
			{
				@Override
				public Supplier<R> identity() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Function<T, E> collectionIdentifier() {
					return identifier;
				}

				@Override
				public BiConsumer<LocatableDataCollection<Object>, T> consumer() {
					return consumer;
				}
			};
	}
}
