package org.nextflection.impl;

import java.util.List;

import org.nextflection.ClassType;
import org.nextflection.Method;
import org.nextflection.Type;
import org.nextflection.TypeVariable;

public class DefaultMethod extends AbstractCallableMember implements Method {

	public DefaultMethod(java.lang.reflect.Method m, ClassType declaringClass, FullReflector reflector) {
		super(declaringClass, reflector);
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

	public Method withTypeArguments(List<TypeVariable> variable, List<Type> value){
		// TODO Auto-generated method stub
		return null;
	}

	public Method withTypeArguments(List<Type> values) {
		// TODO Auto-generated method stub
		return null;
	}

	public Method withErasure() {
		// TODO Auto-generated method stub
		return null;
	}

	public String declarationString() {
		// TODO Auto-generated method stub
		return null;
	}

}
