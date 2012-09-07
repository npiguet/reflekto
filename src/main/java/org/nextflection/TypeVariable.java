package org.nextflection;

import java.util.List;

public interface TypeVariable extends Type {

	public String getName();

	public GenericDeclaration<?> getDeclaringElement();

	public List<Type> getBounds();

	public Type getLeftmostBound();
}
