package reflekto;

import java.util.List;


public interface FullReflector extends Reflector {

	public Type reflect(java.lang.reflect.Type type);

	public ClassType reflect(java.lang.reflect.ParameterizedType type);
	public ClassType reflectGenericInvocation(ClassType original, List<Type> actualTypeArguments, boolean asErasure);

	public TypeVariable reflect(java.lang.reflect.TypeVariable<?> var);

	public Constructor reflect(java.lang.reflect.Constructor<?> cons, ClassType declaringClass);

	public Field reflect(java.lang.reflect.Field f, ClassType declaringClass);

	public Method reflect(java.lang.reflect.Method m, ClassType declaringClass);

	public Method reflectErasure(Method original);

	public ArrayType reflectErasure(ArrayType original);

	public WildcardType reflectGenericInvocation(WildcardType original, List<Type> lowerBounds, List<Type> upperBounds);

	public ArrayType reflectGenericInvocation(ArrayType original, Type elementType);

	public void clearTypeCache();
}
