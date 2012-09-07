package org.nextflection;

public interface Field extends Member {

	public Field withErasure();

	public Field withTypeArgument(TypeVariable variable, ObjectType value);
}
