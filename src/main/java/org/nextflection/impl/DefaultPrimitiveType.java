package org.nextflection.impl;

import org.nextflection.ObjectType;
import org.nextflection.PrimitiveType;
import org.nextflection.Type;
import org.nextflection.TypeName;
import org.nextflection.TypeVariable;

public class DefaultPrimitiveType extends AbstractType implements PrimitiveType {

	public DefaultPrimitiveType(Class<?> clazz, FullReflector creator) {
		super(clazz, creator);
	}

	public Type withErasure() {
		return this;
	}

	public boolean isErasure() {
		return true;
	}

	public Type withTypeArgument(TypeVariable variable, ObjectType value) {
		throw new UnsupportedOperationException("Not applicable to PrimitiveType");
	}

	@Override
	public String toString() {
		return clazz.getName();
	}

	public String declarationString() {
		// TODO Auto-generated method stub
		return null;
	}

	public TypeName getRawName() {
		// TODO Auto-generated method stub
		return null;
	}

	public TypeName getGenericName() {
		// TODO Auto-generated method stub
		return null;
	}
}
