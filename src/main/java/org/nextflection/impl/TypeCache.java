package org.nextflection.impl;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import org.nextflection.Type;
import org.nextflection.WildcardType;

public abstract class TypeCache {

	private final ConcurrentHashMap<TypeKey, Type> classes = new ConcurrentHashMap<TypeKey, Type>();
	private final ConcurrentHashMap<java.lang.reflect.WildcardType, WildcardType> wildcards =
			new ConcurrentHashMap<java.lang.reflect.WildcardType, WildcardType>();

	public Type getOrInitClass(Class<?> clazz, java.lang.reflect.Type... typeArguments){
		TypeKey key = new TypeKey(clazz, typeArguments);
		Type result = classes.get(key);
		if(result == null){
			Type t = initClass(clazz, typeArguments);
			result = classes.putIfAbsent(key, t);
			if(result == null){
				result = t;
			}
		}
		return result;
	}

	public Type getOrInitWildcard(java.lang.reflect.WildcardType jWildcard){
		Type result = wildcards.get(jWildcard);
		if(result == null){
			WildcardType t = initWildcard(jWildcard);
			result = wildcards.putIfAbsent(jWildcard, t);
			if(result == null){
				result = t;
			}
		}
		return result;
	}


	public void clear(){
		classes.clear();
		wildcards.clear();
	}

	public abstract Type initClass(Class<?> clazz, java.lang.reflect.Type[] typeArguments);
	public abstract WildcardType initWildcard(java.lang.reflect.WildcardType jWildcard);

	private static class TypeKey {
		Class<?> clazz;
		java.lang.reflect.Type[] typeArguments;

		public TypeKey(Class<?> clazz, java.lang.reflect.Type[] typeArguments){
			this.clazz = clazz;
			this.typeArguments = typeArguments;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 19;
			result = prime * result + clazz.hashCode();
			result = prime * result + Arrays.hashCode(typeArguments);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null || getClass() != obj.getClass()) {
				return false;
			}

			TypeKey that = (TypeKey) obj;
			return clazz.equals(that.clazz) && Arrays.equals(typeArguments, that.typeArguments);
		}
	}
}
