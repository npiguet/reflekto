package org.nextflection.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nextflection.GenericDeclaration;
import org.nextflection.Reflector;
import org.nextflection.Type;
import org.nextflection.TypeVariable;

public class DefaultTypeVariable extends AbstractElement implements TypeVariable {

	private java.lang.reflect.TypeVariable<?> typeVariable;
	private List<Type> bounds = new ArrayList<Type>();
	private GenericDeclaration genericDeclaration;

	public DefaultTypeVariable(java.lang.reflect.TypeVariable<?> var, GenericDeclaration declaration, Reflector creator) {
		super(creator);
		this.typeVariable = var;
		this.genericDeclaration = declaration;
		populateBounds();
	}

	private void populateBounds() {
		for (java.lang.reflect.Type t : typeVariable.getBounds()) {
			bounds.add(reflector.reflect(t));
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
}
