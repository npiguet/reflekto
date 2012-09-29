package org.nextflection;

import java.util.List;

/**
 * An element that may contain references to zero or more TypeVariables. A Parameterizable is not necessarily able to declare its own
 * TypeVariable
 * @see GenericDeclaration
 */
public interface Parameterizable {

	public Parameterizable withErasure();

	public boolean isErasure();

	public Parameterizable withTypeArguments(List<TypeVariable> variable, List<Type> value);
}
