package org.nextflection;

public interface Constructor extends Member {

	public Constructor withErasure();

	public Constructor withTypeArgument(TypeVariable variable, ObjectType value);
}
