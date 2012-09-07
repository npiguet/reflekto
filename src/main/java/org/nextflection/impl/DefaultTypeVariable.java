package org.nextflection.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nextflection.ClassType;
import org.nextflection.GenericDeclaration;
import org.nextflection.ObjectType;
import org.nextflection.Type;
import org.nextflection.TypeVariable;

public class DefaultTypeVariable extends AbstractElement implements TypeVariable {

	private java.lang.reflect.TypeVariable<?> typeVariable;
	private List<Type> bounds = new ArrayList<Type>();
	private GenericDeclaration genericDeclaration;

	public DefaultTypeVariable(java.lang.reflect.TypeVariable<?> var, GenericDeclaration declaration, FullReflector creator) {
		super(creator);
		this.typeVariable = var;
		this.genericDeclaration = declaration;
		populateBounds();
	}

	private void populateBounds() {
		for (java.lang.reflect.Type t : typeVariable.getBounds()) {
			//bounds.add(reflector.reflect(t));
		}
	}

	public String getName() {
		return typeVariable.getName();
	}

	public GenericDeclaration getDeclaringElement() {
		return genericDeclaration;
	}

	public List<Type> getBounds() {
		return Collections.unmodifiableList(bounds);
	}

	public Type getLeftmostBound() {
		return bounds.get(0);
	}

	public ClassType withErasure() {
		// TODO Auto-generated method stub
		return null;
	}

	public TypeVariable withTypeArgument(TypeVariable variable, ObjectType value) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isErasure() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isParameterizable() {
		return true;
	}

	public boolean isPrimitive() {
		return false;
	}

	public String getSimpleName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCanonicalName() {
		// TODO Auto-generated method stub
		return null;
	}
}
