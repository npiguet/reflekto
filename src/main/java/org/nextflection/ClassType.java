package org.nextflection;

import java.util.List;

public interface ClassType extends ObjectType, GenericDeclaration {

	public boolean isPublic();
	public boolean isAbstract();
	public boolean isFinal();
	public boolean isInterface();
	public Methods methods();
	public boolean isGenericInvocation();
	public ClassType getSuperClass();
	public List<ClassType> getInterfaces();
	public List<Type> getActualTypeParameters();
	public ClassType withTypeArguments(List<Type> values);

}
