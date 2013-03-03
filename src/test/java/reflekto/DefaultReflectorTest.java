package reflekto;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

public class DefaultReflectorTest {

	private CachingReflector reflector = new CachingReflector();

	@Test
	public void testReflectNativeType() {
		Type t = reflector.reflect(int.class);
		assertTrue(t instanceof PrimitiveType);
		assertEquals("int", t.toString());
	}

	@Test
	public void testReflectClass() {
		Type t = reflector.reflect(Object.class);
		assertTrue(t instanceof ClassType);
		assertEquals("java.lang.Object", t.toString());
	}

	@Test
	public void testReflectArray() {
		Type t = reflector.reflect(Object[].class);
		assertTrue(t instanceof ArrayType);
		assertEquals("java.lang.Object[]", t.toString());
	}
}
