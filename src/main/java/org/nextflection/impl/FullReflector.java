package org.nextflection.impl;

import java.util.List;

import org.nextflection.ArrayType;
import org.nextflection.ClassType;
import org.nextflection.Constructor;
import org.nextflection.Field;
import org.nextflection.Method;
import org.nextflection.Reflector;
import org.nextflection.Type;
import org.nextflection.TypeVariable;
import org.nextflection.WildcardType;

public interface FullReflector extends Reflector {

	public Type reflect(java.lang.reflect.Type type);

	public ClassType reflect(java.lang.reflect.ParameterizedType type);

	public TypeVariable reflect(java.lang.reflect.TypeVariable<?> var);

	public Constructor reflect(java.lang.reflect.Constructor<?> cons, ClassType declaringClass);

	public Field reflect(java.lang.reflect.Field f, ClassType declaringClass);

	public Method reflect(java.lang.reflect.Method m, ClassType declaringClass);

	public ClassType reflectGenericInvocation(ClassType original, List<Type> actualTypeArguments);

	public ClassType reflectErasure(ClassType original);

	public Method reflectErasure(Method original);

	public ArrayType reflectErasure(ArrayType original);

	public WildcardType reflectGenericInvocation(WildcardType original, List<Type> lowerBounds, List<Type> upperBounds);

	public ArrayType reflectGenericInvocation(ArrayType original, Type elementType);

	public void clearTypeCache();
}
