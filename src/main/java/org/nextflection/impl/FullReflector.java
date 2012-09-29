package org.nextflection.impl;

import java.util.List;

import org.nextflection.ClassType;
import org.nextflection.Constructor;
import org.nextflection.Field;
import org.nextflection.Method;
import org.nextflection.Reflector;
import org.nextflection.Type;
import org.nextflection.TypeVariable;

public interface FullReflector extends Reflector {

	public Type reflect(java.lang.reflect.Type type);

	public ClassType reflect(java.lang.reflect.ParameterizedType type);

	public TypeVariable reflect(java.lang.reflect.TypeVariable<?> var);

	public Constructor reflect(java.lang.reflect.Constructor<?> cons, ClassType declaringClass);

	public Field reflect(java.lang.reflect.Field f, ClassType declaringClass);

	public Method reflect(java.lang.reflect.Method m, ClassType declaringClass);

	public ClassType withTypeArguments(ClassType original, List<Type> actualTypeArguments);

	public void clearTypeCache();
}
