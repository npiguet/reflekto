package org.nextflection.impl;

import java.util.List;

import org.nextflection.FieldMember;
import org.nextflection.ObjectType;
import org.nextflection.Reflector;
import org.nextflection.TypeVariable;

public class DefaultFieldMember extends AbstractElement implements FieldMember {

	public DefaultFieldMember(Reflector reflector) {
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

	public FieldMember withTypeArgument(TypeVariable variable, ObjectType value) {
		// TODO Auto-generated method stub
		return null;
	}

	public FieldMember withErasure() {
		// TODO Auto-generated method stub
		return null;
	}

}
