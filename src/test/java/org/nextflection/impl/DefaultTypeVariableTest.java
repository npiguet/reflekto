package org.nextflection.impl;

import static org.junit.Assert.assertEquals;

import java.util.EnumSet;

import org.junit.Test;
import org.nextflection.TypeName;
import org.nextflection.TypeVariable;

public class DefaultTypeVariableTest {

	private DefaultReflector reflector = new DefaultReflector();

	@Test
	public void testGenericName(){
		// no bounds
		assertGenericName(Iterable.class, 0, "T", "T", "T");

		// non parameterized bound
		assertGenericName(java.lang.reflect.TypeVariable.class, 0,
				"D extends java.lang.reflect.GenericDeclaration",
				"D",
				"D extends java.lang.reflect.GenericDeclaration");

		// parameterized bound
		assertGenericName(EnumSet.class, 0, "E extends Enum<E>", "E", "E extends Enum<E>");

		// multiple bound
		// TODO
	}

	public void assertGenericName(Class<?> clazz, int typeParameterIndex, String expectedFull, String expectedSimple, String expectedCanonical){
		assertName(reflect(clazz, typeParameterIndex).getGenericName(), expectedFull, expectedSimple, expectedCanonical);
	}

	public void assertName(TypeName name, String expectedFull, String expectedSimple, String expectedCanonical){
		assertEquals(expectedFull, name.full());
		assertEquals(expectedSimple, name.simple());
		assertEquals(expectedCanonical, name.canonical());
	}

	private TypeVariable reflect(Class<?> clazz, int typeParameterIndex){
		DefaultClassType declaration = (DefaultClassType)reflector.reflect(clazz);
		return declaration.getTypeParameters().get(typeParameterIndex);
	}
}
