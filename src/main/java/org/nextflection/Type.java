package org.nextflection;

import java.util.List;

public interface Type extends Parameterizable {
	public Type withErasure();

	public boolean isErasure();

	public Type withTypeArguments(List<TypeVariable> variables, List<Type> values);

	public boolean isPrimitive();

	public String getName();

	public TypeName getRawName();

	public TypeName getGenericName();

	public String declarationString();
}
