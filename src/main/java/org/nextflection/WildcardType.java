package org.nextflection;

import java.util.List;

public interface WildcardType extends Type {

	public List<Type> getUpperBounds();

	public List<Type> getLowerBounds();
}
