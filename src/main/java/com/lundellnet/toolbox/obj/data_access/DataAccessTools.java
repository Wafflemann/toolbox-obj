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
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.lundellnet.toolbox.obj.Reflect;
import com.lundellnet.toolbox.obj.collectors.CoreCollector;

public class DataAccessTools {
	public interface FieldPoint {
		Field field();
	}
	
	@SuppressWarnings("unchecked")
	private static <T> List<T> listGetter(Method g, Supplier<?> s) {
		return ((List<T>) Reflect.invokePublicMethod(g, s.get()));
	}
	
	static class FieldPointImpl
			implements FieldPoint
	{
		protected enum AccessType {
			GET, SET
		}
		
		protected static final String GET_SET_REGEX = "\\w{1}(\\w*)([A-Z]*.*)";
		
		protected static <D> Method pointAccessMethod(Class<D> dataObjClass, Field field, AccessType accessType) {
			String fieldName = field.getName();
			
			switch (accessType) {
				case GET:
					return Reflect.getPublicMethod(fieldName.replaceAll(GET_SET_REGEX, "get" + fieldName.substring(0, 1).toUpperCase() + "$1$2"), dataObjClass);
				case SET:
					return Reflect.getPublicMethod(fieldName.replaceAll(GET_SET_REGEX, "set" + fieldName.substring(0, 1).toUpperCase() + "$1$2"), dataObjClass, field.getType());
				default:
					return null;
			}
		}
		
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
			this.dataGetter = pointAccessMethod(parentClass, elementField, AccessType.GET);
			this.dataSetter = pointAccessMethod(parentClass, elementField, AccessType.SET);
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
			this.listDataElementGetter = pointAccessMethod(parentClass, elementField, AccessType.GET);
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
			this.dataGetter = pointAccessMethod(parentClass, elementField, AccessType.GET);
			this.dataSetter = pointAccessMethod(parentClass, elementField, AccessType.SET);
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
			this.listDataGetter = pointAccessMethod(parentClass, elementField, AccessType.GET);
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
}
