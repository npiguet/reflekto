package org.nextflection;

import java.util.List;

public interface ClassType extends ObjectType, GenericDeclaration {

	public boolean isPublic();
	public boolean isAbstract();
	public boolean isFinal();
	public boolean isInterface();
	public Methods methods();
	public boolean isGenericInvocation();
	public boolean isInnerClass();
	public boolean isInnerClassOf(ClassType enclosing);
	public ClassType getEnclosingClass();
	public ClassType getDeclaredClass();
	public ClassType getSuperClass();
	public List<ClassType> getInterfaces();
	public List<Type> getActualTypeParameters();
	public ClassType withTypeArguments(List<Type> values);
	// TODO: use a proper Package object
	public String getPackage();
}
