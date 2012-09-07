package org.nextflection.impl;

import java.util.List;

import org.nextflection.ClassType;
import org.nextflection.Method;
import org.nextflection.ObjectType;
import org.nextflection.TypeVariable;

public class DefaultMethod extends AbstractElement implements Method {

	public DefaultMethod(java.lang.reflect.Method m, ClassType declaringClass, FullReflector reflector) {
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

	public Method withTypeArgument(TypeVariable variable, ObjectType value) {
		// TODO Auto-generated method stub
		return null;
	}

	public Method withErasure() {
		// TODO Auto-generated method stub
		return null;
	}

}
