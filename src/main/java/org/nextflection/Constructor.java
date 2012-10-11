package org.nextflection;

import java.util.List;

public interface Constructor extends Member {

	public Constructor withErasure();

	public Constructor assignVariables(List<TypeVariable> variable, List<Type> value);
}
