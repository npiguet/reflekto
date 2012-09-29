package org.nextflection.impl;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.nextflection.ClassType;
import org.nextflection.Constructor;
import org.nextflection.Field;
import org.nextflection.GenericDeclaration;
import org.nextflection.Method;
import org.nextflection.Type;
import org.nextflection.TypeVariable;

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
		public TypeVariable initTypeVariable(java.lang.reflect.TypeVariable<?> var) {
			return reflect(var);
		}
	};

	public Type reflect(java.lang.reflect.Type type) {
		if(type instanceof Class){
			return reflect((Class<?>) type);
		} else if (type instanceof java.lang.reflect.GenericArrayType){
			// TODO
			throw new UnsupportedOperationException();
		} else if (type instanceof java.lang.reflect.TypeVariable){
			return reflect((java.lang.reflect.TypeVariable<?>)type);
		} else if (type instanceof java.lang.reflect.WildcardType){
			// TODO
			throw new UnsupportedOperationException();
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
		return new DefaultArrayType(clazz, this);
	}

	protected Type reflectClassOrInterface(Class<?> clazz){
		assert !clazz.isPrimitive() && !clazz.isArray();
		return new DefaultClassType(clazz, this);
	}

	protected Type reflectParameterizedClass(Class<?> jRawClass, java.lang.reflect.Type[] jTypeArguments) {
		// TODO: is this cast really safe? Check the javadoc of ParameterizedType.getRawType()
		ClassType rawClass = (ClassType)reflect(jRawClass);
		List<Type> typeArguments = new ArrayList<Type>(jTypeArguments.length);
		for(java.lang.reflect.Type arg : jTypeArguments){
			typeArguments.add(reflect(arg));
		}
		return rawClass.withTypeArguments(typeArguments);
	}

	public TypeVariable reflect(java.lang.reflect.TypeVariable<?> var) {
		java.lang.reflect.GenericDeclaration jDeclaration = var.getGenericDeclaration();
		GenericDeclaration declaration = null;
		if(jDeclaration instanceof Class){
			declaration = (ClassType)reflect((Class<?>)jDeclaration);
		} else if (jDeclaration instanceof java.lang.reflect.Method) {
			// TODO
			throw new UnsupportedOperationException();
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

	public ClassType buildCopy(ClassType original, List<TypeVariable> newTypeParameters, List<Field> newFields,
			List<Constructor> newConstructors, List<Method> newMethods) {
		return new DefaultClassType((DefaultClassType) original, newTypeParameters, newMethods, newFields, newConstructors);
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
