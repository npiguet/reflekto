package org.nextflection.impl;

import org.nextflection.ClassType;
import org.nextflection.Constructor;
import org.nextflection.ObjectType;
import org.nextflection.TypeVariable;

public class DefaultConstructor extends AbstractElement implements Constructor {

	private final java.lang.reflect.Constructor<?> constructor;
	private final ClassType declaringClass;

	public DefaultConstructor(java.lang.reflect.Constructor<?> constructor, ClassType declaringClass, FullReflector reflector) {
		super(reflector);
		this.declaringClass = declaringClass;
		this.constructor = constructor;
	}

	public Constructor withErasure() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isErasure() {
		// TODO Auto-generated method stub
		return false;
	}

	public Constructor withTypeArgument(TypeVariable variable, ObjectType value) {
		// TODO Auto-generated method stub
		return null;
	}
}
