package org.nextflection;


public interface Field extends Member, GenericDeclaration {


	public Field withTypeArgument(TypeVariable variable, ObjectType value);

	public Field withErasure();
}
