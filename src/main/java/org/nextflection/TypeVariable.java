package org.nextflection;

import java.util.List;

public interface TypeVariable extends Type, Parameterizable {

	public String getName();

	public GenericDeclaration getDeclaringElement();

	public List<Type> getBounds();

	public Type getLeftmostBound();

	public ClassType withErasure();

	public TypeVariable withTypeArgument(TypeVariable variable, ObjectType value);
}
