package org.nextflection.impl;

import org.nextflection.GenericDeclaration;
import org.nextflection.ObjectType;
import org.nextflection.Reflector;
import org.nextflection.Type;
import org.nextflection.TypeVariable;

public class DefaultReflector implements Reflector {

	public Type reflect(java.lang.reflect.Type type) {
		if (type instanceof Class) {
			return reflect((Class<?>) type);
		}

		throw new UnsupportedOperationException("Type " + type + " (" + type.getClass().getName() + ") not supported yet");
	}

	public ObjectType reflect(Class<?> clazz) {
		return new DefaultClassType(clazz, this);
	}

	public TypeVariable reflect(java.lang.reflect.TypeVariable<?> var, GenericDeclaration declaration) {
		return new DefaultTypeVariable(var, declaration, this);
	}
}
