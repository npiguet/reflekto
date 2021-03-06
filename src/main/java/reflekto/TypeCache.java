package reflekto;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;


public abstract class TypeCache {

	private final ConcurrentHashMap<TypeKey, Type> classes = new ConcurrentHashMap<TypeKey, Type>();
	private final ConcurrentHashMap<java.lang.reflect.WildcardType, WildcardType> wildcards =
			new ConcurrentHashMap<java.lang.reflect.WildcardType, WildcardType>();

	public Type getOrInitClass(Class<?> clazz, boolean isErasure, java.lang.reflect.Type... typeArguments){
		TypeKey key = new TypeKey(clazz, isErasure, typeArguments);
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
	public abstract String buildClassKey(Class<?> clazz, java.lang.reflect.Type[] typeArgument);
	public abstract String buildClassKey(ClassType clazz, Type[] typeArguments, boolean isErasure);
	public abstract WildcardType initWildcard(java.lang.reflect.WildcardType jWildcard);

	private static class TypeKey {
		// TODO: use some version of toString as the type key instead of this object.
		//       this should make it possible to use both java.lang.reflect and
		//       reflekto objects to lookup classes in the cache.
		Class<?> clazz;
		boolean isErasure;
		java.lang.reflect.Type[] typeArguments;

		public TypeKey(Class<?> clazz, boolean isErasure, java.lang.reflect.Type[] typeArguments){
			this.clazz = clazz;
			this.isErasure = isErasure;
			this.typeArguments = typeArguments;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 19;
			result = prime * result + (isErasure ? 1231 : 1237);
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
			return clazz.equals(that.clazz) && //
					Arrays.equals(typeArguments, that.typeArguments) && //
					isErasure == that.isErasure;
		}
	}
}
