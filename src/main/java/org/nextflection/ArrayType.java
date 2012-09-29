package org.nextflection;

import java.util.List;

public interface ArrayType extends ObjectType {

	public ArrayType withErasure();

	public ArrayType withTypeArguments(List<TypeVariable> variable, List<Type> value);
}
