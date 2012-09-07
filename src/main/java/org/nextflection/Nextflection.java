package org.nextflection;

import org.nextflection.impl.DefaultReflector;

public class Nextflection {

	private static final Reflector REFLECTOR = new DefaultReflector();

	public static ObjectType reflect(Class<?> clazz){
		return REFLECTOR.reflect(clazz);
	}

	public Type reflect(java.lang.reflect.Type type){
		return REFLECTOR.reflect(type);
	}
}
