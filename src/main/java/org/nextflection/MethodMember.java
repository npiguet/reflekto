package org.nextflection;

public interface MethodMember extends Member, GenericDeclaration {

	public MethodMember withTypeArgument(TypeVariable variable, ObjectType value);

	public MethodMember withErasure();

}
