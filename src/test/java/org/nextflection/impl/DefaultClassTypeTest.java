package org.nextflection.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.EventListener;

import org.junit.Test;
import org.nextflection.TypeName;

public class DefaultClassTypeTest {
	private DefaultReflector reflector = new DefaultReflector();

	@Test
	public void testToString(){
		assertEquals(Object.class.toString(), reflect(Object.class).toString());
	}

	@Test
	public void testDeclarationString(){
		// no generics
		assertDeclarationString(Object.class, "public class java.lang.Object");
		assertDeclarationString(EventListener.class, "public abstract interface java.util.EventListener");
		assertDeclarationString(Number.class, //
				"public abstract class java.lang.Number " + //
				"implements java.io.Serializable");
		assertDeclarationString(IOException.class, //
				"public class java.io.IOException " + //
				"extends java.lang.Exception");
		assertDeclarationString(java.lang.reflect.Field.class, //
				"public final class java.lang.reflect.Field " + //
				"extends java.lang.reflect.AccessibleObject " + //
				"implements java.lang.reflect.Member");
		assertDeclarationString(java.lang.reflect.Method.class, //
				"public final class java.lang.reflect.Method " + //
				"extends java.lang.reflect.AccessibleObject " + //
				"implements java.lang.reflect.GenericDeclaration, java.lang.reflect.Member");

		// generics
		assertDeclarationString(Iterable.class,
				"public abstract interface java.lang.Iterable<T>");
		assertDeclarationString(Long.class, //
				"public final class java.lang.Long " + //
				"extends java.lang.Number " + //
				"implements java.lang.Comparable<java.lang.Long>");
	}

	@Test
	public void testGetGenericName(){
		assertGenericName(Object.class, "java.lang.Object", "Object", "java.lang.Object");
		assertGenericName(Iterable.class, "java.lang.Iterable<T>", "Iterable<T>", "java.lang.Iterable<T>");
		assertGenericName(java.lang.reflect.TypeVariable.class,
				"java.lang.reflect.TypeVariable<D extends java.lang.reflect.GenericDeclaration>",
				"TypeVariable<D>",
				"java.lang.reflect.TypeVariable<D extends java.lang.reflect.GenericDeclaration>");
		// TODO: different canonical name
	}

	public void assertGenericName(Class<?> clazz, String expectedFull, String expectedSimple, String expectedCanonical){
		assertName(reflect(clazz).getGenericName(), expectedFull, expectedSimple, expectedCanonical);
	}

	public void assertName(TypeName name, String expectedFull, String expectedSimple, String expectedCanonical){
		assertEquals(expectedFull, name.full());
		assertEquals(expectedSimple, name.simple());
		assertEquals(expectedCanonical, name.canonical());
	}

	private void assertDeclarationString(Class<?> clazz, String expected){
		assertEquals(expected, reflect(clazz).declarationString());
	}

	private DefaultClassType reflect(Class<?> clazz){
		return (DefaultClassType)reflector.reflect(clazz);
	}
}
