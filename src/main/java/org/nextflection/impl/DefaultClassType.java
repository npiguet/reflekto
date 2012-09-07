package org.nextflection.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nextflection.ClassType;
import org.nextflection.Constructor;
import org.nextflection.Field;
import org.nextflection.Method;
import org.nextflection.ObjectType;
import org.nextflection.Reflector;
import org.nextflection.TypeVariable;

public class DefaultClassType extends AbstractElement implements ClassType {

	private final Class<?> clazz;
	private final List<TypeVariable> typeParameters;
	private final List<Method> methods;
	private final List<Field> fields;
	private final List<Constructor> constructors;

	public DefaultClassType(Class<?> clazz, Reflector creator) {
		super(creator);
		this.clazz = clazz;
		this.typeParameters = this.createTypeParameters();
		this.methods = this.createMethods();
		this.fields = this.createFields();
		this.constructors = this.createConstructors();
	}

	protected DefaultClassType(Class<?> clazz, List<TypeVariable> typeParameters, List<Method> methods, List<Field> fields, List<Constructor> constructors, Reflector creator){
		super(creator);
		this.clazz = clazz;
		this.typeParameters = typeParameters;
		this.methods = methods;
		this.fields = fields;
		this.constructors = constructors;
	}

	private List<TypeVariable> createTypeParameters() {
		List<TypeVariable> params = new ArrayList<TypeVariable>(clazz.getTypeParameters().length);
		for (java.lang.reflect.TypeVariable<? extends Class<?>> var : clazz.getTypeParameters()) {
			params.add(reflector.reflect(var, this));
		}
		return params;
	}
	
	private List<Constructor> createConstructors() {
		// TODO: code it
		return null;
	}

	private List<Field> createFields() {
		// TODO: code it
		return null;
	}

	private List<Method> createMethods() {
		// TODO: code it
		return null;
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
