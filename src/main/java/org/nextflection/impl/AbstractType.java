package org.nextflection.impl;

import org.nextflection.Type;
import org.nextflection.TypeName;

public abstract class AbstractType extends AbstractElement implements Type {

	protected final Class<?> clazz;

	public AbstractType(Class<?> clazz, FullReflector creator) {
		super(creator);
		this.clazz = clazz;
	}

	public String getName(){
		return getGenericName().full();
	}

	public TypeName getRawName() {
		return new AbstractTypeName(){

			public String full() {
				return clazz.getName();
			}

			public String simple() {
				return clazz.getSimpleName();
			}

			public String canonical() {
				return clazz.getCanonicalName();
			}
		};
	}

	public boolean isPrimitive() {
		return clazz.isPrimitive();
	}
}
