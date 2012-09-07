package org.nextflection;

public interface ArrayType extends ObjectType, Parameterizable {

	public ArrayType withErasure();

	public ArrayType withTypeArgument(TypeVariable variable, ObjectType value);
}
