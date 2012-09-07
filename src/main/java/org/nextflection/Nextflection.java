package org.nextflection;

import org.nextflection.impl.DefaultReflector;

public class Nextflection {

	private static final Reflector REFLECTOR = new DefaultReflector();

	public static Type reflect(Class<?> clazz) {
		return REFLECTOR.reflect(clazz);
	}
}
