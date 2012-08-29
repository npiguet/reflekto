package org.nextflection;

public interface ClassType extends ObjectType, GenericDeclaration {

	public ClassType withTypeArgument(TypeVariable variable, ObjectType value);

	public ClassType withErasure();
}
