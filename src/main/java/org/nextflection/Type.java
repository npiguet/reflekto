package org.nextflection;

public interface Type extends Parameterizable {
	public Type withErasure();

	public boolean isErasure();

	public Type withTypeArgument(TypeVariable variable, ObjectType value);
}
