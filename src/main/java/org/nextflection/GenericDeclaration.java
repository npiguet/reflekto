package org.nextflection;

public interface GenericDeclaration {

	public GenericDeclaration withTypeArgument(TypeVariable variable, ObjectType value);

	public GenericDeclaration withErasure();
}
