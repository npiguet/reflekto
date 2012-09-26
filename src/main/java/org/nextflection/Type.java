package org.nextflection;

public interface Type extends Parameterizable {
	public Type withErasure();

	public boolean isErasure();

	public Type withTypeArgument(TypeVariable variable, ObjectType value);

	public boolean isPrimitive();

	public String getName();

	public String getSimpleName();

	public String getCanonicalName();
	
	public String declarationString();
}
