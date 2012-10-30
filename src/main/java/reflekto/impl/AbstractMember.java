package reflekto.impl;

import reflekto.ClassType;
import reflekto.Member;

public abstract class AbstractMember extends AbstractElement implements Member {

	protected final ClassType declaringClass;

	public AbstractMember(ClassType declaringClass, FullReflector reflector) {
		super(reflector);
		this.declaringClass = declaringClass;
	}

	public ClassType getDeclaringClass() {
		return declaringClass;
	}

}
