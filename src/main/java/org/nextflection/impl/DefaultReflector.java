package org.nextflection.impl;

import java.util.List;

import org.nextflection.ClassType;
import org.nextflection.Constructor;
import org.nextflection.Field;
import org.nextflection.GenericDeclaration;
import org.nextflection.Method;
import org.nextflection.ObjectType;
import org.nextflection.Type;
import org.nextflection.TypeVariable;

public class DefaultReflector implements FullReflector {

	public Type reflect(java.lang.reflect.Type type) {
		if (type instanceof Class) {
			return reflect((Class<?>) type);
		}

		throw new UnsupportedOperationException("Type " + type + " (" + type.getClass().getName() + ") not supported yet");
	}

	public ObjectType reflect(Class<?> clazz) {
		return new DefaultClassType(clazz, this);
	}

	public TypeVariable reflect(java.lang.reflect.TypeVariable<?> var, GenericDeclaration declaration) {
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
}
