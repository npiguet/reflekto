package org.nextflection.impl;

import java.util.List;

import org.nextflection.Field;
import org.nextflection.ObjectType;
import org.nextflection.Reflector;
import org.nextflection.TypeVariable;

public class DefaultField extends AbstractElement implements Field {

	public DefaultField(Reflector reflector) {
		super(reflector);
		// TODO Auto-generated constructor stub
	}

	public List<TypeVariable> getTypeParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isErasure() {
		// TODO Auto-generated method stub
		return false;
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