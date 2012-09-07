package org.nextflection;

public interface ArrayType extends ObjectType {

	public ArrayType withErasure();

	public ArrayType withTypeArgument(TypeVariable variable, ObjectType value);
}
