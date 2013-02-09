package reflekto;

import reflekto.impl.FullReflector;

public abstract class CallableMember extends Member {

	public CallableMember(ClassType declaringClass, FullReflector reflector) {
		super(declaringClass, reflector);
	}

}
