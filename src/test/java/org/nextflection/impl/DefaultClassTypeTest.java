package org.nextflection.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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
		// class with no super types
		assertDeclarationString(Object.class, "public class java.lang.Object");
		// interface with no super types
		assertDeclarationString(EventListener.class, "public abstract interface java.util.EventListener");
		// class with non-parameterized interface
		assertDeclarationString(Number.class, //
				"public abstract class java.lang.Number " + //
				"implements java.io.Serializable");
		// class with non parameterized superclass
		assertDeclarationString(IOException.class, //
				"public class java.io.IOException " + //
				"extends java.lang.Exception");
		// class with non-parameterized superclass and interface
		assertDeclarationString(java.lang.reflect.Field.class, //
				"public final class java.lang.reflect.Field " + //
				"extends java.lang.reflect.AccessibleObject " + //
				"implements java.lang.reflect.Member");
		// class with multiple interfaces
		assertDeclarationString(java.lang.reflect.Method.class, //
				"public final class java.lang.reflect.Method " + //
				"extends java.lang.reflect.AccessibleObject " + //
				"implements java.lang.reflect.GenericDeclaration, java.lang.reflect.Member");
		// parameterized interface
		assertDeclarationString(Iterable.class,
				"public abstract interface java.lang.Iterable<T>");
		// class implementing a parameterized interface
		assertDeclarationString(Long.class, //
				"public final class java.lang.Long " + //
				"extends java.lang.Number " + //
				"implements java.lang.Comparable<java.lang.Long>");
		// class extending and implementing parameterized supertypes
		assertDeclarationString(ArrayList.class, //
				"public class java.util.ArrayList<E> " + //
				"extends java.util.AbstractList<E> " + //
				"implements java.util.List<E>, java.util.RandomAccess, java.lang.Cloneable, java.io.Serializable");
		// Class declaring bounds that depend on itself
		assertDeclarationString(Enum.class, //
				"public abstract class java.lang.Enum<E extends java.lang.Enum<E>> " +
				"implements java.lang.Comparable<E>, java.io.Serializable");
		// Class declaring multiple type arguments
		assertDeclarationString(HashMap.class, //
				"public class java.util.HashMap<K, V> " + //
				"extends java.util.AbstractMap<K, V> " + //
				"implements java.util.Map<K, V>, java.lang.Cloneable, java.io.Serializable");
		// Enum parent class
		assertDeclarationString(TimeUnit.class, //
				"public abstract enum java.util.concurrent.TimeUnit");
		// Enum constant
		assertDeclarationString(TimeUnit.SECONDS.getClass(), //
				"class java.util.concurrent.TimeUnit$4 " +
				"extends java.util.concurrent.TimeUnit");
		// TODO: static inner class
	}

	@Test
	public void testGetGenericName(){
		// non parameterized
		assertGenericName(Object.class, "java.lang.Object", "Object", "java.lang.Object");
		// unbounded type variable
		assertGenericName(Iterable.class, "java.lang.Iterable<T>", "Iterable<T>", "java.lang.Iterable<T>");
		// bounded type variable
		assertGenericName(java.lang.reflect.TypeVariable.class,
				"java.lang.reflect.TypeVariable<D extends java.lang.reflect.GenericDeclaration>",
				"TypeVariable<D>",
				"java.lang.reflect.TypeVariable<D extends java.lang.reflect.GenericDeclaration>");
		// different canonical name
		// TODO
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
