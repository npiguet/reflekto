package org.nextflection;

import java.util.List;

public interface ArrayType extends ObjectType {

	public ArrayType withErasure();

	public ArrayType assignVariables(List<TypeVariable> variable, List<Type> value);
}
