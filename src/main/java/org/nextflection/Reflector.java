package org.nextflection;

public interface Reflector {

	public ObjectType reflect(Class<?> clazz);

	public Type reflect(java.lang.reflect.Type type);

	public TypeVariable reflect(java.lang.reflect.TypeVariable<?> var, GenericDeclaration declaration);
}
