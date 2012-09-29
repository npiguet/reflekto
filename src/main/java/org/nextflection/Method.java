package org.nextflection;

import java.util.List;

public interface Method extends Member, GenericDeclaration {

	public Method withErasure();

	public Method withTypeArguments(List<TypeVariable> variable, List<Type> value);

	public Method withTypeArguments(List<Type> values);
}
