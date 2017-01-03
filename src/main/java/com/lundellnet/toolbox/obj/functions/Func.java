package com.lundellnet.toolbox.obj.functions;

import java.lang.reflect.Field;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.lundellnet.toolbox.obj.Reflect;

class Func {
	public static <K, V> BiFunction<K, V, Map.Entry<K, V>> toEntry() { return SimpleEntry<K, V>::new; }
	public static <K, V> Map.Entry<K, V> toEntry(K k, V v) { return Func.<K, V>toEntry().apply(k, v); }
	
	@SuppressWarnings("unchecked")
	public static <E extends Enum<E>> Function<Field, E> enumInstance()
		{ return (f) -> (E) Reflect.invokePublicMethod(Reflect.getPublicMethod("valueOf", f.getDeclaringClass(), String.class), null, f.getName()); }
	public static <E extends Enum<E>> E enumInstance(Field f) { return Func.<E>enumInstance().apply(f); }
}
