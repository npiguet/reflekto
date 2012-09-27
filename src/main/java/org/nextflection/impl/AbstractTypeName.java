package org.nextflection.impl;

import org.nextflection.TypeName;

public abstract class AbstractTypeName implements TypeName {

	public String get(Kind k) {
		return k.getName(this);
	}

}
