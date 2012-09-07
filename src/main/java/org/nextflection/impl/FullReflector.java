package org.nextflection.impl;

import java.util.List;

import org.nextflection.ClassType;
import org.nextflection.Constructor;
import org.nextflection.Field;
import org.nextflection.GenericDeclaration;
import org.nextflection.Method;
import org.nextflection.Reflector;
import org.nextflection.TypeVariable;

public interface FullReflector extends Reflector {

	public TypeVariable reflect(java.lang.reflect.TypeVariable<?> var, GenericDeclaration<?> declaration);

	public Constructor reflect(java.lang.reflect.Constructor<?> cons, ClassType declaringClass);

	public Field reflect(java.lang.reflect.Field f, ClassType declaringClass);

	public Method reflect(java.lang.reflect.Method m, ClassType declaringClass);
	
	public ClassType buildCopy(ClassType original, List<TypeVariable> parameters, List<Field> newFields, List<Constructor> newConstructors, List<Method> newMethods);
}
