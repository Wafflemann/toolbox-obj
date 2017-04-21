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
package com.lundellnet.toolbox.obj.data_access;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.lundellnet.toolbox.Reflect;
import com.lundellnet.toolbox.evince.MutationType;
import com.lundellnet.toolbox.obj.collectors.CoreCollector;
import com.lundellnet.toolbox.obj.data_access.compilation.CollectingObjectDataPointBuilder;
import com.lundellnet.toolbox.obj.data_access.compilation.CollectingSetDataPointBuilder;
import com.lundellnet.toolbox.obj.data_access.compilation.ConvertingObjectDataPointBuilder;
import com.lundellnet.toolbox.obj.data_access.compilation.ConvertingSetDataPointBuilder;
import com.lundellnet.toolbox.obj.data_access.compilation.DataPointBuilder;

public class DataAccessTools {
	@SuppressWarnings("unchecked")
	private static <T> List<T> listGetter(Method g, Supplier<?> s) {
		return ((List<T>) Reflect.invokePublicMethod(g, s.get()));
	}
	
	static class FieldPointImpl
			implements FieldPoint
	{
		private final Field field;
		
		protected FieldPointImpl(Field field) {
			this.field = field;
		}

		@Override
		public Field field() {
			return field;
		}
	}
	
	protected static class DataPointBasic <I, O>
			extends FieldPointImpl
			implements DataPoint<I, O>
	{
		private final ReentrantReadWriteLock pointLock = new ReentrantReadWriteLock();
		private final Supplier<?> parentSupplier;
		private final Method dataGetter;
		private final Method dataSetter;
		
		public DataPointBasic(Class<?> parentClass, Supplier<?> parentSupplier, Field elementField) {
			super(elementField);
			
			this.parentSupplier = parentSupplier;
			this.dataGetter = Reflect.getMutationMethod(MutationType.GET, parentClass, elementField);
			this.dataSetter = Reflect.getMutationMethod(MutationType.SET, parentClass, elementField);
		}
		
		@Override
		public Consumer<I> setter() {
			return (t) -> {
				try {
					pointLock.writeLock().lock();
					Reflect.invokePublicMethod(dataSetter, parentSupplier.get(), t);
				} finally {
					pointLock.writeLock().unlock();
				}
			};
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public Supplier<O> getter() {
			return () -> {
					try {
						pointLock.readLock().lock();
						return (O) Reflect.invokePublicMethod(dataGetter, parentSupplier.get());
					} finally {
						pointLock.readLock().unlock();
					}
				};
		}
	}
	
	protected static class DataPointSet <T>
			extends FieldPointImpl
			implements DataPoint<T, Set<T>>
	{
		private final ReentrantReadWriteLock pointLock = new ReentrantReadWriteLock();
		private final Supplier<?> parentSupplier;
		private final Method listDataElementGetter;
		
		public DataPointSet(Class<?> parentClass, Supplier<?> parentSupplier, Field elementField) {
			super(elementField);
			
			this.parentSupplier = parentSupplier;
			this.listDataElementGetter = Reflect.getMutationMethod(MutationType.GET, parentClass, elementField);
		}

		@Override
		public Consumer<T> setter() {
			return (t) -> {
					try {
						pointLock.writeLock().lock();
						DataAccessTools.<T>listGetter(listDataElementGetter, parentSupplier).add(t);
					} finally {
						pointLock.writeLock().unlock();
					}
				};
		}

		@Override
		public Supplier<Set<T>> getter() {
			return () -> {
					try {
						pointLock.readLock().lock();
						return DataAccessTools.<T>listGetter(listDataElementGetter, parentSupplier).parallelStream().collect(Collectors.toSet());
					} finally {
						pointLock.readLock().unlock();
					}
				};
		}
	}

	protected static class CollectingObjectDataPoint <I, O>
			extends FieldPointImpl
			implements DataPoint<Stream<I>, O>
	{
		private final ReentrantReadWriteLock pointLock = new ReentrantReadWriteLock();
		private final Supplier<?> parentSupplier;
		private final Method dataGetter;
		private final Method dataSetter;
		private final CoreCollector<I, ?, ?, O> collector;
		
		public CollectingObjectDataPoint(
				Class<?> parentClass, Supplier<?> parentSupplier, Field elementField, CoreCollector<I, ?, ?, O> collector
		) {
			super(elementField);
			
			this.parentSupplier = parentSupplier;
			this.dataGetter = Reflect.getMutationMethod(MutationType.GET, parentClass, elementField);
			this.dataSetter = Reflect.getMutationMethod(MutationType.SET, parentClass, elementField);
			this.collector = collector;
		}

		@Override
		public Consumer<Stream<I>> setter() {
			return (s) -> {
					O r = s.collect(collector);
				
					try {
						pointLock.writeLock().lock();
						Reflect.invokePublicMethod(dataSetter, parentSupplier.get(), r);
					} finally {
						pointLock.writeLock().unlock();
					}
				};
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public Supplier<O> getter() {
			return () -> {
					try {
						pointLock.readLock().lock();
						return (O) Reflect.invokePublicMethod(dataGetter, parentSupplier.get());
					} finally {
						pointLock.readLock().unlock();
					}
				};
		}
	}
	
	protected static class CollectingSetDataPoint <I, O>
			extends FieldPointImpl
			implements DataPoint<Stream<I>, Set<O>>
	{
		private final ReentrantReadWriteLock pointLock = new ReentrantReadWriteLock();
		private final Supplier<?> parentSupplier;
		private final Method listDataGetter;
		private final CoreCollector<I, ?, ?, O> collector;
		
		public CollectingSetDataPoint(
				Class<?> parentClass, Supplier<?> parentSupplier, Field elementField, CoreCollector<I, ?, ?, O> collector
		) {
			super(elementField);
			
			this.parentSupplier = parentSupplier;
			this.listDataGetter = Reflect.getMutationMethod(MutationType.GET, parentClass, elementField);
			this.collector = collector;
		}

		@Override
		public Consumer<Stream<I>> setter() {
			return (s) -> {
					O r = s.collect(collector);
				
					try {
						pointLock.writeLock().lock();
						DataAccessTools.<O>listGetter(listDataGetter, parentSupplier).add(r);
					} finally {
						pointLock.writeLock().unlock();
					}
				};
		}

		@Override
		public Supplier<Set<O>> getter() {
			return () -> {
					try {
						pointLock.readLock().lock();
						return DataAccessTools.<O>listGetter(listDataGetter, parentSupplier).parallelStream().collect(Collectors.toSet());
					} finally {
						pointLock.readLock().unlock();
					}
				};
		}
	}
	
	protected static class ConvertingObjectDataPoint <I, O>
			extends FieldPointImpl
			implements DataPoint<I, O>
	{
		private final ReentrantReadWriteLock pointLock = new ReentrantReadWriteLock();
		private final Supplier<?> parentSupplier;
		private final Method dataGetter;
		private final Method dataSetter;
		private final Function<I, O> converter;
		
		ConvertingObjectDataPoint(
				Class<?> parentClass, Supplier<?> parentSupplier, Field elementField, Function<I, O> converter
		) {
			super(elementField);
			
			this.parentSupplier = parentSupplier;
			this.dataGetter = Reflect.getMutationMethod(MutationType.GET, parentClass, elementField);
			this.dataSetter = Reflect.getMutationMethod(MutationType.SET, parentClass, elementField);
			this.converter = converter;
		}
		
		@Override
		public Consumer<I> setter() {
			return (t) -> {
				try {
					pointLock.writeLock().lock();
					Reflect.invokePublicMethod(dataSetter, parentSupplier.get(), converter.apply(t));
				} finally {
					pointLock.writeLock().unlock();
				}
			};
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public Supplier<O> getter() {
			return () -> {
					try {
						pointLock.readLock().lock();
						return (O) Reflect.invokePublicMethod(dataGetter, parentSupplier.get());
					} finally {
						pointLock.readLock().unlock();
					}
				};
		}
	}
	
	protected static class ConvertingSetDataPoint <I, O>
			extends FieldPointImpl
			implements DataPoint<I, Set<O>>
	{
		private final ReentrantReadWriteLock pointLock = new ReentrantReadWriteLock();
		private final Supplier<?> parentSupplier;
		private final Method listDataGetter;
		private final Function<I, O> converter;
		
		ConvertingSetDataPoint(
				Class<?> parentClass, Supplier<?> parentSupplier, Field elementField, Function<I, O> converter
		) {
			super(elementField);
			
			this.parentSupplier = parentSupplier;
			this.listDataGetter = Reflect.getMutationMethod(MutationType.GET, parentClass, elementField);
			this.converter = converter;
		}

		@Override
		public Consumer<I> setter() {
			return (t) -> {
					try {
						pointLock.writeLock().lock();
						DataAccessTools.<O>listGetter(listDataGetter, parentSupplier).add(converter.apply(t));
					} finally {
						pointLock.writeLock().unlock();
					}
				};
		}

		@Override
		public Supplier<Set<O>> getter() {
			return () -> {
					try {
						pointLock.readLock().lock();
						return DataAccessTools.<O>listGetter(listDataGetter, parentSupplier).parallelStream().collect(Collectors.toSet());
					} finally {
						pointLock.readLock().unlock();
					}
				};
		}
	}
	
	public static <T> DataPointBuilder<T, T> dataPointBasicBuilder() {
		return DataPointBasic<T, T>::new;
	}
	
	public static <T> DataPointBuilder<T, Set<T>> dataPointSetBuilder() {
		return DataPointSet<T>::new;
	}
	
	public static <T, R> CollectingObjectDataPointBuilder<T, R> collectingDataPointObjectBuilder() {
		return CollectingObjectDataPoint<T, R>::new;
	}
	
	public static <T, R> CollectingSetDataPointBuilder<T, R> collectingDataPointSetBuilder() {
		return CollectingSetDataPoint<T, R>::new;
	}
	
	public static <T, R> ConvertingObjectDataPointBuilder<T, R> convertingDataPointObjectBuilder() {
		return ConvertingObjectDataPoint<T, R>::new;
	}
	
	public static <T, R> ConvertingSetDataPointBuilder<T, R> convertingDataPointSetBuilder() {
		return ConvertingSetDataPoint<T, R>::new;
	}
}
