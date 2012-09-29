package org.nextflection;

import java.util.List;

public interface ClassType extends ObjectType, GenericDeclaration {

	public boolean isPublic();
	public boolean isAbstract();
	public boolean isFinal();
	public boolean isInterface();
	public ClassType withTypeArguments(List<Type> values);

}
