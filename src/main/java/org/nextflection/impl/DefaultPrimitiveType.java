package org.nextflection.impl;

import java.util.List;

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

	public Type withTypeArguments(List<TypeVariable> variable, List<Type> value){
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

	@Override
	public TypeName getRawName() {
		return new AbstractTypeName(){
			public String full() {
				return clazz.getName();
			}

			public String simple() {
				return clazz.getName();
			}

			public String canonical() {
				return clazz.getName();
			}
		};
	}

	public TypeName getGenericName() {
		return getRawName();
	}
}
