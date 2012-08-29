package org.nextflection;


public interface FieldMember extends Member, GenericDeclaration {


	public FieldMember withTypeArgument(TypeVariable variable, ObjectType value);

	public FieldMember withErasure();
}
