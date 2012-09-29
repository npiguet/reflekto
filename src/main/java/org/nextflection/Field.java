package org.nextflection;

import java.util.List;

public interface Field extends Member {

	public Field withErasure();

	public Field withTypeArguments(List<TypeVariable> variable, List<Type> value);
}
