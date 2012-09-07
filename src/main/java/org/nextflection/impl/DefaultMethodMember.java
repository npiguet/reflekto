package org.nextflection.impl;

import java.util.List;

import org.nextflection.MethodMember;
import org.nextflection.ObjectType;
import org.nextflection.Reflector;
import org.nextflection.TypeVariable;

public class DefaultMethodMember extends AbstractElement implements MethodMember {

	public DefaultMethodMember(Reflector reflector) {
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

	public MethodMember withTypeArgument(TypeVariable variable, ObjectType value) {
		// TODO Auto-generated method stub
		return null;
	}

	public MethodMember withErasure() {
		// TODO Auto-generated method stub
		return null;
	}

}
