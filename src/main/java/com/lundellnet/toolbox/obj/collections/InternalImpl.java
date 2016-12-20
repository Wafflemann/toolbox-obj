package com.lundellnet.toolbox.obj.collections;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.UNORDERED;

import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector.Characteristics;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.lundellnet.toolbox.obj.Reflect;
import com.lundellnet.toolbox.obj.annotations.CollectionConstant;
import com.lundellnet.toolbox.obj.annotations.DataLocation;

class InternalImpl {
	@FunctionalInterface
	interface CollectionDataBuilder <T, C extends Collection<T, F>, F extends CollectionField> {
		C build(Class<T> t);
	}
	
	static class Field <T>
			implements CollectionField
	{
		private static final String GET_SET_REGEX = "\\w{1}(\\w*)([A-Z]*.*)";
		
		private final Class<T> collectionClass;
		
		private final java.lang.reflect.Field collectionField;
		private final Method collectionFieldSetter;
		private final Method collectionFieldGetter;
		private final String collectionFieldName;
		
		private ReentrantReadWriteLock fieldLock = null;
		private T collectionObject = null;
		
		protected Field(java.lang.reflect.Field rowField) {
			this.collectionClass = (Class<T>) rowField.getDeclaringClass();
			this.collectionField = rowField;
			this.collectionFieldName = rowField.getName();
			
			this.collectionFieldGetter = Reflect.getPublicMethod(collectionFieldName.replaceAll(GET_SET_REGEX, "set" + collectionFieldName.substring(0, 1).toUpperCase() + "$1$2"), collectionClass, rowField.getType());
			this.collectionFieldSetter = Reflect.getPublicMethod(collectionFieldName.replaceAll(GET_SET_REGEX, "get" + collectionFieldName.substring(0, 1).toUpperCase() + "$1$2"), collectionClass);
		}
		
		Field<T> init(T collectionObject) {
			this.collectionObject = collectionObject;
			this.fieldLock = new ReentrantReadWriteLock();
			
			return this;
		}
		
		T getCollection() {
			fieldLock = null;
			
			return collectionObject;
		}
		
		protected java.lang.reflect.Field getField() {
			return collectionField;
		}

		@Override
		public String getFieldName() {
			return collectionFieldName;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <F> Class<F> getFieldType() {
			return (Class<F>) collectionField.getType();
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public <F> F get() {
			if (fieldLock == null) {
				return null;
			}
			
			try {
				fieldLock.readLock().lock();
				return (F) Reflect.invokePublicMethod(collectionFieldSetter, collectionObject);
			} finally {
				fieldLock.readLock().unlock();
			}
		}
		
		@Override
		public <F> void set(F fieldValue) {
			if (fieldLock == null) {
				return;
			}
			
			try {
				fieldLock.writeLock().lock();
				Reflect.invokePublicMethod(collectionFieldGetter, collectionObject, fieldValue);
			} finally {
				fieldLock.writeLock().unlock();
			}
		}
	}
	
	static class LocatableField <T>
			extends InternalImpl.Field<T>
			implements LocatableCollectionField
	{
		LocatableField(java.lang.reflect.Field field) {
			super(field);
		}

		@Override
		public DataLocation getLocation() {
			return getField().getAnnotation(DataLocation.class);
		}
	}
	
	static class Collection <T, F extends InternalApi.Field>
			implements InternalApi.Collection<T, F>
	{
		protected interface FieldBuilder <T, F extends Field<T>> {
			F build(java.lang.reflect.Field field);
		}
		
		protected static <T, F extends Field<T>> ConcurrentMap<String, F> mapFieldStream(
				Stream<java.lang.reflect.Field> fieldStream, FieldBuilder<T, F> fieldBuilder
		) {
			return fieldStream.map((field) -> fieldBuilder.build(field))
					.map((fieldAccess) -> new SimpleEntry<String, F>(fieldAccess.getFieldName(), fieldAccess))
					.collect(Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue));
		}
		
		private final Class<T> collectionClass;
		private final ConcurrentMap<String, ? extends Field<T>> collectionFields;
		
		private T collection = null;
		
		protected Collection(FieldBuilder<T, ? extends Field<T>> fieldBuilder, Class<T> collectionClass) {
			this.collectionClass = collectionClass;
			this.collectionFields = mapFieldStream(Arrays.stream(collectionClass.getFields()), fieldBuilder);
		}

		T getCollection() {
			if (collection == null) {
				
			}
			
			return collection;
		}

		@Override
		public Class<T> getCollectionClass() {
			return collectionClass;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<F> getFields() {
			return collectionFields.entrySet().stream()
					.map((fieldEntry) -> (F) fieldEntry.getValue())
					.collect(java.util.stream.Collectors.toList());
		}
	}
	
	/*If R was an extention of something, perhaps a class to make steps more customizable? */
	static abstract class Collector <T, R, E extends Enum<E>, C extends Collection<Object, F>, F extends CollectionField>
			implements InternalApi.Collector<T, R, E, C, F>
	{
		private static <T, E extends Enum<E>> Set<Map.Entry<E, Class<?>>> getMappingFromRootCollection(Class<T> rootCollection) {
			/*Arrays.stream(rootCollection.getFields()).map((collectionField) -> {
				
			});*/
			
			return null;
		}
		
		private final ReentrantReadWriteLock collectionsLock = new ReentrantReadWriteLock();
		private final Class<R> collectObjectType;
		private final Class<E> collectEnumType;
		private final Map<Class<?>, E> collectionTypes;
		private final ConcurrentMap<E, C> collections;
		
		private BiFunction<C, C, C> collectionNotAbsentHandler = null;
		
		private DataCollection<R> identityCollection = null;
		
		@SuppressWarnings("unchecked")
		protected Collector(
				CollectionDataBuilder<Object, C, F> dataBuilder, Class<R> collectObjectType, Class<E> collectEnumType
		) {
			this.collectObjectType = collectObjectType;
			this.collectEnumType = collectEnumType;
			this.collectionTypes = new HashMap<>();
			this.collections = Arrays.stream((E[]) Reflect.invokePublicMethod(Reflect.getPublicMethod("values", collectEnumType), null))
				.map((enumType) -> {
					Class<?> collectionType = enumType.getClass().getAnnotation(CollectionConstant.class).collectionClass();
					
					collectionTypes.put(collectionType, enumType);
					
					return new AbstractMap.SimpleEntry<>(enumType, collectionType);
				})
				.map((typeEntry) -> new AbstractMap.SimpleEntry<E, C>(typeEntry.getKey(), dataBuilder.build((Class<Object>) typeEntry.getValue())))
				.collect(Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue));
		}

		//@Override
		public List<E> getCollectionTypes() {
			return collections.entrySet().stream().map(Map.Entry::getKey).collect(java.util.stream.Collectors.toList());
		}
		@Override
		public Supplier<Function<E, C>> supplier() {
			return () -> (enumMapping) -> getCollection(enumMapping);
		}

		@Override
		public Function<Function<E, C>, R> finisher() {
			final Supplier<DataCollection<R>> collectionSupplier = () -> (identityCollection != null) ? identityCollection : (identityCollection = DataCollection.<R>builder().build(collectObjectType)); 
			//final BiConsumer<DataCollection<R>, C> collectionAccumulator = null;
			//final BinaryOperator<DataCollection<R>> collectionCombiner = null;
			//final Function<DataCollection<R>, R> collectionFinisher = null;
			
			return (s) -> {
					//R r = identity().get();
					
					collectionSupplier.get().getFields().forEach((field) -> {
						Class<?> fieldType = field.getFieldType();
						
						Object obj = s.apply(collectionTypes.get(fieldType)).getCollection();
						
						field.set(obj);
					});
					
					return collectionSupplier.get().getCollection();
				};
		}

		private C getCollection(E collectionType) {
			C collection = null;
			
			try {
				collectionsLock.readLock().lock();
				collection = collections.get(collectionType);
			} finally {
				collectionsLock.readLock().unlock();
			}
			
			if (collection == null) {
				
			}
			
			return collection;
		}
		
		@SuppressWarnings("unused")
		private void setCollection(E collectionType, C collection) {
			try {
				collectionsLock.writeLock().lock();
				if (collections.putIfAbsent(collectionType, collection) != null) {
					
				}
			} finally {
				collectionsLock.writeLock().unlock();
			}
		}
	}
}
