package org.nextflection.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nextflection.ClassType;
import org.nextflection.ConstructorMember;
import org.nextflection.FieldMember;
import org.nextflection.MethodMember;
import org.nextflection.ObjectType;
import org.nextflection.Reflector;
import org.nextflection.TypeVariable;

public class DefaultClassType extends AbstractElement implements ClassType {

	private final Class<?> clazz;
	private final List<TypeVariable> typeParameters;
	private final List<MethodMember> methods;
	private final List<FieldMember> fields;
	private final List<ConstructorMember> constructors;

	public DefaultClassType(Class<?> clazz, Reflector creator) {
		super(creator);
		this.clazz = clazz;
		this.typeParameters = this.createTypeParameters();
		this.methods = this.createMethods();
		this.fields = this.createFields();
		this.constructors = this.createConstructors();
	}

	protected DefaultClassType(Class<?> clazz, List<TypeVariable> typeParameters, List<MethodMember> methods, List<FieldMember> fields, List<ConstructorMember> constructors, Reflector creator){
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
	
	private List<ConstructorMember> createConstructors() {
		// TODO: code it
		return null;
	}

	private List<FieldMember> createFields() {
		// TODO: code it
		return null;
	}

	private List<MethodMember> createMethods() {
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
