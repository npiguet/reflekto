package reflekto;

import static junit.framework.Assert.assertEquals;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;


public class MethodTest {

	private CachingReflector reflector = new CachingReflector();

	@Test
	public void testDeclarationString(){
		// primitive return type
		assertDeclarationString(Object.class, "hashCode", "public int hashCode()");
		// void return type, and final
		assertDeclarationString(Object.class, "notify", "public final void notify()");
		// with generic return type
		assertDeclarationString(Object.class, "getClass", "public final java.lang.Class<?> getClass()");
		// class return type
		assertDeclarationString(Object.class, "toString", "public java.lang.String toString()");
		// with parameters
		assertDeclarationString(Object.class, "equals", "public boolean equals(java.lang.Object)");
		// type variable return type
		assertDeclarationString(ArrayList.class, "get", "public E get(int)");
		// abstract class method
		assertDeclarationString(AbstractList.class, "get", "public abstract E get(int)");
		// interface method
		assertDeclarationString(Iterator.class, "hasNext", "public abstract boolean hasNext()");
		// with type variable declaration
		assertDeclarationString(Arrays.class, "asList", "public static <T> java.util.List<T> asList(T[])");
	}

	public void assertDeclarationString(Class<?> clazz, String methodName, String expectedDeclaration){
		Methods meths = ((ClassType)reflector.reflect(clazz)).methods();
		Method m = meths.withName(methodName).get(0);
		assertEquals(expectedDeclaration, m.declarationString());
	}

}
