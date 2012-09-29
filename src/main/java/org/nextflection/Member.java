package org.nextflection;

import java.util.List;

public interface Member extends Parameterizable {

	public ClassType getDeclaringClass();

	public Member withErasure();

	public Member withTypeArguments(List<TypeVariable> variable, List<Type> value);

	public String declarationString();
}
