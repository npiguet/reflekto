package org.nextflection;

public interface Method extends Member, GenericDeclaration {

	public Method withTypeArgument(TypeVariable variable, ObjectType value);

	public Method withErasure();

}
