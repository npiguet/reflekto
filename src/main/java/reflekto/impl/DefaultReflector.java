package reflekto.impl;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import reflekto.ArrayType;
import reflekto.ClassType;
import reflekto.Constructor;
import reflekto.Field;
import reflekto.GenericDeclaration;
import reflekto.Method;
import reflekto.Type;
import reflekto.TypeVariable;
import reflekto.WildcardType;

public class DefaultReflector implements FullReflector {

	protected TypeCache typeCache = new TypeCache(){
		@Override
		public Type initClass(Class<?> rawClass, java.lang.reflect.Type[] typeArguments) {
			if(typeArguments.length > 0){
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
	};

	public Type reflect(java.lang.reflect.Type type) {
		if(type == null){
			return null;
		} else if (type instanceof Class){
			return reflect((Class<?>) type);
		} else if (type instanceof java.lang.reflect.GenericArrayType){
			return reflect((java.lang.reflect.GenericArrayType)type);
		} else if (type instanceof java.lang.reflect.TypeVariable){
			return reflect((java.lang.reflect.TypeVariable<?>)type);
		} else if (type instanceof java.lang.reflect.WildcardType){
			return reflect((java.lang.reflect.WildcardType)type);
		} else if (type instanceof java.lang.reflect.ParameterizedType){
			return reflect((java.lang.reflect.ParameterizedType)type);
		}
		throw new IllegalStateException();
	}

	public Type reflect(Class<?> clazz) {
		return typeCache.getOrInitClass(clazz);
	}

	public ClassType reflect(ParameterizedType type) {
		// TODO: are those casts really safe? Check the javadoc of ParameterizedType.getRawType()
		return (ClassType)typeCache.getOrInitClass((Class<?>)type.getRawType(), type.getActualTypeArguments());
	}

	protected Type reflectPrimitive(Class<?> clazz){
		assert clazz.isPrimitive();
		return new DefaultPrimitiveType(clazz, this);
	}

	protected Type reflectArray(Class<?> clazz){
		assert clazz.isArray();
		return new DefaultArrayType(clazz.getComponentType(), this);
	}

	protected Type reflect(java.lang.reflect.GenericArrayType type){
		return new DefaultArrayType(type.getGenericComponentType(), this);
	}

	protected Type reflectClassOrInterface(Class<?> clazz){
		assert !clazz.isPrimitive() && !clazz.isArray();
		return new DefaultClassType(clazz, this);
	}

	public WildcardType reflect(java.lang.reflect.WildcardType jWildcard){
		return new DefaultWildcardType(jWildcard, this);
	}

	protected Type reflectParameterizedClass(Class<?> jRawClass, java.lang.reflect.Type[] jTypeArguments) {
		// TODO: is this cast really safe? Check the javadoc of ParameterizedType.getRawType()
		ClassType rawClass = (ClassType)reflect(jRawClass);
		List<Type> typeArguments = new ArrayList<Type>(jTypeArguments.length);
		for(java.lang.reflect.Type arg : jTypeArguments){
			typeArguments.add(reflect(arg));
		}
		return rawClass.getGenericInvocation(typeArguments);
	}

	public TypeVariable reflect(java.lang.reflect.TypeVariable<?> var) {
		java.lang.reflect.GenericDeclaration jDeclaration = var.getGenericDeclaration();
		GenericDeclaration declaration = null;
		if(jDeclaration instanceof Class){
			declaration = (ClassType)reflect((Class<?>)jDeclaration);
		} else if (jDeclaration instanceof java.lang.reflect.Method) {
			java.lang.reflect.Method jMethod = (java.lang.reflect.Method)jDeclaration;
			ClassType ownerClass = (ClassType)reflect(jMethod.getDeclaringClass());
			declaration = reflect(jMethod, ownerClass);
		} else if (jDeclaration instanceof java.lang.reflect.Constructor) {
			// TODO
			throw new UnsupportedOperationException();
		}
		return new DefaultTypeVariable(var, declaration, this);
	}

	public Constructor reflect(java.lang.reflect.Constructor<?> cons, ClassType declaringClass) {
		return new DefaultConstructor(cons, declaringClass, this);
	}

	public Field reflect(java.lang.reflect.Field f, ClassType declaringClass) {
		return new DefaultField(f, declaringClass, this);
	}

	public Method reflect(java.lang.reflect.Method m, ClassType declaringClass) {
		return new DefaultMethod(m, declaringClass, this);
	}

	public ClassType reflectGenericInvocation(ClassType original, List<Type> actualTypeParameters) {
		return new DefaultClassType((DefaultClassType) original, actualTypeParameters);
	}

	public WildcardType reflectGenericInvocation(WildcardType original, List<Type> lowerBounds, List<Type> upperBounds) {
		return new DefaultWildcardType((DefaultWildcardType) original, lowerBounds, upperBounds);
	}

	public ArrayType reflectGenericInvocation(ArrayType original, Type elementType) {
		return new DefaultArrayType((DefaultArrayType) original, elementType);
	}

	public ClassType reflectErasure(ClassType original) {
		return new DefaultClassType((DefaultClassType)original);
	}

	public Method reflectErasure(Method original) {
		return new DefaultMethod((DefaultMethod)original);
	}

	public ArrayType reflectErasure(ArrayType original) {
		return new DefaultArrayType((DefaultArrayType)original);
	}

	public void clearTypeCache(){
		this.typeCache.clear();
	}

	public static List<List<String>> E;

	public static void main(String... args) throws SecurityException, NoSuchFieldException{
		java.lang.reflect.Type t = DefaultReflector.class.getField("E").getGenericType();
		System.out.println(t);
		System.out.println(((ParameterizedType)t).getRawType());
		System.out.println(((ParameterizedType)t).getOwnerType());
		System.out.println(Arrays.toString(((ParameterizedType)t).getActualTypeArguments()));
	}
}
