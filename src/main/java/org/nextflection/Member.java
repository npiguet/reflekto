package org.nextflection;


public interface Member extends Parameterizable {

	public ClassType getDeclaringClass();

	public Member withErasure();

	public String declarationString();

	public String getName();
}
