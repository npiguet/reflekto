package org.nextflection;

import java.util.List;

public interface GenericDeclaration<T extends GenericDeclaration> {

	public List<TypeVariable> getTypeParameters();

	public T withTypeArgument(TypeVariable variable, ObjectType value);
}
