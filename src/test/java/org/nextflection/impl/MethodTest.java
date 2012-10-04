package org.nextflection.impl;

import junit.framework.Assert;

import org.junit.Test;
import org.nextflection.ClassType;
import org.nextflection.Method;
import org.nextflection.Methods;

public class MethodTest {

	private DefaultReflector reflector = new DefaultReflector();

	@Test
	public void testDeclarationString(){
		ClassType obj = (ClassType)reflector.reflect(Object.class);
		Methods meths = obj.methods();

		// primitive return type
		Method m = meths.withName("hashCode").get(0);
		Assert.assertEquals("public int hashCode()", m.declarationString());

		// void return type
		m = meths.withName("notify").get(0);
		Assert.assertEquals("public final void notify()", m.declarationString());
	}

}
