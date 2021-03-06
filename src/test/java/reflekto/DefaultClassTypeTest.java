package reflekto;

import static junit.framework.Assert.assertSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractCellEditor;

import org.junit.Test;

public class DefaultClassTypeTest {
	private CachingReflector reflector = new CachingReflector();

	@Test
	public void testToString() {
		assertEquals("java.lang.Object", reflect(Object.class).toString());
	}

	@Test
	public void testDeclarationString() {
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
		assertDeclarationString(Iterable.class, "public abstract interface java.lang.Iterable<T>");
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
				"public abstract class java.lang.Enum<E extends java.lang.Enum<E>> "
						+ "implements java.lang.Comparable<E>, java.io.Serializable");
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
				"class java.util.concurrent.TimeUnit$4 " + "extends java.util.concurrent.TimeUnit");
		// TODO: static inner class
	}

	@Test
	public void testGetGenericName() {
		// non parameterized
		assertGenericName(Object.class, "java.lang.Object", "Object", "java.lang.Object");
		// unbounded type variable
		assertGenericName(Iterable.class, "java.lang.Iterable<T>", "Iterable<T>", "java.lang.Iterable<T>");
		// bounded type variable
		assertGenericName(java.lang.reflect.TypeVariable.class,
				"java.lang.reflect.TypeVariable<D extends java.lang.reflect.GenericDeclaration>", "TypeVariable<D>",
				"java.lang.reflect.TypeVariable<D extends java.lang.reflect.GenericDeclaration>");
		// different canonical name
		// TODO
	}

	@Test
	public void testIsErasure() {
		// no class is erased by default, because of Object.getClass() which returns Class<?>
		assertFalse(reflect(Object.class).isErasure());
		// By default ArrayList is definitely not erased
		assertFalse(reflect(ArrayList.class).isErasure());
		// Erasure types are erased
		assertTrue(reflect(ArrayList.class).withErasure().isErasure());
		// The erasure of an erased type is itself
		assertSame(reflect(HashMap.class).withErasure(), reflect(HashMap.class).withErasure().withErasure());
	}

	@Test
	public void testWithErasure() {
		// when
		ClassType erased = reflect(ArrayList.class).withErasure();

		// then
		// the class itself is erased
		assertTrue(erased.isErasure());
		// the name reflects the erasure
		assertEquals("java.util.ArrayList", erased.getName());
		// methods that use type variables are erased
		assertEquals("public java.lang.Object get(int)", erased.methods().withName("get").get(0).declarationString());
	}

	@Test
	public void testIsSameType() {
		assertTrue(reflect(ArrayList.class).isSameType(reflect(ArrayList.class)));
		assertFalse(reflect(ArrayList.class).isSameType(reflector.reflect(int.class)));
		// TODO: test the same thing with generic arguments
	}

	@Test
	public void testIsInnerClassOf() {
		assertTrue(reflect(Map.Entry.class).isInnerClassOf(reflect(Map.class)));
		assertFalse(reflect(Object.class).isInnerClassOf(reflect(ArrayList.class)));
	}

	@Test
	public void testGetMethods() {
		// no super types, and no overriden methods
		assertEquals(12, reflect(Object.class).methods().size());
		// Overriden methods must be added only once
		assertEquals(1, reflect(Integer.class).methods().withName("intValue").size());
		// Methods in super types that are not overriden must be added
		assertEquals(1, reflect(Number.class).methods().withName("hashCode").size());
		// Methods declared in an interface but not implemented must still be added
		assertEquals(1, reflect(AbstractCellEditor.class).methods().withName("getCellEditorValue").size());
		// Private methods of super classes must not be added
		assertNull(reflect(HashMap.class).methods().withName("eq").get());
	}

	@Test
	public void testIsSuperClassOf() {
		// Direct subclass
		assertTrue(reflect(Object.class).isSuperClassOf(reflect(Number.class)));
		// Indirect subclass
		assertTrue(reflect(Object.class).isSuperClassOf(reflect(Integer.class)));
		// Interface
		assertTrue(reflect(MouseListener.class).isSuperClassOf(reflect(MouseAdapter.class)));
		// isSuperClassOf ignores generics
		assertTrue(reflect(List.class).withErasure().isSuperClassOf(reflect(ArrayList.class)));

		// doesn't work
		assertFalse(reflect(Integer.class).isSuperClassOf(reflect(Object.class)));
	}

	public void assertGenericName(Class<?> clazz, String expectedFull, String expectedSimple, String expectedCanonical) {
		assertName(reflect(clazz).getGenericName(), expectedFull, expectedSimple, expectedCanonical);
	}

	public void assertName(TypeName name, String expectedFull, String expectedSimple, String expectedCanonical) {
		assertEquals(expectedFull, name.full());
		assertEquals(expectedSimple, name.simple());
		assertEquals(expectedCanonical, name.canonical());
	}

	private void assertDeclarationString(Class<?> clazz, String expected) {
		assertEquals(expected, reflect(clazz).declarationString());
	}

	private ClassType reflect(Class<?> clazz) {
		return (ClassType) reflector.reflect(clazz);
	}
}
