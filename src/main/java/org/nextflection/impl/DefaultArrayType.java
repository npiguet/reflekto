package org.nextflection.impl;

import org.nextflection.ObjectType;
import org.nextflection.Type;
import org.nextflection.TypeVariable;

public class DefaultArrayType extends AbstractType {

	public DefaultArrayType(Class<?> clazz, FullReflector creator) {
		super(clazz, creator);
	}

	public Type withErasure() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isErasure() {
		// TODO Auto-generated method stub
		return false;
	}

	public Type withTypeArgument(TypeVariable variable, ObjectType value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return clazz.toString();
	}

	public String declarationString() {
		// TODO Auto-generated method stub
		return null;
	}

}
