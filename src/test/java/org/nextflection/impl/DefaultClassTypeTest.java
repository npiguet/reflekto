package org.nextflection.impl;

import static org.junit.Assert.assertEquals;

import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.EventListener;

import org.junit.Test;

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
		assertDeclarationString(Number.class, 
				"public abstract class java.lang.Number " +
				"implements java.io.Serializable");
		assertDeclarationString(IOException.class,
				"public class java.io.IOException " +
				"extends java.lang.Exception");
		assertDeclarationString(java.lang.reflect.Field.class, 
				"public final class java.lang.reflect.Field " +
				"extends java.lang.reflect.AccessibleObject " +
				"implements java.lang.reflect.Member");
		assertDeclarationString(java.lang.reflect.Method.class, 
				"public final class java.lang.reflect.Method " +
				"extends java.lang.reflect.AccessibleObject " +
				"implements java.lang.reflect.GenericDeclaration, java.lang.reflect.Member");
		
		// generics
//		assertDeclarationString(Long.class, 
//				"public class java.lang.Long " +
//				"extends java.lang.Number " +
//				"implements java.lang.Comparable<java.lang.Long>");
	}
	
	private void assertDeclarationString(Class<?> clazz, String expected){
		assertEquals(expected, reflect(clazz).declarationString());
	}
	
	private DefaultClassType reflect(Class<?> clazz){
		return (DefaultClassType)reflector.reflect(clazz);
	}
}
