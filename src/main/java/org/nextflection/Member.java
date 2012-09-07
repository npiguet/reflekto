package org.nextflection;

public interface Member extends Parameterizable {

	public ClassType getDeclaringClass();

	public Member withErasure();

	public Member withTypeArgument(TypeVariable variable, ObjectType value);
}
