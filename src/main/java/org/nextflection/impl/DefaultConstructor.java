package org.nextflection.impl;

import org.nextflection.ClassType;
import org.nextflection.Constructor;
import org.nextflection.ObjectType;
import org.nextflection.TypeVariable;

public class DefaultConstructor extends AbstractMember implements Constructor {

	private final java.lang.reflect.Constructor<?> constructor;

	public DefaultConstructor(java.lang.reflect.Constructor<?> constructor, ClassType declaringClass, FullReflector reflector) {
		super(declaringClass, reflector);
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

	public String declarationString() {
		// TODO Auto-generated method stub
		return null;
	}
}
