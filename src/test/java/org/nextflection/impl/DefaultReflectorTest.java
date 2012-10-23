package org.nextflection.impl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;
import org.nextflection.Type;

public class DefaultReflectorTest {

	private DefaultReflector reflector = new DefaultReflector();

	@Test
	public void testReflectNativeType() {
		Type t = reflector.reflect(int.class);
		assertTrue(t instanceof DefaultPrimitiveType);
		assertEquals("int", t.toString());
	}

	@Test
	public void testReflectClass() {
		Type t = reflector.reflect(Object.class);
		assertTrue(t instanceof DefaultClassType);
		assertEquals("java.lang.Object", t.toString());
	}

	@Test
	public void testReflectArray() {
		Type t = reflector.reflect(Object[].class);
		assertTrue(t instanceof DefaultArrayType);
		assertEquals("java.lang.Object[]", t.toString());
	}
}
