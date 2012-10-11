package org.nextflection.impl;

import java.util.List;

import org.nextflection.Type;
import org.nextflection.TypeName;
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

	@Override
	public String toString() {
		return clazz.toString();
	}

	public String declarationString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeName getRawName() {
		// TODO Auto-generated method stub
		return null;
	}

	public TypeName getGenericName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Type assignVariables(List<TypeVariable> variables,
			List<Type> values) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isSuperTypeOf(Type that) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAssignableFrom(Type that) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSameType(Type that) {
		// TODO Auto-generated method stub
		return false;
	}

}
