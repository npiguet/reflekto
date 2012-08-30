package org.nextflection.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nextflection.ClassType;
import org.nextflection.ObjectType;
import org.nextflection.Reflector;
import org.nextflection.TypeVariable;

public class DefaultClassType extends AbstractElement implements ClassType {

	private Class<?> clazz;
	private List<TypeVariable> typeParameters = new ArrayList<TypeVariable>();

	public DefaultClassType(Class<?> clazz, Reflector creator) {
		super(creator);
		this.clazz = clazz;
		this.populateTypeParameters();
	}

	private void populateTypeParameters() {
		for (java.lang.reflect.TypeVariable<? extends Class<?>> var : clazz.getTypeParameters()) {
			typeParameters.add(reflector.reflect(var, this));
		}
	}

	public List<TypeVariable> getTypeParameters() {
		return Collections.unmodifiableList(typeParameters);
	}

	public ClassType withTypeArgument(TypeVariable variable, ObjectType value) {
		// TODO Auto-generated method stub
		return null;
	}

	public ClassType withErasure() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isErasure() {
		// TODO Auto-generated method stub
		return false;
	}
}
