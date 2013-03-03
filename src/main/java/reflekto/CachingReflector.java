package reflekto;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CachingReflector implements FullReflector {

	protected TypeCache typeCache = new TypeCache() {
		@Override
		public Type initClass(Class<?> rawClass, java.lang.reflect.Type[] typeArguments) {
			if (typeArguments.length > 0) {
				return reflectParameterizedClass(rawClass, typeArguments);
			}
			if (rawClass.isPrimitive()) {
				return reflectPrimitive(rawClass);
			}
			if (rawClass.isArray()) {
				return reflectArray(rawClass);
			}
			return reflectClassOrInterface(rawClass);
		}

		@Override
		public WildcardType initWildcard(java.lang.reflect.WildcardType jWildcard) {
			return reflect(jWildcard);
		}

		@Override
		public String buildClassKey(Class<?> rawClass, java.lang.reflect.Type[] typeArguments) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String buildClassKey(ClassType clazz, Type[] typeArguments, boolean isErasure) {
			// TODO Auto-generated method stub
			return null;
		}
	};

	public Type reflect(java.lang.reflect.Type type) {
		if (type == null) {
			return null;
		} else if (type instanceof Class) {
			return reflect((Class<?>) type);
		} else if (type instanceof java.lang.reflect.GenericArrayType) {
			return reflect((java.lang.reflect.GenericArrayType) type);
		} else if (type instanceof java.lang.reflect.TypeVariable) {
			return reflect((java.lang.reflect.TypeVariable<?>) type);
		} else if (type instanceof java.lang.reflect.WildcardType) {
			return reflect((java.lang.reflect.WildcardType) type);
		} else if (type instanceof java.lang.reflect.ParameterizedType) {
			return reflect((java.lang.reflect.ParameterizedType) type);
		}
		throw new IllegalStateException();
	}

	public Type reflect(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}
		return typeCache.getOrInitClass(clazz, false);
	}

	public ClassType reflect(ParameterizedType type) {
		// TODO: are those casts really safe? Check the javadoc of ParameterizedType.getRawType()
		return (ClassType) typeCache.getOrInitClass((Class<?>) type.getRawType(), false, type.getActualTypeArguments());
	}

	protected Type reflectPrimitive(Class<?> clazz) {
		assert clazz.isPrimitive();
		return new PrimitiveType(clazz, this);
	}

	protected Type reflectArray(Class<?> clazz) {
		assert clazz.isArray();
		return new ArrayType(clazz.getComponentType(), this);
	}

	protected Type reflect(java.lang.reflect.GenericArrayType type) {
		return new ArrayType(type.getGenericComponentType(), this);
	}

	protected Type reflectClassOrInterface(Class<?> clazz) {
		assert !clazz.isPrimitive() && !clazz.isArray();
		return new ClassType(clazz, this);
	}

	public WildcardType reflect(java.lang.reflect.WildcardType jWildcard) {
		return new WildcardType(jWildcard, this);
	}

	protected Type reflectParameterizedClass(Class<?> jRawClass, java.lang.reflect.Type[] jTypeArguments) {
		// TODO: is this cast really safe? Check the javadoc of ParameterizedType.getRawType()
		ClassType rawClass = (ClassType) reflect(jRawClass);
		List<Type> typeArguments = new ArrayList<Type>(jTypeArguments.length);
		for (java.lang.reflect.Type arg : jTypeArguments) {
			typeArguments.add(reflect(arg));
		}
		return rawClass.getGenericInvocation(typeArguments);
	}

	public TypeVariable reflect(java.lang.reflect.TypeVariable<?> var) {
		java.lang.reflect.GenericDeclaration jDeclaration = var.getGenericDeclaration();
		GenericDeclaration declaration = null;
		if (jDeclaration instanceof Class) {
			declaration = (ClassType) reflect((Class<?>) jDeclaration);
		} else if (jDeclaration instanceof java.lang.reflect.Method) {
			java.lang.reflect.Method jMethod = (java.lang.reflect.Method) jDeclaration;
			ClassType ownerClass = (ClassType) reflect(jMethod.getDeclaringClass());
			declaration = reflect(jMethod, ownerClass);
		} else if (jDeclaration instanceof java.lang.reflect.Constructor) {
			// TODO
			throw new UnsupportedOperationException();
		}
		return new TypeVariable(var, declaration, this);
	}

	public Constructor reflect(java.lang.reflect.Constructor<?> cons, ClassType declaringClass) {
		return new Constructor(cons, declaringClass, this);
	}

	public Field reflect(java.lang.reflect.Field f, ClassType declaringClass) {
		return new Field(f, declaringClass, this);
	}

	public Method reflect(java.lang.reflect.Method m, ClassType declaringClass) {
		return new Method(m, declaringClass, this);
	}

	public ClassType reflectGenericInvocation(ClassType original, List<Type> actualTypeParameters, boolean asErasure) {
		// FIXME: use typecache instead of creating the instance directly. However for this to work, there must be some
		// kind of way to transform
		// the actualTypeParameters into object from java.lang.reflect that can be used as lookup keys.
		return new ClassType(original, actualTypeParameters, asErasure);
	}

	public WildcardType reflectGenericInvocation(WildcardType original, List<Type> lowerBounds, List<Type> upperBounds) {
		return new WildcardType(original, lowerBounds, upperBounds);
	}

	public ArrayType reflectGenericInvocation(ArrayType original, Type elementType) {
		return new ArrayType(original, elementType);
	}

	public Method reflectErasure(Method original) {
		return new Method(original);
	}

	public ArrayType reflectErasure(ArrayType original) {
		return new ArrayType(original);
	}

	public void clearTypeCache() {
		this.typeCache.clear();
	}

	// ======== The section below here is not functional. This is just for doing some tests
	// ======== when i am unsure of the meaning of a method.
	public static List<List<String>> E;
	@SuppressWarnings("rawtypes")
	public static ArrayList F;

	public static void main(String... args) throws SecurityException, NoSuchFieldException {
		java.lang.reflect.Type t = CachingReflector.class.getField("E").getGenericType();
		System.out.println(t);
		System.out.println(((ParameterizedType) t).getRawType());
		System.out.println(((ParameterizedType) t).getOwnerType());
		System.out.println(Arrays.toString(((ParameterizedType) t).getActualTypeArguments()));

		java.lang.reflect.Type t2 = CachingReflector.class.getField("F").getGenericType();
		System.out.println(t2);
		System.out.println(((ParameterizedType) t2).getRawType());
		System.out.println(((ParameterizedType) t2).getOwnerType());
		System.out.println(Arrays.toString(((ParameterizedType) t2).getActualTypeArguments()));

	}
}
