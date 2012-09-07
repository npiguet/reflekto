package org.nextflection.impl;

import org.nextflection.ClassType;
import org.nextflection.Field;
import org.nextflection.ObjectType;
import org.nextflection.Type;
import org.nextflection.TypeVariable;

public class DefaultField extends AbstractCallableMember implements Field {

	private final java.lang.reflect.Field field;
	private final Type type;

	public DefaultField(java.lang.reflect.Field field, ClassType declaringClass, FullReflector reflector) {
		super(declaringClass, reflector);
		this.field = field;
		this.type = buildType(field.getType());
	}

	private Type buildType(java.lang.reflect.Type langType) {
		//Type type = reflector.reflect(langType);
		// TODO: apply the value of the type variables of the class, if any
		return type;
	}

	public String getName() {
		return field.getName();
	}

	public boolean isErasure() {
		return type.isErasure();
	}

	public Field withTypeArgument(TypeVariable variable, ObjectType value) {
		// TODO Auto-generated method stub
		return null;
	}

	public Field withErasure() {
		// TODO Auto-generated method stub
		return null;
	}

}
