package org.nextflection.impl;

import org.nextflection.Type;

public abstract class AbstractType extends AbstractElement implements Type {

	protected final Class<?> clazz;

	public AbstractType(Class<?> clazz, FullReflector creator) {
		super(creator);
		this.clazz = clazz;
	}

	public String getName() {
		return clazz.getName();
	}

	public String getSimpleName() {
		return clazz.getSimpleName();
	}

	public String getCanonicalName() {
		return clazz.getCanonicalName();
	}

	public boolean isPrimitive() {
		return clazz.isPrimitive();
	}
}
