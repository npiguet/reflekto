package org.nextflection;

public interface Method extends Member, GenericDeclaration {

	public Method withErasure();

	public Method withTypeArgument(TypeVariable variable, ObjectType value);
}
