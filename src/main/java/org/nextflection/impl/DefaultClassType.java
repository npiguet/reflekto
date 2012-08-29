package org.nextflection.impl;

import org.nextflection.ClassType;
import org.nextflection.ObjectType;
import org.nextflection.Reflector;
import org.nextflection.TypeVariable;

public class DefaultClassType implements ClassType {

	private Reflector reflector;
	private Class<?> clazz;

	public DefaultClassType(Class<?> clazz, Reflector creator){
		this.reflector = creator;
		this.clazz = clazz;
	}

	public ClassType withTypeArgument(TypeVariable variable, ObjectType value) {
		// TODO Auto-generated method stub
		return null;
	}

	public ClassType withErasure() {
		// TODO Auto-generated method stub
		return null;
	}
}
