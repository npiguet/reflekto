package org.nextflection;

import java.util.List;

public interface GenericDeclaration {

	public List<TypeVariable> getTypeParameters();

	public GenericDeclaration withTypeArgument(TypeVariable variable, ObjectType value);

	public GenericDeclaration withErasure();

	public boolean isErasure();
}
