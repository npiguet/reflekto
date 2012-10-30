package reflekto.impl;

import reflekto.TypeName;

public abstract class AbstractTypeName implements TypeName {

	public String get(Kind k) {
		return k.getName(this);
	}

}
