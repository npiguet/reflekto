package org.nextflection;

public interface ClassType extends ObjectType, GenericDeclaration {

	public boolean isPublic();
	public boolean isAbstract();
	public boolean isFinal();
	public boolean isInterface();
	
}
