package org.nextflection.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nextflection.ClassType;
import org.nextflection.Constructor;
import org.nextflection.Field;
import org.nextflection.Method;
import org.nextflection.ObjectType;
import org.nextflection.TypeVariable;

public class DefaultClassType extends AbstractElement implements ClassType {

	private final Class<?> clazz;
	private final List<TypeVariable> typeParameters;
	private final List<Method> methods;
	private final List<Field> fields;
	private final List<Constructor> constructors;

	public DefaultClassType(Class<?> clazz, FullReflector creator) {
		super(creator);
		this.clazz = clazz;
		this.typeParameters = Collections.unmodifiableList(this.createTypeParameters());
		this.methods = Collections.unmodifiableList(this.createMethods());
		this.fields = Collections.unmodifiableList(this.createFields());
		this.constructors = Collections.unmodifiableList(this.createConstructors());
	}

	/**
	 * Builds a copy of the original DefaultClassType, replacing the list of type parameters, methods, constructors and fields iif the specified
	 * one is not null.
	 */
	protected DefaultClassType(DefaultClassType original, List<TypeVariable> newTypeParameters, List<Method> newMethods, List<Field> newFields,
			List<Constructor> newConstructors) {
		super(original.reflector);
		this.clazz = original.clazz;
		if (newTypeParameters != null) {
			this.typeParameters = Collections.unmodifiableList(newTypeParameters);
		} else {
			this.typeParameters = original.typeParameters;
		}
		if (newMethods != null) {
			this.methods = Collections.unmodifiableList(newMethods);
		} else {
			this.methods = original.methods;
		}
		if (newFields != null) {
			this.fields = Collections.unmodifiableList(newFields);
		} else {
			this.fields = original.fields;
		}
		if (newConstructors != null) {
			this.constructors = Collections.unmodifiableList(newConstructors);
		} else {
			this.constructors = original.constructors;
		}
	}

	private List<TypeVariable> createTypeParameters() {
		java.lang.reflect.TypeVariable<? extends Class<?>>[] langParams = clazz.getTypeParameters();
		List<TypeVariable> params = new ArrayList<TypeVariable>(langParams.length);
		for (java.lang.reflect.TypeVariable<? extends Class<?>> var : langParams) {
			params.add(reflector.reflect(var, this));
		}
		return params;
	}

	private List<Constructor> createConstructors() {
		java.lang.reflect.Constructor<?>[] langConstructors = clazz.getDeclaredConstructors();
		List<Constructor> cons = new ArrayList<Constructor>(langConstructors.length);
		for (java.lang.reflect.Constructor<?> c : langConstructors) {
			cons.add(reflector.reflect(c, this));
		}
		return cons;
	}

	private List<Field> createFields() {
		java.lang.reflect.Field[] langFields = clazz.getDeclaredFields();
		List<Field> flds = new ArrayList<Field>(langFields.length);
		for (java.lang.reflect.Field f : langFields) {
			flds.add(reflector.reflect(f, this));
		}
		return flds;
	}

	private List<Method> createMethods() {
		java.lang.reflect.Method[] langMethods = clazz.getDeclaredMethods();
		List<Method> meths = new ArrayList<Method>(langMethods.length);
		for (java.lang.reflect.Method m : langMethods) {
			meths.add(reflector.reflect(m, this));
		}
		return meths;
	}

	public List<TypeVariable> getTypeParameters() {
		return typeParameters;
	}

	public ClassType withTypeArgument(TypeVariable variable, ObjectType value) {
		// TODO Auto-generated method stub
		return null;
	}

	public ClassType withErasure() {
		List<Field> newFields = new ArrayList<Field>(fields.size());
		List<Constructor> newConstructors = new ArrayList<Constructor>(fields.size());
		List<Method> newMethods = new ArrayList<Method>(methods.size());
		List<TypeVariable> noTypeVariables = Collections.emptyList();

		for (Field f : fields) {
			newFields.add(f.withErasure());
		}
		for (Constructor c : constructors) {
			newConstructors.add(c.withErasure());
		}
		for (Method m : methods) {
			newMethods.add(m.withErasure());
		}

		return reflector.buildCopy(this, noTypeVariables, newFields, newConstructors, newMethods);
	}

	public boolean isErasure() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isParameterizable() {
		return true;
	}
}
