package org.nextflection.impl;

import java.util.List;

import org.nextflection.ClassType;
import org.nextflection.Constructor;
import org.nextflection.Type;
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

	public Constructor withTypeArguments(List<TypeVariable> variable, List<Type> value){
		// TODO Auto-generated method stub
		return null;
	}

	public String declarationString() {
		// TODO Auto-generated method stub
		return null;
	}
}
